package models

import anorm._
import anorm.SqlParser._
import play.api.Play.current
import play.api.db.DB
import play.api.libs.json.Json
import play.api.libs.json.Writes

case class Merchant(id: Long = 0L, name: String, nickName: String, password: String, mobilePhone: String, email: String, address: String, identification: String = "", description: String)

object Merchant {
  val merchant: RowParser[Merchant] = {
    get[Long]("id") ~
      get[String]("name") ~
      get[String]("nickName") ~
      get[String]("password") ~
      get[String]("mobilePhone") ~
      get[String]("email") ~
      get[String]("address") ~
      get[String]("identification") ~
      get[String]("description") map {
        case id ~ name ~ nickName ~ password ~ mobilePhone ~ email ~ address ~ identification ~ description =>
          Merchant(id, name, nickName, password, mobilePhone, email, address, identification, description)
      }
  }

  implicit val merchantFormat = Json.format[Merchant]

  implicit val merchantWrites = new Writes[Merchant] {
    def writes(merchant: Merchant) = Json.obj(
      "id" -> merchant.id,
      "name" -> merchant.name,
      "nickName" -> merchant.nickName,
      "password" -> merchant.password,
      "mobilePhone" -> merchant.mobilePhone,
      "email" -> merchant.email,
      "address" -> merchant.address,
      "identification" -> merchant.identification,
      "description" -> merchant.description)
  }

  def getMerchantNum(): Long = {
    DB.withConnection { implicit c =>
      val count: Long = SQL("select count(*) from merchant;").as(scalar[Long].single)
      count
    }
  }

  def getMerchantPassword(name: String): String = {
    DB.withConnection { implicit c =>
      val password: String = SQL("select password from Merchant where name = {name};")
        .on("name" -> name).as(SqlParser.str("password").single)
      password;
    }
  }

  def getMerchant(name: String): Merchant = {
    DB.withConnection { implicit c =>
      val record: Merchant = SQL("select * from Merchant where name = {name}").
        on('name -> name).as(merchant.single)
      record
    }
  }

  def addMerchant(merchant: Merchant): Option[Long] = {
    DB.withConnection { implicit c =>
      val id: Option[Long] =
        SQL("insert into merchant(name,nickName,password, mobilePhone,email,address,identification,description) values ({name},{nickName}, {password},{mobilePhone},{email},{address},{identification},{description})")
          .on("name" -> merchant.name, "nickName" -> merchant.nickName, "password" -> merchant.password, "mobilePhone" -> merchant.mobilePhone, "email" -> merchant.email, "address" -> merchant.address, "identification" -> merchant.identification, "description" -> merchant.description).executeInsert()
      id
    }
  }
}