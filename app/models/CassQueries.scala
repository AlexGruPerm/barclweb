package models

trait CassQueries {

  val sqlTickersDict  = "select * from mts_meta.tickers"
  val sqlBCalcProps = "select * from mts_meta.bars_property"
  val sqlLastTickDdate = "select ddate from mts_src.ticks_count_days where ticker_id = :tickerId limit 1"
  val sqlLastTickTs = "select db_tsunx from mts_src.ticks where ticker_id = :tickerId and ddate= :pDdate limit 1"

}

