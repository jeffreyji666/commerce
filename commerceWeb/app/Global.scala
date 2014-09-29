import play.api._
import play.api.mvc._
import play.api.mvc.Results._
import play.filters.gzip.GzipFilter
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

object Global extends WithFilters(new GzipFilter) with GlobalSettings {
  override def onStart(app: Application) {
    Logger.info("Application has started")
  }

  override def onStop(app: Application) {
    Logger.info("Application shutdown")
  }

  override def onError(request: RequestHeader, ex: Throwable) = Future { InternalServerError("{}") }
  override def onBadRequest(request: RequestHeader, error: String) = Future { BadRequest("{}") }
  override def onHandlerNotFound(request: RequestHeader) = Future { NotFound("{}") }
}