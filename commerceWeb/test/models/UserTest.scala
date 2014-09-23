package models

import org.specs2.mutable.Specification

import play.api.test.WithApplication

class UserTest extends Specification {

  "User#getUserNum" should {
    "be 1 when there is only 1 record" in new WithApplication {
      val actual = User.getUserNum

      actual must beEqualTo(1L)
    }
  }
}