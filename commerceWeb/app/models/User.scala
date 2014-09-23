package models

import anorm._
import anorm.SqlParser._
import play.api.Play.current
import play.api.db.DB
import play.api.libs.json.Json

case class User(id: Long = 0L, userName: String, nickName: String, password: String, mobilePhone: String, email: String, address: String)

object User {
  val user: RowParser[User] = {
    get[Long]("id") ~
      get[String]("userName") ~
      get[String]("nickName") ~
      get[String]("password") ~
      get[String]("mobilePhone") ~
      get[String]("email") ~
      get[String]("address") map {
        case id ~ userName ~ nickName ~ password ~ mobilePhone ~ email ~ address =>
          User(id, userName, nickName, password, mobilePhone, email, address)
      }
  }  
  
  implicit val userFormat = Json.format[User]

  def getUserNum(): Long = {
    DB.withConnection { implicit c =>
      val count: Long = SQL("select count(*) from user;").as(scalar[Long].single)
      count
    }
  }

  def getUserPassword(userName: String): String = {
    DB.withConnection { implicit c =>
      val password: String = SQL("select password from user where userName = {userName};")
        .on("userName" -> userName).as(SqlParser.str("password").single)
      password;
    }
  }

  def getUser(userName: String): User = {
    DB.withConnection { implicit c =>
      val record: User = SQL("select * from user where userName = {userName}").
        on('userName -> userName).as(user.single)
      record
    }
  }

  def addUser(user: User): Option[Long] = {
    DB.withConnection { implicit c =>
      val id: Option[Long] =
        SQL("insert into user(userName,nickName,password, mobilePhone,email,address) values ({userName},{nickName}, {password},{mobilePhone},{email},{address})")
          .on("userName" -> user.userName, "nickName" -> user.nickName, "password" -> user.password, "mobilePhone" -> user.mobilePhone, "email" -> user.email, "address" -> user.address).executeInsert()
      id
    }
  }
}