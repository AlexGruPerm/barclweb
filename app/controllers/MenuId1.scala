package controllers

import app.Global
import javax.inject._
import models.{CommonFuncs, Ticker, TickerWithDdateTs}
import play.api.Logger
import play.api.mvc._

import scala.concurrent.{Await, Future}
import scala.concurrent.duration.Duration
import scala.concurrent.ExecutionContext.Implicits.global

@Singleton
class MenuId1 @Inject()(cc: ControllerComponents)(implicit assetsFinder: AssetsFinder)
  extends AbstractController(cc) with CommonFuncs {
  val log: Logger = Logger(this.getClass())
  log.info("Constructor "+this.getClass.getName)

  def midAction = Action {
    log.info(this.getClass.getName+" mid1")

    val sess  = Global.sessInstance
    val tickersFrx :Seq[Ticker] = sess.tickersDict
    val currentDateTime :String = getDateAsString(convertLongToDate(System.currentTimeMillis))

    /**
     * Old code: sequential execution.
     * [info] c.MenuId1 - Duration 1.877 s. size=28
     * [info] c.MenuId1 - Duration 2.502 s. size=28
     * [info] c.MenuId1 - Duration 1.763 s. size=28
     *
     * val seqTickersDdateTs :Seq[TickerWithDdateTs] = tickersFrx.map(t => TickerWithDdateTs(t,sess.getTickerLastTs(t)))
     * .sortBy(elm => elm.diffSeconds)(Ordering[Long])
     *
     * Parallel execution with Futures(timings):
     * [info] c.MenuId1 - Duration 0.273 s. size=28
     * [info] c.MenuId1 - Duration 0.132 s. size=28
     * [info] c.MenuId1 - Duration 0.161 s. size=28
     *
    */
      //todo : remove this 4 lines into method in sess.
    val t1 = System.currentTimeMillis
    val currTimestamp :Long = System.currentTimeMillis
    val futs: Seq[Future[TickerWithDdateTs]] = tickersFrx
      .map(t => Future(TickerWithDdateTs(t, sess.getTickerLastTs(t), currTimestamp)))
    val seqTickersDdateTs :Seq[TickerWithDdateTs] = Await.result(Future.sequence(futs), Duration.Inf)
      .sortBy(elm => elm.ticker.tickerId)
      //.sortBy(elm => elm.diffSeconds)(Ordering[Long])

    val durrS :Double = (System.currentTimeMillis-t1).toDouble/1000.toDouble
    log.info(s"Duration $durrS s. seqTickersDdateTs size=${seqTickersDdateTs.size}")
    Ok(views.html.mid1("mid1",1,seqTickersDdateTs,currentDateTime))
  }

}

