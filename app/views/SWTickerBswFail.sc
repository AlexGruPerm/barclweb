import models._

val seqTickersBws :Seq[LastBar]

for(thisTickerId <- seqTickersBws.map(tb => tb.tickerWithDts.ticker.tickerId).distinct){
  println(  seqTickersBws.count(elm => elm.tickerWithDts.ticker.tickerId==thisTickerId && elm.isFail==1)   )
}