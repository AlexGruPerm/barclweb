case class Ticker(tickerId :Int)

case class TickerWithDdateTs(ticker :Ticker, dbTsunx :Long, currTimestamp :Long )

case class LastBar(
                    tickerWithDts: TickerWithDdateTs,
                    isFail :Int
                  ){
  def getTickerId :Int = tickerWithDts.ticker.tickerId
}

case class TickerFailBwsCnt(tickerId  :Int,failCnt :Int)

val seqLastBars :Seq[LastBar] = Seq(
  LastBar(TickerWithDdateTs(Ticker(1),100L,100L),1),
  LastBar(TickerWithDdateTs(Ticker(1),100L,100L),0),
  LastBar(TickerWithDdateTs(Ticker(1),100L,100L),1),

  LastBar(TickerWithDdateTs(Ticker(2),100L,100L),0),
  LastBar(TickerWithDdateTs(Ticker(2),100L,100L),1),
  LastBar(TickerWithDdateTs(Ticker(2),100L,100L),0),

  LastBar(TickerWithDdateTs(Ticker(3),100L,100L),1),
  LastBar(TickerWithDdateTs(Ticker(3),100L,100L),0)
)

val res : Seq[TickerFailBwsCnt] =
  seqLastBars.map(_.getTickerId).distinct.map{
    thisTickerId => TickerFailBwsCnt(thisTickerId,seqLastBars
      .count(c => c.getTickerId==thisTickerId && c.isFail==1))
  }



//


/*
val res : Seq[TickerFailBwsCnt] =
  seqLastBars
    .map(lb => (lb.tickerWithDts.ticker.tickerId,lb.isFail))
    .groupBy(t => t._1)
    .collect { case grp => {
      val (groupId: Int, tf: List[(Int, Int)]) = grp
      TickerFailBwsCnt(tf.head._1, tf.count(t => t._2 == 1))}
    }.toSeq
*/
