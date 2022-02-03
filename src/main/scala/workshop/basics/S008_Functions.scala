package workshop.basics

object S008_Functions extends App {
  // Functions are first class citizen in scala
  // functions are objects, we can pass as arg, return as value,
  // => thick/fat arrow , often called lambda in java/c#
  // annonymouse, no name for function, only references

  // no arg, no output
  val show = () => println("Hello")
  // 1 arg, 1 output , returns sq of number
  val power = (n: Int) => n * n

  //2 args, 1 return value
  val add = (a: Int, b: Int) => {
    // multi line
    a + b // return value
  }

  show()

  println(power(5))
  println(add(10, 20))

  // functions calling is a syntatic sugar in scala
  // scala generate code behind functions
  // every function has apply method
  // power(5) is calling power.apply(5)

  println(power.apply(5)) // 25
  println(add.apply(10, 20)) // 30
  show.apply() // print Hello


}
