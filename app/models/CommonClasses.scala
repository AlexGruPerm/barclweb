package models

case class Ticker(tickerId :Int, tickerCode :String, tickerFirst :String, tickerSeconds :String) {
  override def toString = tickerCode + " [" + tickerId + "] "+tickerFirst+" / "+tickerSeconds
}

case class BarCalcProperty(tickerId  :Int,
                           bws       :Int,
                           isEnabled :Int)

case class TickerBws(
                      ticker :Ticker,
                      bws    :Int
                    )
