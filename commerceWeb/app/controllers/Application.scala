package controllers

import bean.JsonResult
import models.User
import play.api.libs.json.JsError
import play.api.libs.json.Json
import play.api.mvc.Action
import play.api.mvc.Controller
import play.api.libs.json._
import play.api.libs.functional.syntax._

object Application extends Controller {
  implicit val loginInfo = (
    (__ \ 'userName).read[String] and
    (__ \ 'password).read[String]) tupled

  def getNames = Action {
    val names = List(1, 2, 3)
    Ok(Json.toJson(names)).as(JSON)
  }

  def login = Action(parse.json) { implicit request =>
    request.body.validate[(String, String)].map {
      case (userName, password) => {
        val res = User.getUserPassword(userName) == password
        Ok(Json.toJson(JsonResult(res, "200", "登录成功")))
      }
    }.recoverTotal {
      e => BadRequest("Detected error:" + JsError.toFlatJson(e))
    }
  }

  def register = Action(parse.json) { implicit request =>
    request.body.validate[User].map { user =>
      {
        val res = User.addUser(user).getOrElse(0L) > 0L
        Ok(Json.toJson(JsonResult(res, "200", "注册成功")))
      }
    }.recoverTotal {
      e => BadRequest("Detected error:" + JsError.toFlatJson(e))
    }
  }

}
