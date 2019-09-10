class Animal(aview :String){
  def getAView = aview
}

class RunningAnimal(aview :String, speed :Int, name :String) extends Animal(aview :String){
  def getSpeed = speed
  def getIntern = "running"
}

class SwimmingAnimal(aview :String, freq :Int, name :String) extends Animal(aview :String) {
  def getFreq = freq
  def getIntern = "swimming"
}

val seqRunAnimals :Seq[Animal] = Seq(
  new RunningAnimal("force",200,"tiger"),
  new RunningAnimal("force",150,"lion"),
  new RunningAnimal("force",250,"panther"),
  new RunningAnimal("slow",20,"dog"),
  new RunningAnimal("slow",8,"cat"),
  new SwimmingAnimal("fast",freq = 400, name ="shark"),
  new SwimmingAnimal("fast",freq = 300, name ="tune"),
  new SwimmingAnimal("slow",freq = 3, name ="red fish")
)

//T <: A declares that type variable T refers to a subtype of type A
def getgrpSizes[T <: Animal](seqAnimals :Seq[T]) :Seq[(String,Int)] ={
  seqAnimals.groupBy(a => a.getAView)
  .map{
    case (key, value :List[T]) => (key, value.size)
    case (key, _) => (key, 0)
  }.toSeq
}

println(getgrpSizes(seqRunAnimals))

//------------------------------------------------------------------------

def getSpeedFreq[A](animal :A) :Int ={
  animal match {
    case a:RunningAnimal => a.getSpeed
    case a:SwimmingAnimal => a.getFreq
    case _ => 0
  }
}


