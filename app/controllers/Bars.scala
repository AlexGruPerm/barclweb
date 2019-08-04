package controllers

import app.Global
import javax.inject._
import models.{CommonFuncs, LastBar, TickerBws}
import play.api.Logger
import play.api.http.MimeTypes
import play.api.libs.json._
import play.api.mvc._



@Singleton
class Bars @Inject()(cc: ControllerComponents)(implicit assetsFinder: AssetsFinder)
  extends AbstractController(cc) with CommonFuncs {
  val log: Logger = Logger(this.getClass())
  log.info("Constructor "+this.getClass.getName)
  val sess  = Global.sessInstance

  def barsstat = Action(parse.json) {
    request => {
      request.contentType match {
        case Some(MimeTypes.JSON) => {
          val tickerSeq :Option[JsArray] = (request.body \ "tickersId").asOpt[JsArray]
          log.info("tickerSeq="+tickerSeq)
          tickerSeq match {
            case Some(tickersArray) => {
              val seqTickers :Seq[Int] =  tickersArray.value.map(v => v.as[String].toInt).toSeq
              log.info("seqTickers="+seqTickers)

              val t1 = System.currentTimeMillis
              val seqLastBars :Seq[LastBar] = sess.getLastBarsByTickers(seqTickers)
              val durrS :Double = (System.currentTimeMillis-t1).toDouble/1000.toDouble
              log.info(s"Duration $durrS s. seqLastBars size=${seqLastBars.size}")

              Ok(views.html.barsstats("Hello",seqLastBars))
            }
            case None => BadRequest("Request must be Json with parameter 'tickersId' : [array of tickerId.] ")
          }
        }
        case None => BadRequest("Request must be Json with parameter 'tickersId' : [array of tickerId.] ")
      }
    }
  }







}