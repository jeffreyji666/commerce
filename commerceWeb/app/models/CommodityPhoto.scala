package models

import anorm._
import anorm.SqlParser._
import play.api.Play.current
import play.api.db.DB
import play.api.libs.json.Json
import play.api.libs.json.Writes

case class CommodityPhoto(id: Long = 0L, commodityId: Long, photoUrl: String, photoDesc: String)

object CommodityPhoto {
  val photo: RowParser[CommodityPhoto] = {
    get[Long]("id") ~
      get[Long]("commodityId") ~
      get[String]("photoUrl") ~
      get[String]("photoUrl") map {
        case id ~ commodityId ~ photoUrl ~ photoDesc =>
          CommodityPhoto(id, commodityId, photoUrl, photoDesc)
      }
  }

  implicit val photoFormat = Json.format[CommodityPhoto]

  implicit val photoWrites = new Writes[CommodityPhoto] {
    def writes(photo: CommodityPhoto) = Json.obj(
      "id" -> photo.id,
      "commodityId" -> photo.commodityId,
      "photoUrl" -> photo.photoUrl,
      "photoUrl" -> photo.photoUrl)
  }

  def getCommodityPhotoNum(commodityId: Long): Long = {
    DB.withConnection { implicit c =>
      val count: Long = SQL("select count(*) from commodity_photo where commodityId = {commodityId};").on("commodityId" -> commodityId).as(scalar[Long].single)
      count
    }
  }

  def getCommodityPhotos(commodityId: Long): Seq[CommodityPhoto] = {
    DB.withConnection { implicit c =>
      val records: List[CommodityPhoto] = SQL("select * from commodity_photo where commodityId = {commodityId};")
        .on("commodityId" -> commodityId).as(photo.*)
      records
    }
  }

  def addCommodityPhoto(commodityPhoto: CommodityPhoto): Option[Long] = {
    DB.withConnection { implicit c =>
      val id: Option[Long] =
        SQL("insert into commodity_photo(commodityId,photoUrl,photoDesc) values ({commodityId},{photoUrl},{photoDesc})")
          .on("commodityId" -> commodityPhoto.commodityId, "photoUrl" -> commodityPhoto.photoUrl, "photoDesc" -> commodityPhoto.photoDesc).executeInsert()
      id
    }
  }

  def delCommodityPhoto(id: Long): Int = {
    DB.withConnection { implicit c =>
      val result: Int = SQL("delete from commodity_photo where id = {id}").on("id" -> id).executeUpdate()
      result
    }
  }

  def delCommodityPhotos(commodityId: Long): Int = {
    DB.withConnection { implicit c =>
      val result: Int = SQL("delete from commodity_photo where commodityId = {commodityId}").on("commodityId" -> commodityId).executeUpdate()
      result
    }
  }
}