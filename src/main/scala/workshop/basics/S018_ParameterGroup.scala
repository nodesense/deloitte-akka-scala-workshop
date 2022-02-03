package workshop.basics

object S018_ParameterGroup extends App {
  // parameter group is scala generated curry functions to reuse parameter, results
  // normal funciton, not a parameter group
  // add(10, 20, 30)
  def add(a: Int, b: Int, c: Int) = a + b + c

  // sum is a funciton, it has two nested curry  function inside
  // parameter group
  def sum(a: Int)(b: Int)(c: Int) = a + b + c

  // sum is a method, not a function, parameter group works with function
  // scala, we can convert method inot function using methodName _
  val sumF = sum _ // we are converting method inot function

  val sum10 = sumF(10) // a = 10 this returns a curry function that accept b as parameter
  val sum10Plus20 = sum10(20) // b = 20, this returns a curry function that accept c as parameter
  val result = sum10Plus20(30) // c = 30 , this return output a + b + c = 60
  println(result)

  println(sum10Plus20(60)) // 10 + 20 + 60 = 90

  // s"" - template string, useful to substitute values
  def heading(parent: String)(header: String)(title: String) = s"<$parent><$header>$title</$header></$parent>"

  // _ convert method into function
  def h1 = (heading _) ("div")("h1")

  def h2 = (heading _) ("div")("h2")

  println(h1("Welcome to Deloitte"))
  println(h2("Scala"))


}
