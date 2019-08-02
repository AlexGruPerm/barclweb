package models

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
