package workshop.basics

object S005_Methods extends App {
  // methods are instance/object member function

  //Unit is return type (void)
  def show(): Unit = {
    // method body
    println("Hello Word")
  }

  def power(n: Int): Int = n * n // single line code, result of n * n is returned

  def add(a: Int, b: Int): Int = {
    // multiline
    a + b // no need to return keyword, last evaluated expression value shall be returned
  }

  show()
  println(power(5))
  println(add(10, 20))
}
