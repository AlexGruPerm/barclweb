package controllers
import java.time.LocalDate
import java.time.format.DateTimeFormatter

import app.Global
import javax.inject._
import models.{BarSimple, CommonFuncs}
import play.api.Logger
import play.api.libs.json.{Json, _}
import play.api.mvc._

@Singleton
class ControllerBars @Inject()(cc: ControllerComponents)(implicit assetsFinder: AssetsFinder)
  extends AbstractController(cc) with CommonFuncs {
  val log: Logger = Logger(this.getClass())

  /*
  //todo: saved for future delete.
  //todo: in route :
  //todo: GET /getbars/:tickerid/:barwidthsec/:deeplimit/:pddate controllers.ControllerBars.getJsonBarsByTickerWidthDeep(tickerid: Int, barwidthsec :Int, deeplimit:Int, pddate :String)
  def  getJsonBarsByTickerWidthDeep(tickerid: Int, barwidthsec :Int, deeplimit:Int, pddate :String)= Action {implicit request =>
    log.info(this.getClass.getName+" getJsonBarsByTickerWidthDeep")
    val uid :String = Global.reqLog.saveLog(request)

    val sess  = Global.sessInstance
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    val lDate :LocalDate = LocalDate.parse(pddate, formatter);
    val bars :Seq[BarSimple] = sess.getBarsForGraph(tickerid, barwidthsec, deeplimit, lDate)
    log.info("getJsonBarsByTickerWidthDeep bars.size="+bars.size)

    implicit val residentWrites = new Writes[BarSimple] {
      def writes(bar: BarSimple) = Json.toJson(bar.getTsEndFull,bar.c)
    }
    val jres =Json.obj(
      "label" -> ("TICKER_ID="+tickerid),
      "data" -> bars
    )
    Ok(jres)
  }
  */


  /**
   * Return bars for plot highcharts.highstock.Bars
   * Parameter "deeplimit" useless. Return all bars from date.
  */
  def  getJsonBarsGByTickerWidthDeep(tickerid: Int, barwidthsec :Int, deeplimit:Int, pddate :String)= Action {implicit request =>
    log.info(this.getClass.getName+" getJsonBarsByTickerWidthDeep")
    val uid :String = Global.reqLog.saveLog(request)

    val sess  = Global.sessInstance
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    val lDate :LocalDate = LocalDate.parse(pddate, formatter);
    val bars :Seq[BarSimple] = sess.getBarsForGraph(tickerid, barwidthsec, deeplimit, lDate)
    log.info("getJsonBarsByTickerWidthDeep bars.size="+bars.size)

    implicit val residentWrites = new Writes[BarSimple] {
      def writes(bar: BarSimple) = Json.toJson(bar.ts_end, bar.o, bar.h, bar.l, bar.c)
    }

    val graphTitle :String = (sess.tickersDict.find(elmTicker => elmTicker.tickerId==tickerid) match {
      case Some(foundTicker) => foundTicker.tickerCode
      case None => "N/A"
    }) + " ["+barwidthsec+"]"

    val jres = Json.obj("data" -> bars, "title" -> graphTitle)

    Ok(jres)
  }


}
