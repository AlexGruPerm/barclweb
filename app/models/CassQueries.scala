package models

trait CassQueries {

  val sqlTickersDict  = "select * from mts_meta.tickers"
  val sqlBCalcProps = "select * from mts_meta.bars_property"
  val sqlLastTickDdate = "select ddate from mts_src.ticks_count_days where ticker_id = :tickerId limit 1"
  val sqlLastTickTs = "select db_tsunx from mts_src.ticks where ticker_id = :tickerId and ddate= :pDdate limit 1"

  val sqlBarsBwsCodeStats = "select * from mts_bars.bars_bws_dates where ticker_id = :tickerID and bar_width_sec=:bws limit 1"

  val sqlBarByDateTs =
    """select ticker_id,ddate,bar_width_sec,ts_begin,ts_end,btype,o,c,ticks_cnt
      |  from mts_bars.bars
      |  where ticker_id=:tickerID and
      |        bar_width_sec=:bws and
      |        ddate=:pDdate and
      |        ts_end=:ts """.stripMargin

  val sqlSaveStatComm =
    """insert into mts_web.comm(ddate,ts,uid,uip,rpath,cntrl,mth)
      |         values(toDate(now()), toUnixTimestamp(now()),:uid,:uip,:rpath,:cntrl,:mth) """.stripMargin

  val sqlSaveStatDdate = "update mts_web.ddate_stat set cnt=cnt+1 where ddate=toDate(now()) "

  val sqlSaveStatUid = "update mts_web.uid_stat set cnt=cnt+1 where uid=:uid and ddate=toDate(now()) "

  val sqlSaveStatRPath = "update mts_web.rpath_stat set cnt=cnt+1 where rpath=:rpath and ddate=toDate(now()) "

  val sqlSeqSimpleBars = """   select ts_end,o,h,l,c
                           |     from mts_bars.bars
                           |    where ticker_id         = :tickerID and
                           |              ddate         = :pDdate and
                           |              bar_width_sec = :bws
                           |   limit :plimit """.stripMargin

}

