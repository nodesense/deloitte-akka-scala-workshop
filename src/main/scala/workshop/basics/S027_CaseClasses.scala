package workshop.basics

object S027_CaseClasses extends App {
  // case class used to represent fact
  // case class objects are immutable, once created we cannot modify
  // id and amount are member variable,
  // Case class companion object inbuilt, no need to use new keyword
  case class Order(id: Int, amount: Double)

  val order1 = Order(1, 1000.0)
  val order2 = Order(2, 2000)

  println(order1)
  println(order2)
}
