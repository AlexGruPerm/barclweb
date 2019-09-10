def toInt(s: String): Option[Int] = {
  try {
    Some(s.toInt)
  } catch {
    case e: Exception => None
  }
}

def calcSumSeq[A](seq1 :Seq[A]) :Int ={
  seq1.map(elm => elm match {
    case i:Int => i
    case s:String => toInt(s).getOrElse(0.toInt)
  }).sum
}

def calcSum[A,B](implicit seq1 :Seq[A],seq2 :Seq[B]) :Int ={
  calcSumSeq(seq1) + calcSumSeq(seq2)
}

println(calcSum(Seq(1,2,3),Seq(1,2,3)))
println(calcSum(Seq("1","2"),Seq("3","4")))
println(calcSum(Seq(1,"a"),Seq(2,"a")))

implicit val implSeq :Seq[Int] = Seq(1,2,3,4)

println(calcSum)