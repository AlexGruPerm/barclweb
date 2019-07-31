package models

import java.time.LocalDate

import com.datastax.oss.driver.api.core.CqlSession
import com.datastax.oss.driver.api.core.cql.BoundStatement
import models.CommRowConverters._

import scala.jdk.CollectionConverters._

object CassSessionInstance extends CassSession{

  private val (node :String,dc :String) = getNodeAddressDc("src")
  log.info("CassSessionInstance DB Address : "+node+" - "+dc)
  val sess :CqlSession = createSession(node,dc)
  log.info("CassSessionInstance session is connected = " + !sess.isClosed)

  private val prepTickersDict: BoundStatement = prepareSql(sess, sqlTickersDict)
  private val prepBCalcProps :BoundStatement = prepareSql(sess,sqlBCalcProps)
  private val prepLastTickDdate :BoundStatement = prepareSql(sess,sqlLastTickDdate)
  private val prepLastTickTs :BoundStatement = prepareSql(sess,sqlLastTickTs)

  def tickersDict: Seq[Ticker] = sess.execute(prepTickersDict).all().iterator.asScala
    .map(rowToTicker).toList.filter(tck => tck.tickerId < 30).sortBy(_.tickerId)

  def getAllBarsProperties : Seq[BarCalcProperty] = sess.execute(prepBCalcProps).all().iterator.asScala
    .map(rowToBarCalcProperty).toList.filter(bp => bp.isEnabled==1)

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







  /**
   * We need Leave only tickers for which exists bar property
   */
  /*
  def getTickersWithBws :Seq[TickerBws] =
    getFirstTicksMeta
      .flatMap(thisTicker =>
        getAllBarsProperties.collect{
          case bp if bp.tickerId == thisTicker.tickerId => TickerBws(thisTicker,bp.bws)
        }
      )
  */





  trait Factory {
    def apply(): CassSessionInstance.type
  }

}
