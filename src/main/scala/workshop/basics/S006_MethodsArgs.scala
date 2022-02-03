package workshop.basics

object S006_MethodsArgs extends App {
  // Default args
  def add(a: Int, b: Int = 0) = a + b

  def mul(a: Int, b: Int) = a * b
  // args passed from left to right
  println(add(10, 20)) // a = 10. b = 20
  // default arg
  println(add(10)) // a = 10, b= 0

  // named arguments
  println(add(b = 5, a = 4)) // 9
  println(mul(b = 10, a = 5)) // 50

  // variables number of arguments var args
  def printAll(names: String*) = {
    names.foreach(println) // iterator that print all elements in name
  }

  printAll() // names is immutable array, empty list
  printAll("Scala") // passing one arg, names shall have 1 element "Scala"
  printAll("Scala", "Akka", "Play") // passing 3 args, names shall have Scala Akka Play

  def sum(numbers: Int*) = {
    var r = 0
    for (n <- numbers) {
      r += n
    }

    r // return result
  }

  println(sum()) // numbers is empyty, 0
  println(sum(10)) // numebrs with 1 element, return 10
  println(sum(10, 20, 30, 40)) // numebrs with 4 elements, return 100

}
