package controllers

import javax.inject._
import play.api.Logger
import play.api.http.MimeTypes
import play.api.libs.json._
import play.api.mvc._

@Singleton
class Bars @Inject()(cc: ControllerComponents)(implicit assetsFinder: AssetsFinder)
  extends AbstractController(cc) {
  val log: Logger = Logger(this.getClass())
  log.info("Constructor "+this.getClass.getName)

  def barsstat =
    Action { request =>
      //json= AnyContentAsFormUrlEncoded(ListMap(tickersId -> List(5,10,18)))
      log.info("request.body= "+request.body)

      /*
      val tickersIds: Option[String] = request.body.asFormUrlEncoded.flatMap(m => m.get("tickersId").flatMap(_.headOption))
      log.info(tickersIds.toString)
      */

      val json = request.body.asJson
      log.info("request.body.asJson="+request.body.asJson)

      val jsonBody: Option[JsValue] = request.contentType match {
            case Some(MimeTypes.JSON) => {log.info("yes, json")
              request.body.asJson
            }
            case _ => {
              log.info("xz, any")
            None
            }
          }
      log.info("jsonBody="+jsonBody)

      Ok(views.html.barsstats("Hello"+(
        jsonBody match {
          case Some(jv) => jv
          case None => "None"
        }
      )))
    }

}