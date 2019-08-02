import app.Global
import models._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent._
import scala.concurrent.duration._

  val sess = Global.sessInstance
  val tickersFrx: Seq[Ticker] = sess.tickersDict
  println("tickersFrx.size = "+tickersFrx.size)

  //Test 1 sequence execution.
  val t1 = System.currentTimeMillis
  val seqTickersDdateTs: Seq[TickerWithDdateTs] = tickersFrx
    .map(t => TickerWithDdateTs(t, sess.getTickerLastTs(t)))
    .sortBy(elm => elm.diffSeconds)(Ordering[Long])
  seqTickersDdateTs.foreach(t => println(t.toString))
  println(s"Duration sequential ${(System.currentTimeMillis-t1).toDouble/1000.toDouble} s.")

  //Test 2 with Futures.
  val t1f = System.currentTimeMillis
  val futs: Seq[Future[TickerWithDdateTs]] = tickersFrx
    .map(t => Future(TickerWithDdateTs(t, sess.getTickerLastTs(t))))
  val resSeq :Seq[TickerWithDdateTs] = Await.result(Future.sequence(futs), Duration.Inf)
    .sortBy(elm => elm.diffSeconds)(Ordering[Long])
  resSeq.foreach(t => println(t.toString))
  println(s"Duration with Futures ${(System.currentTimeMillis - t1f).toDouble / 1000.toDouble} s.")



