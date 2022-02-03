package workshop.basics

object S012_TryCatchFinally extends App {
  // java, python try catch
  // in scala, we have try catch,,,,, try catch are expression, return results

  val result = try {
    42 / 0 // throw exception
    // 42 / 2  // returns 21
  } catch {
    case e: ArithmeticException => -2 // return -2 if the exception is Arithmetic exception
    // default exception handler
    case _ => -1 // if there is exception, it is capturd here, we return -1
  } finally {
    // doesn't return any output, to ensure that resource cleanup
    // called if there is exception
    // called if there is no exception
    println("at finally")
  }

  println("Result is ", result)

  println("print at last")

}
