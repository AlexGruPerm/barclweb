package models

import java.time.LocalDate

import com.datastax.oss.driver.api.core.CqlSession
import com.datastax.oss.driver.api.core.cql.BoundStatement
import models.CommRowConverters._

import scala.concurrent.{Await, Future}
import scala.concurrent.duration.Duration
import scala.jdk.CollectionConverters._
import scala.concurrent.ExecutionContext.Implicits.global

object CassSessionInstance extends CassSession{

  private val (node :String,dc :String) = getNodeAddressDc("src")
  log.info("CassSessionInstance DB Address : "+node+" - "+dc)
  val sess :CqlSession = createSession(node,dc)
  log.info("CassSessionInstance session is connected = " + !sess.isClosed)

  private val prepTickersDict: BoundStatement = prepareSql(sess, sqlTickersDict)
  private val prepBCalcProps :BoundStatement = prepareSql(sess,sqlBCalcProps)
  private val prepLastTickDdate :BoundStatement = prepareSql(sess,sqlLastTickDdate)
  private val prepLastTickTs :BoundStatement = prepareSql(sess,sqlLastTickTs)
  private val prepBarsBwsCodeStats :BoundStatement = prepareSql(sess,sqlBarsBwsCodeStats)
  private val prepBarByDateTs :BoundStatement = prepareSql(sess,sqlBarByDateTs)
  //groups of queries for save web clients stats.
  private val prepSaveStatComm :BoundStatement = prepareSql(sess,sqlSaveStatComm)
  private val prepSaveStatDdate :BoundStatement = prepareSql(sess,sqlSaveStatDdate)
  private val prepSaveStatUid :BoundStatement = prepareSql(sess,sqlSaveStatUid)
  private val prepSaveStatRPath :BoundStatement = prepareSql(sess,sqlSaveStatRPath)

  private def tickersDictReader: Seq[Ticker] = sess.execute(prepTickersDict).all().iterator.asScala
    .map(rowToTicker).toList.filter(tck => tck.tickerId < 30).sortBy(_.tickerId)
  lazy private val tickersDist = tickersDictReader
  def tickersDict = tickersDist

  def getAllBarsPropertiesReader : Seq[BarCalcProperty] = sess.execute(prepBCalcProps).all().iterator.asScala
    .map(rowToBarCalcProperty).toList.filter(bp => bp.isEnabled==1)
  lazy private val seqBarsProperties :Seq[BarCalcProperty] = getAllBarsPropertiesReader
  def getAllBarsProperties = seqBarsProperties

  def getTickersBws(seqTickers :Seq[Ticker] = tickersDict,
                    seqBarProp :Seq[BarCalcProperty] = getAllBarsProperties) :Seq[TickerBws] =
    seqBarProp.iterator.flatMap{
      thisBarProp => seqTickers.collect{
        case thisTicker if thisTicker.tickerId == thisBarProp.tickerId =>
          TickerBws(thisTicker,thisBarProp.bws)
      }
    }.toSeq.sortBy(tbp => (tbp.ticker.tickerId,tbp.bws))


  private def getTickerMaxDdate(ticker :Ticker) :LocalDate =
    sess.execute(prepLastTickDdate.setInt("tickerId",ticker.tickerId)).one().getLocalDate("ddate")

  def getTickerLastTs(ticker :Ticker) :Long =
    sess.execute(prepLastTickTs
      .setInt("tickerId",ticker.tickerId)
      .setLocalDate("pDdate",getTickerMaxDdate(ticker))).one().getLong("db_tsunx")



  def getBars(thisTickerBws: TickerBws,thisTickerWithDdateTs :TickerWithDdateTs,currTimestamp :Long): Seq[LastBar] = {
    sess.execute(prepBarsBwsCodeStats
        .setInt("tickerID",thisTickerBws.ticker.tickerId)
        .setInt("bws",thisTickerBws.bws)
    ).all().iterator.asScala.toSeq.map(row => (row.getLocalDate("ddate"),row.getLong("ts_end_max")))
        .collect{
          case lastBarStat =>
            val (lastBarDdate :LocalDate, lastBarTs :Long) = lastBarStat
            sess.execute(prepBarByDateTs
              .setInt("tickerID", thisTickerBws.ticker.tickerId)
              .setLocalDate("pDdate", lastBarDdate)
              .setInt("bws",thisTickerBws.bws)
              .setLong("ts", lastBarTs)
            ).all().iterator.asScala.toSeq.map(row => rowToLastBar(currTimestamp,thisTickerWithDdateTs,row)).toList.head
        }
  }

  def getLastBarsByTickers(seqTickersId :Seq[Int]) :Seq[LastBar] = {

    val seqTickerBws :Seq[TickerBws] = getTickersBws(tickersDict.filter(td => seqTickersId.contains(td.tickerId)))
    log.info("getLastBarsByTickers seqTickerBws.size="+seqTickerBws.size)

    val currTimestamp :Long = System.currentTimeMillis

    val futs: Seq[Future[TickerWithDdateTs]] = seqTickerBws.map(tbws => tbws.ticker).distinct
      .map(t => Future(TickerWithDdateTs(t, getTickerLastTs(t), currTimestamp)))

    val seqTickersDdateTs :Seq[TickerWithDdateTs] = Await.result(Future.sequence(futs), Duration.Inf)
      .sortBy(elm => elm.diffSeconds)(Ordering[Long])

    val seqLastBarsFuts :Seq[Future[Seq[LastBar]]] = seqTickerBws
      .flatMap(tbws => seqTickersDdateTs
        .withFilter(twd => twd.ticker.tickerId == tbws.ticker.tickerId)
        .flatMap(twdf => Seq(Future(getBars(tbws,twdf,currTimestamp))))
      )

    val seqLastBars :Seq[LastBar] = Await.result(Future.sequence(seqLastBarsFuts), Duration.Inf).flatten
    seqLastBars
  }


  def saveClientLog(logRow :clientRow) ={
    sess.execute(prepSaveStatComm
      .setString("uid",logRow.clientSessionId)
      .setString("uip",logRow.remoteAddress)
      .setString("rpath",logRow.routerPath)
      .setString("cntrl",logRow.routerController)
      .setString("mth",logRow.routerMethod)
    )
    sess.executeAsync(prepSaveStatDdate)
    sess.executeAsync(prepSaveStatUid.setString("uid",logRow.clientSessionId))
    sess.executeAsync(prepSaveStatRPath.setString("rpath",logRow.routerPath))
  }




  trait Factory {
    def apply(): CassSessionInstance.type
  }

}
