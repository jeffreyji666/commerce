package util

import java.io.IOException
import java.io.InputStream
import java.util.Properties

import play.api.Logger

/**
 * Configuration class
 */
object Config {

  val rootDir =
    if (System.getProperty("root.dir") != null) System.getProperty("root.dir")
    else ""

  val properties = getProperties(rootDir + "conf/app.conf");
  properties.putAll(getProperties(rootDir + "conf/app-test.conf"));
  properties.putAll(getProperties(rootDir + "conf/app-local.conf"));

  def getString(key: String): String = {
    return properties.getProperty(key);
  }

  def getInt(key: String): Integer = {
    return Integer.parseInt(getString(key));
  }

  def getBool(key: String): Boolean = {
    return getString(key).toLowerCase == "true";
  }

  def initialize(fileName: String) {
    properties.putAll(getProperties(rootDir + fileName))
    properties.putAll(getProperties(rootDir + "conf/app-test.conf"));
    properties.putAll(getProperties(rootDir + "conf/app-local.conf"));
  }

  private def getProperties(filename: String): Properties = {
    var prop = new Properties()
    var ins: InputStream = null
    try {
      //ins = ClassLoader.getSystemResourceAsStream(filename)
      ins = new java.io.FileInputStream(filename)
      Logger.debug("load properties: " + ins)
      if (ins != null) {
        prop.load(ins)
      }
    } catch {
      case e: Exception =>
        Logger.info("Error while loading file: " + filename)
    } finally {
      try {
        if (ins != null)
          ins.close()
      } catch {
        case e: IOException =>
          Logger.info("Error while close inputstream: " + filename)
      }
    }
    return prop;
  }
}