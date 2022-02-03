package workshop.basics

object S011_MatchCase extends App {
  // match, case, -- expression that return output
  // in java/c++, switch case - statement, in scala no switch case

  val power = (n: Int) => n * n
  val result = 11 % 3 match {
    case 0 => "Even" // return value
    case 1 => "Odd" // return
    case _ => "Unknown" // default  // return
  }

  println(result)
}
