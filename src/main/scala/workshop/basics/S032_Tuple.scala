package workshop.basics

object S032_Tuple extends App {
  // tuple - collection of elements/pair of elements/related elements/used to represent fact
  // tuple - immutable
  // In Scala, tuple can be creatd using () , TupleN, ->
  // tuple has type

  val tuple0: Unit = () // tuple with no elelemt of type Unit / void
  val tuple1: Tuple1[Int] = Tuple1(10) // add extra , to represent tuple only for tuple1
  //  tuple memebrs can be accesible via _1, _2
  println(tuple1._1)
  val tuple2 = ("iPhone", 60000)
  println(tuple2._1, tuple2._2)

  // only for tuple2 ->
  val country: Tuple2[String, String] = "India" -> "IN" // ("India", "IN")


}
