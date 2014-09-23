package controllers

import org.specs2.mutable.Specification

import models.User
import bean.JsonResult
import play.api.libs.json.Json
import play.api.test.FakeRequest
import play.api.test.Helpers.OK
import play.api.test.Helpers.POST
import play.api.test.Helpers.contentAsString
import play.api.test.Helpers.defaultAwaitTimeout
import play.api.test.Helpers.status
import play.api.test.WithApplication

class ApplicationSpec extends Specification {

  "getNames" should {
    "should be json string" in new WithApplication {
      val result = Application.getNames.apply(FakeRequest())
      status(result) must equalTo(OK)
      val bodyText: String = contentAsString(result)
      bodyText must beEqualTo("[1,2,3]")
    }
  }

  "login" should {
    "should be login" in new WithApplication {
      val result = Application.login.apply((FakeRequest(POST, "/api/auth/login").withBody(Json.parse("""{"userName": "test","password":"test"}"""))))
      status(result).mustEqual(OK)
      Json.parse(contentAsString(result)).as[JsonResult].msg must beEqualTo("登录成功")
    }
  }

  "register" should {
    "should be registered" in new WithApplication {
      val user = Json.toJson(User(0L, "jeffrey", "jeffreyji", "test", "13917958249", "test@gmail.com", "上海市金钟路120号"))
      val result = Application.register.apply((FakeRequest(POST, "/api/auth/register").withBody(user)))
      status(result).mustEqual(OK)
      Json.parse(contentAsString(result)).as[JsonResult].msg must beEqualTo("注册成功")
    }
  }
}
