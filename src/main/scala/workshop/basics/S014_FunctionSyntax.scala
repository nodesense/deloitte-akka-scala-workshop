package workshop.basics

object S014_FunctionSyntax extends App {
  // FunctionN - N - 0 to 22, N basically define number of args to function

  // We use lambda => which is commmon way to create function
  // compiler will convert your function into Function N

  // function0 with no arg, no return [Unit]
  // the last type in [] is return value
  val show = new Function0[Unit] {
    def apply(): Unit = println("Hello")
  }

  show() // internally calls show.apply()
  show.apply()

  // function0 with no arg, return string
  val version = new Function0[String] {
    def apply(): String = sys.props("java.version")
  }

  println(version())
  println(version.apply())

  // function1 with 1 arg of int type, return Int type
  val power = new Function1[Int, Int] {
    def apply(n: Int): Int = n * n
  }

  println(power(5)) //25

  // 3 mins
  // Write Function2[Int, Int, Int, Int], take 2 numbers as input and reutrn a + b
  val add = new Function2[Int, Int, Int] {
    def apply(a: Int, b: Int): Int = a + b
  }

  println(add(10, 20))
}
