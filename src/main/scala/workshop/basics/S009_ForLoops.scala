package workshop.basics

// scala has do..while, while, for loop
object S009_ForLoops extends App {
  // for [statement], for yield [expression]

  val range = 1.to(10) // collection
  val range2 = 1 to 10 // collection

  // for loop statement
  // single line
  for (i <- range) println(i)

  // multiline
  for (i <- range2) {
    println(i)
  }

  range2.foreach(println) // print the elements in println
}
