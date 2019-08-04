package models

trait CassQueries {

  val sqlTickersDict  = "select * from mts_meta.tickers"
  val sqlBCalcProps = "select * from mts_meta.bars_property"
  val sqlLastTickDdate = "select ddate from mts_src.ticks_count_days where ticker_id = :tickerId limit 1"
  val sqlLastTickTs = "select db_tsunx from mts_src.ticks where ticker_id = :tickerId and ddate= :pDdate limit 1"

  /**
    * ticker_id | bar_width_sec | ddate      | curr_ts       | cnt | ts_end_max    | ts_end_min
  */
  val sqlBarsBwsCodeStats = "select * from mts_bars.bars_bws_dates where ticker_id = :tickerID and bar_width_sec=:bws limit 1"

  val sqlBarByDateTs =
    """select ticker_id,ddate,bar_width_sec,ts_begin,ts_end,btype,o,c,ticks_cnt
      |  from mts_bars.bars
      |  where ticker_id=:tickerID and
      |        bar_width_sec=:bws and
      |        ddate=:pDdate and
      |        ts_end=:ts
      |        allow filtering """.stripMargin

}

