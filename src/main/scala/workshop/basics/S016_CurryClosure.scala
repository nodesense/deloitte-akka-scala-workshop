package workshop.basics

object S016_CurryClosure extends App {
  // Curry func - a function that returns another function is called curry function
  // Closure -a variable/parameter/value which life exist even after the function call completed - visiblity

  // seq generator, that generate seq like 1, 2,3,4,5 or 1, 3, 5, 7, ....
  val seq = (start: Int, step: Int) => {
    // closure, functional state, the value of closure remain in memory even after function existed
    // visibility, since currVal is referenced in generator, and generator function returned back to called
    // seq1By2, seq100by10
    var currVal = start

    val generator = () => {
      println("Generator called")
      val presentValue = currVal
      currVal += step
      presentValue // return value
    }

    generator // we are returning a function
  }

  //seq1By2 is a function returned by seq
  val seq1By2 = seq(1, 2)
  val seq100by10 = seq(100, 10)

  println(seq1By2()) // calls generator function, 1
  println(seq1By2()) // calls generator function, 3
  println(seq1By2()) // calls generator function, 5

  println(seq100by10()) // calls generator function, 100
  println(seq100by10()) // calls generator function, 110

}
