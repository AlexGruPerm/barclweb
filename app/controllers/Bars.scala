package controllers

import javax.inject._
import play.api.Logger
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
      log.info("json= "+request.body)
      //val json = request.body.asJson

      val body: AnyContent          = request.body
      val jsonBody: Option[JsValue] = body.asJson

      log.info("jsonBody= "+jsonBody)

      jsonBody
        .map { json =>
          log.info("json= "+(json \ "name").as[String])
        }
        .getOrElse {
          log.info(("Bad Json"))
        }

      Ok(views.html.barsstats("Hello"))
    }

}