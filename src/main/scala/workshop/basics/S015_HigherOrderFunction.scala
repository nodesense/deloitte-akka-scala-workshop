package workshop.basics

object S015_HigherOrderFunction extends App {
  // a Function that accept another function as parameter is known is higher order function

  // sum of sq of the numbers
  // sum of cube of the numbers
  // sum of tan/cosine of the number

  val sq = (d: Double) => d * d
  val cube = (d: Double) => d * d * d

  //func: Double => Double a Function1, accept 1 arg of Double, return Double type
  // Sum is called higher order function, which accept another function as input
  // for sum method, func is anabsctract, doesn't know what it does
  val sum = (numbers: List[Double], func: Double => Double) => {
    var result = 0.0
    for (n <- numbers) {
      result += func(n)
    }

    result // return resule
  }

  val data = List(2.0, 4.0, 6.0, 8.0)

  // sum of sq of numbers, we pass sq function as input
  println(sum(data, sq))
  println(sum(data, n => n * n))

  println(sum(data, cube))

  // Sum of log of number
  println(sum(data, Math.log))


}
