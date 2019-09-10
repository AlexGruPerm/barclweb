
class Engine(engineHP :Int) {
  def getEngineHP = engineHP
}

class Car(model :String, engineHP :Int,yearP :Int) extends Engine(engineHP :Int){
  def getModel :String = model
  def getyear :Int = yearP
}

case class People(name :String, car :Car)

val seqPeople :Seq[People] = Seq(
  People("John",new Car("bmw",1000,2012)),
  People("Bob",new Car("porche",800,2013)),
  People("Poll",new Car("mazda",1200,2012)),
  People("Mark",new Car("mercedes",500,2013)),
  People("Stiv",new Car("toyota",100,2012)),
  People("Alex",new Car("fiat",2000,2013))
)

/*
//not working case.
*/
implicit def customOrdering[A <: People]: Ordering[A]  =
  Ordering.by((a: A) => (a.car.getyear, a.car.getEngineHP))(
    Ordering.Tuple2(Ordering.Int.reverse, Ordering.Int))

seqPeople.sortBy(p => p)
  .foreach(e =>
   println(e+"  "+e.car.getyear+" - "+e.car.getEngineHP))
