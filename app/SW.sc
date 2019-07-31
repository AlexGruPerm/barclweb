

val menuCont :Seq[(Int,String)] = Seq(
  (1,"Menu1"),
  (2,"Menu2"),
  (3,"Menu3"),
  (4,"Menu4"),
  (5,"Menu5")
)

for ((k,v) <- menuCont.sortBy(elm => elm._1)){
  if (k==1){
    println("div " +  v )
  } else {
    println("div a " +  v )
  }
}
