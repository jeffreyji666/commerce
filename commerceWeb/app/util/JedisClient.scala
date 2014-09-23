package com.buzzinate.lezhi.db

import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.util.ArrayList

import play.api.Logger
import redis.clients.jedis.JedisPoolConfig
import redis.clients.jedis.JedisShardInfo
import redis.clients.jedis.ShardedJedis
import redis.clients.jedis.ShardedJedisPool
import util.Config

object JedisClient {
  private val ips = Config.getString("redis.host")
  private val port = Config.getInt("redis.port")

  private var pool: ShardedJedisPool = null
  private var jedisShardInfos: ArrayList[JedisShardInfo] = null

  try {
    val poolConfig = new JedisPoolConfig()
    poolConfig.setTestWhileIdle(true)
    poolConfig.setMaxTotal(200)
    poolConfig.setMinEvictableIdleTimeMillis(1000L * 120)

    jedisShardInfos = new ArrayList[JedisShardInfo]
    ips.split(",").foreach(ip => {
      val jinfo = new JedisShardInfo(ip, port)
      jedisShardInfos.add(jinfo)
    })

    pool = new ShardedJedisPool(poolConfig, jedisShardInfos)
  } catch {
    case ex: Exception =>
      Logger.error("Failed to init redis client.", ex)
      pool == null
  }

  def set(key: String, expire: Int, value: Object) {
    val bs = serialize(value)
    use(pool) { client =>
      client.setex(key.getBytes(), expire, bs)
    }
  }

  def get(key: String): String = {
    use(pool) { client =>
      client.get(key)
    }
  }

  def getObject(key: String): Any = {
    val bs = use(pool) { client =>
      client.get(key.getBytes())
    }
    deserialize(bs)
  }

  def set(key: String, expire: Int, value: String) {
    use(pool) { client =>
      client.setex(key, expire, value)
    }
  }

  def delete(key: String) {
    use(pool) { client => client.del(key)
    }
  }

  def flush() {
    use(pool) { client =>
      val allJedis = client.getAllShards().iterator()
      while (allJedis.hasNext()) {
        val jedis = allJedis.next()
        jedis.flushAll()
      }
    }
  }

  def shutdown() {
    if (pool != null)
      pool.destroy()
  }

  private def serialize(obj: Any): Array[Byte] = {
    if (obj != null) {
      val bos = new ByteArrayOutputStream()
      val oos = new ObjectOutputStream(bos)
      try {
        oos.writeObject(obj)
        val bs = bos.toByteArray()
        bs
      } catch {
        case ex: Exception =>
          Logger.error("Failed to serialize obj." + obj.toString(), ex)
          null
      } finally {
        bos.close()
        oos.close()
      }
    } else {
      null
    }
  }

  private def deserialize(bs: Array[Byte]): Any = {
    if (bs != null) {
      val bis = new ByteArrayInputStream(bs)
      val inputStream = new ObjectInputStream(bis)
      try {
        val obj = inputStream.readObject()
        obj
      } catch {
        case ex: Exception =>
          Logger.error("Failed to deserialize.", ex)
          null
      } finally {
        bis.close()
        inputStream.close()
      }
    } else {
      null
    }
  }

  //manage the redis connection with pool
  private def use[T](pool: ShardedJedisPool)(f: ShardedJedis => T): T = {
    var returned = false
    val jedis = pool.getResource
    try {
      f(jedis)
    } catch {
      case e: Exception => {
        pool.returnBrokenResource(jedis)
        returned = true
        throw e
      }
    } finally {
      if (!returned) pool.returnResource(jedis)
    }
  }
}