package workshop.basics

object S023_FunctionCompose extends App {
  // f(g(x))
  // which is executed first, g(x), return value of g(x) passed as argument to f(x) as input
  // compose is a feature, it basically compose functions together

  // f compose g => f(g(x)) what happens here g(x) executed
  // f andThen g => g(f(x)) what happens here f(x) executed first

  val f = (x: String) => s"f($x)"
  val g = (x: String) => s"g($x)"

  val fComposeG = f compose g // this create a new function f(g(x))

  println(fComposeG("x")) // f(g(x))

  val fAndThenG = f andThen g // g(f(x))
  println(fAndThenG("x")) // g(f(x))


  val gst = (v: Double) => v + v * .18
  val discount = (v: Double) => v - v * .10
  val price = (v: Double) => v

  val c = price compose gst compose discount
  println(c(100))
}
