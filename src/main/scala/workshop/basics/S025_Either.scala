package workshop.basics

object S025_Either extends App {
  // Option: Some value or None {we don't error or we don't get alternative or default nothing}

  // Either: Right or Left
  // GET /products/10
  //      200 OK - good side
  //      403 - Forbidden [error part]
  //       404 - product not found etc
  // Either[Left, Right], but conventionally Left mean error side, right mean correct side
  def safeDiv(a: Int, b: Int): Either[String, Int] = if (b != 0) Right(a / b) else Left("Divide by Zero")

  val x = safeDiv(10, 0) //.. try passing 2 and 0
  println(x, x.isRight, x.isLeft)

  if (x.isRight) {
    println("Correct result is ", x.right.get) // get 5 printed
  } else {
    println("Wrong result ", x.left.get) // Divide by Zero
  }
  // try to change safeDiv with value 0 and 2 to see output
  // for comprehension for Either
  // for comprehension resolves Either, if Right has data, it execute the block
  // if left has data, it doesn't execute [it assumes that wrong result]
  for {result <- x} {
    println("Result is ", result)
  }

}
