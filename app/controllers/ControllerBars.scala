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

    Thread.sleep(5000)

    val jres =Json.obj(
      "label" -> ("TICKER_ID="+tickerid),
      "data" -> bars
    )

    //log.info(jres.toString())
    Ok(jres)
  }


}
