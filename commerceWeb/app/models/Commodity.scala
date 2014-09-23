package models

import anorm._
import anorm.SqlParser._
import play.api.Play.current
import play.api.db.DB
import play.api.libs.json.Json

case class Commodity(id: Long = 0L, merchantId: Long, name: String, description: String, price: BigDecimal)

object Commodity {
  val commodity: RowParser[Commodity] = {
    get[Long]("id") ~
      get[Long]("merchantId") ~
      get[String]("name") ~
      get[String]("description") ~
      get[BigDecimal]("price") map {
        case id ~ merchantId ~ name ~ description ~ price =>
          Commodity(id, merchantId, name, description, price)
      }
  }

  implicit val commodityFormat = Json.format[Commodity]

  def getCommodityNum(merchantId: Long): Long = {
    DB.withConnection { implicit c =>
      val count: Long = SQL("select count(*) from commodity where merchantId = {merchantId};").on("merchantId" -> merchantId).as(scalar[Long].single)
      count
    }
  }

  def getCommodity(name: String): Commodity = {
    DB.withConnection { implicit c =>
      val record: Commodity = SQL("select * from commodity where name = {name};")
        .on("name" -> name).as(commodity.single)
      record
    }
  }

  def getCommodity(merchantId: Long): Seq[Commodity] = {
    DB.withConnection { implicit c =>
      val records: List[Commodity] = SQL("select * from commodity where merchantId = {merchantId};")
        .on("merchantId" -> merchantId).as(commodity.*)
      records
    }
  }

  def addCommodity(commodity: Commodity): Option[Long] = {
    DB.withConnection { implicit c =>
      val id: Option[Long] =
        SQL("insert into commodity(merchantId,name,description,price) values ({merchantId},{name},{description},{price})")
          .on("merchantId" -> commodity.merchantId, "name" -> commodity.name, "description" -> commodity.description, "price" -> commodity.price).executeInsert()
      id
    }
  }

  def updateCommodity(commodity: Commodity): Int = {
    DB.withConnection { implicit c =>
      val params: Seq[NamedParameter] = Seq("name" -> commodity.name, "description" -> commodity.description, "price" -> commodity.price, "id" -> commodity.id)
      val num = SQL("update commodity set name = {name},description = {description},price = {price} WHERE id = {id}").on(params: _*).executeUpdate
      num
    }
  }
}