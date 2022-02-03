package workshop.basics

object S013_Options extends App {

  // if there is error in code, exception
  // on error, use default value, ignore the record etc

  // Option - get some result, else None

  def toInt(input: String): Option[Int] = {
    try {
      Some(input.toInt) // return Option[int] after converting string to int
    } catch {
      case _ => None
    }
  }

  // 10 20  NaN 30 - avg  (10 + 20 + 0 + 30) / 4 = 15
  // Problem domain wants to ignore NaN (10 + 20 + 30)/3 = 20
  val n = toInt("NaN")
  println("n is", n) // None
  if (n.isEmpty) println("N has no data")
  val m = toInt("100")
  println("M is ", m) // Some(100)
  if (m.isDefined) println("M has some data")

  // how to get data safely only if data is defined
  if (m.isDefined) {
    val r = m.get // returns 100 from option some
    println("Result is ", r)
  }

  val data = List("10", "20", "Nan", "30")

  val goodData = List("1", "2", "3")
  // every element from left to right "1", "2", "3"
  val finalResult = data.map(s => toInt(s)) // returns List[Option[Int]] Some(1), Some(2), Some(3)
    .filter(opValue => opValue.isDefined) // take only defiend values
    .map(opValue => opValue.get) // List[Int] 1, 2, 3
    .sum

  println("Sum is ", finalResult)

  for (result <- toInt("nan")) {
    println("Result is ", result)
  }
}
