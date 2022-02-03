package workshop.basics

import scala.util.Try

object S026_TrySuccessFailure extends App {
  // helper to simplify try catch
  // Try return Sucess if no error, else it return Failure
  def safeDiv(a: Int, b: Int) = Try(a / b)

  val x = safeDiv(10, 2)
  println("x", x)
  if (x.isSuccess) {
    println("X result ", x.get) // 5
  }

  val y = safeDiv(10, 0)
  println("y", y)
  //
  if (y.isFailure) {
    println("Failed ", y.failed.get)
  }

  // for comprehension
  for {result <- x} {
    println("Result in for comprehension ", result)
  }

  // won't be working for y failure
  for {result <- y} {
    println("Error may not print ", result)
  }


}
