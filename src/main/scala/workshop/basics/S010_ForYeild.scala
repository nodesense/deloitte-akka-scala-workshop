package workshop.basics

object S010_ForYeild extends App {
  // for yeild an expression that returns output

  // if condition in yield, guard condition
  // return only even numbers
  val evenNumbers = for (n <- 1 to 10 if n % 2 == 0) yield n
  println("Even ", evenNumbers)

  val mulby10 = for (n <- 1 to 5) yield n * 10 // for each number, multiply by 10
  println("* 10 ", mulby10)

  val names = List("Scala", "java", "akka")
  val upperCases = for (name <- names) yield name.toUpperCase
  println("upper case ", upperCases)


  // for yield with nested loops
  // (n, c) represt a tuple, pair of elements
  val result = for {
    n <- 1 to 3 if (n != 2)
    c <- Seq('a', 'b', 'c') if (c != 'c')
  } yield (n, c)

  println(result)


}
