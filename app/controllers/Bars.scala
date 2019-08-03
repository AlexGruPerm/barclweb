package controllers

import javax.inject._
import play.api.Logger
import play.api.http.MimeTypes
import play.api.libs.json._
import play.api.mvc.{request, _}



@Singleton
class Bars @Inject()(cc: ControllerComponents)(implicit assetsFinder: AssetsFinder)
  extends AbstractController(cc) {
  val log: Logger = Logger(this.getClass())
  log.info("Constructor "+this.getClass.getName)

  /*
  def barsstat =
    Action { request =>
      log.info("request.body= "+request.body)
      val jsonBody: Option[JsValue] = request.contentType match {
            case Some(MimeTypes.JSON) => {
              log.info("yes, json")
              val json :Option[JsValue] = request.body.asJson
              log.info("json = "+json)

              /*
              val seqTickerIs = Json.parse(request.body.asText.getOrElse(" ")).as[JsObject]
              log.info("!!!!!! seqTickerIs="+seqTickerIs)
              */
              json
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
*/



  def barsstat = Action(parse.json) {
    request => {
      //log.info(request.body.toString())

      request.contentType match {
        case Some(MimeTypes.JSON) => {
          val tickerSeq :Option[JsArray] = (request.body \ "tickersId").asOpt[JsArray]
          log.info("tickerSeq="+tickerSeq)

          tickerSeq match {
            case Some(tickersArray) => {

              val seqTickers :Seq[Int] =  tickersArray.value.map(v => v.as[String].toInt).toSeq
              log.info("seqTickers="+seqTickers)

              Ok(views.html.barsstats("Hello",seqTickers))
            }
            case None => BadRequest("Request must be Json with parameter 'tickersId' : [array of tickerId.] ")
          }

        }
        case None => BadRequest("Request must be Json with parameter 'tickersId' : [array of tickerId.] ")
      }

      }
  }







}