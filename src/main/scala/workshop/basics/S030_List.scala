package workshop.basics

object S030_List extends App {
  // List, LinearSeq, immutable
  val products = List("iPhone", "Galaxy", "Pixel")
  val products2 = List("Realme", "redme")

  println(products.size)
  println("Head", products.head)
  println("Tail ", products.tail)

  // concatenation ::: concat two list
  val allProducts = products ::: products2

  println(allProducts)


  // :: adding element into another element
  val products3 = "Nokia" :: products
  println(products3)

}
