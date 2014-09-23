package bean

import play.api.libs.json.Json

case class JsonResult(res: Boolean, statusCode: String, msg: String) {
}

object JsonResult {
  implicit val resultFormat = Json.format[JsonResult]
}