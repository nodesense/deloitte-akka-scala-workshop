package workshop.basics

// In Scala if is an expression that returns value
object S007_If extends App {
  // if expression return Even or Odd based on conditions
  val result = if (10 % 2 == 0) "Even" else "Odd"
  println(result)

  val result2 = if (10 > 5) {
    println("I am good, 10 is bigger")
    10 // return value
  } else {
    println("5 is big")
    5 // return value
  }

  println(result2)

  // returns either Odd or Even
  def oddOrEven(n: Int) = if (n % 2 == 0) "Even" else "Odd"

  println(oddOrEven(10))
  println(oddOrEven(11))

}
