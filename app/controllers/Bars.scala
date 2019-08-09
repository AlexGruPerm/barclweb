package controllers

import app.Global
import javax.inject._
import models.{CommonFuncs, LastBar, TickerFailBwsCnt}
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

  /**
   * Take one GET parameter tickerID
   * Calculate fail cnt of bars for ticker and bws.
   * And return json {"failcnt" : X}
   * Called with AJAX from
   *
   * For single ticker
   */
  def  bwsfailcnt(tickerid: Int)= Action {implicit request =>
    val uid :String = Global.reqLog.saveLog(request)
    //Thread.sleep(3000)
    log.info("bwsfailcnt tickerId="+tickerid)
    //Thread.sleep(Random.nextInt(5000))
    val t1 = System.currentTimeMillis
    val seqLastBars :Seq[LastBar] = sess.getLastBarsByTickers(Seq(tickerid))
    val durrS :Double = (System.currentTimeMillis-t1).toDouble/1000.toDouble
    log.info(s"Duration $durrS s. bwsfailcnt size=${seqLastBars.size}")
    val failCnt :Int = seqLastBars.count(_.isFail==1)
    val jres =Json.obj("failcnt" -> failCnt)
    Ok(jres)//.withSession(request.session + ("uid" -> uid))
  }

  /** like bwsfailcnt search above.
   * But also GEt and don't receive parameters. Take it from
  */
  def  bwsfailcnta= Action {implicit request =>
    val uid :String = Global.reqLog.saveLog(request)
    //Thread.sleep(3000)
    val t1 = System.currentTimeMillis
    //get it by all tickers.
    val seqLastBars :Seq[LastBar] = sess.getLastBarsByTickers(sess.tickersDict.map(td => td.tickerId))
    val durrS :Double = (System.currentTimeMillis-t1).toDouble/1000.toDouble
    log.info(s"Duration $durrS s. bwsfailcnta size=${seqLastBars.size}")

    val seqTickerFailBwsCnt: Seq[TickerFailBwsCnt] =
      seqLastBars.map(_.getTickerId).distinct.map {
        thisTickerId =>
          TickerFailBwsCnt(thisTickerId, seqLastBars
            .count(c => c.getTickerId == thisTickerId && c.isFail == 1))
      }

    implicit val residentWrites = new Writes[TickerFailBwsCnt] {
      def writes(thisTickerFail: TickerFailBwsCnt) = //Json.toJson()
      Json.obj("tickerID" -> thisTickerFail.tickerId, "failcnt" -> thisTickerFail.failCnt)
    }

    val jres =Json.obj(
      "tickersFails" -> seqTickerFailBwsCnt
    )
    /*
    {"tickersFails":[
                     {"tickerID":1,"failcnt":2},
                     {"tickerID":2,"failcnt":0},
                     {"tickerID":3,"failcnt":5}
                    ]
    }
    */

    /** Examples:
     *
     * /*
     * val json: JsValue = Json.obj(
     * "name" -> "Watership Down",
     * "location" -> Json.obj("lat" -> 51.235685, "long" -> -1.309197),
     * "residents" -> Json.arr(
     *       Json.obj(
     * "name" -> "Fiver",
     * "age" -> 4,
     * "role" -> JsNull
     * ),
     *       Json.obj(
     * "name" -> "Bigwig",
     * "age" -> 6,
     * "role" -> "Owsla"
     * )
     * )
     * )
     * */
     *
    */

    log.info(jres.toString())
    Ok(jres)
  }




}