package models

import java.time.LocalDate

case class Ticker(tickerId :Int, tickerCode :String, tickerFirst :String, tickerSeconds :String) {
  override def toString = tickerCode + " [" + tickerId + "] "+tickerFirst+" / "+tickerSeconds
}

case class TickerWithDdateTs(ticker :Ticker, dbTsunx :Long, currTimestamp :Long ) extends CommonFuncs{
  val lastTickDateTime = getDateAsString(convertLongToDate(dbTsunx))
  val diffSeconds = currTimestamp/1000L - dbTsunx/1000L
}



case class BarCalcProperty(tickerId  :Int,
                           bws       :Int,
                           isEnabled :Int)

case class TickerBws(
                      ticker :Ticker,
                      bws    :Int
                    )


case class LastBar(
                    tickerWithDts: TickerWithDdateTs,
                    currTimestamp :Long,
                    ddate: LocalDate,
                    bws: Int,
                    tsBegin: Long,
                    tsEnd: Long,
                    bType: String,
                    o: Double,
                    c: Double,
                    ticksCnt: Int
                  ) extends CommonFuncs{
  val lastBarDateTime = getDateAsString(convertLongToDate(tsEnd))
  val diffSecondsToCurr = currTimestamp/1000L - tsEnd/1000L
  val diffSecondsToLastTick = tickerWithDts.dbTsunx/1000L - tsEnd/1000L
  val isFail :Int = if (diffSecondsToLastTick > bws) 1 else 0
  def getTickerId :Int = tickerWithDts.ticker.tickerId
}

case class TickerFailBwsCnt(tickerId  :Int,failCnt :Int)

case class clientRow(
                      clientSessionId: String,
                      remoteAddress: String,
                      routerPath: String,
                      routerController: String,
                      routerMethod: String,
                    )
