package workshop.basics

object S019_ByNameCalling extends App {
  // statement/expression/block as block of code which can be lazily executed
  // return nano time
  def nano() = {
    println("Nano called")
    System.nanoTime()
  }

  //t: => Long , t is a block/expression that return a Long data value, t is not value
  // t is lazi evaluated/executed only when used
  def delayed(t: => Long) = {
    println("Delayed called")

    // now we shall use the t which will evaluate nano() or {println("Expression alled")  100}
    val result = t // evaluating expression now
    println("After evaluating t ", result)
  }

  // we expect, nano to be called first, get long value and pass the long value to delayed but THAT IS NOT TRUE HERE
  delayed(nano())

  // expression that return long value
  delayed({
    println("expression called out")
    100 // return value
  })


}
