package workshop.basics

object S004_Variable extends App {
  //variable allow the values/reference to be changed
  var k: Int = 10
  var j = 20

  k += 1
  println(k)

  // in SCALA, the primitives [Boolean, Int, Float, Double, char..] are treated as class instance
  // by scala compiler
  // at runtime, primives are JVM primitive types [bool, int, float, ..]

  // +, - /, % etc are functions in Scala , they are member of type Int
  k = k.+(1) // k += 1
  k = k.*(2) // k = k * 2


}
