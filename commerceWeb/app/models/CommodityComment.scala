package models

import anorm._
import anorm.SqlParser._
import play.api.Play.current
import play.api.db.DB
import play.api.libs.json.Json
import play.api.libs.json.Writes

case class CommodityComment(id: Long = 0L, nickName: String, commodityId: Long, comment: String)

object CommodityComment {
  val comment: RowParser[CommodityComment] = {
    get[Long]("id") ~
      get[String]("nickName") ~
      get[Long]("commodityId") ~
      get[String]("comment") map {
        case id ~ nickName ~ commodityId ~ comment =>
          CommodityComment(id, nickName, commodityId, comment)
      }
  }

  implicit val commentFormat = Json.format[CommodityComment]

  implicit val commentWrites = new Writes[CommodityComment] {
    def writes(comment: CommodityComment) = Json.obj(
      "id" -> comment.id,
      "nickName" -> comment.nickName,
      "commodityId" -> comment.commodityId,
      "comment" -> comment.comment)
  }

  def getCommodityCommentNum(commodityId: Long): Long = {
    DB.withConnection { implicit c =>
      val count: Long = SQL("select count(*) from commodity_comment where commodityId = {commodityId};").on("commodityId" -> commodityId).as(scalar[Long].single)
      count
    }
  }

  def getCommodityComments(commodityId: Long): Seq[CommodityComment] = {
    DB.withConnection { implicit c =>
      val records: List[CommodityComment] = SQL("select * from commodity_comment where commodityId = {commodityId};")
        .on("commodityId" -> commodityId).as(comment.*)
      records
    }
  }

  def addCommodityComment(comment: CommodityComment): Option[Long] = {
    DB.withConnection { implicit c =>
      val id: Option[Long] =
        SQL("insert into commodity_comment(nickName,commodityId,comment) values ({commodityId},{comment})")
          .on("nickName" -> comment.nickName, "commodityId" -> comment.commodityId, "comment" -> comment.comment).executeInsert()
      id
    }
  }

  def delCommodityComment(id: Long): Int = {
    DB.withConnection { implicit c =>
      val result: Int = SQL("delete from commodity_comment where id = {id}").on("id" -> id).executeUpdate()
      result
    }
  }

  def delCommodityComments(commodityId: Long): Int = {
    DB.withConnection { implicit c =>
      val result: Int = SQL("delete from commodity_comment where commodityId = {commodityId}").on("commodityId" -> commodityId).executeUpdate()
      result
    }
  }
}