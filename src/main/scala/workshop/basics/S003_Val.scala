package workshop.basics

object S003_Val extends App {
  // value type, IMMUTABLE, once assigned, value/reference can't be changed
  // Static typing, compiler validate and enfore correct typing
  val PI: Double = 3.14
  println(PI)
  // PI = 2.14 // error

  // Type inference , scala compiler derive type from expression
  val k = 10 + 20
  // K is an Integer
  // k = "Welcome" // error since k is integer, welcome is string
}
