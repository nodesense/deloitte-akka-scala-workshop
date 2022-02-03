package workshop.basics

object S017_PartialFunctions extends App {
  // functions, reusable code with arg, return value
  // pre-conditions
  // when we invoke function, we call parameter which may be right or wrong
  // read a file which is not exist, divide where as dividend is 0
  // precondition if the paramter right like file exist or not, dividdend is 0 or not
  // with functions, first the function is invoked, then only preconditions are tested

  // PARTIAL FUNCTION
  // what if the caller of the function check if the parameters that to be passed are right
  // before even calling function

  val divide = new PartialFunction[Int, Int] {
    // your function/Business logic
    def apply(x: Int) = 42 / x

    // pre-condition , this func to be called to know whether should accpet arg x or not
    //return boolena, if true - apply can be called,
    //                if false - apply cannot be called
    def isDefinedAt(x: Int) = x != 0
  }

  println(divide.isDefinedAt(2)) // true
  println(divide.isDefinedAt(0)) // false

  if (divide.isDefinedAt(2)) println(divide(2))

  // we have shorter approch to write parition functions
  // we can use case
  // scala will automatically write isDefined at
  val divide2: PartialFunction[Int, Int] = {
    case d: Int if d != 0 => 42 / d

  }

  println(divide2.isDefinedAt(2)) // true
  println(divide2.isDefinedAt(0)) // false

  if (divide2.isDefinedAt(2)) println(divide2(2)) // 21

  // there are few scala functions which respect paritial functions
  val list: List[Double] = List(4, 16, 25, -9)

  val squareRoot: PartialFunction[Double, Double] = {
    case d: Double if d > 0 => Math.sqrt(d)
  }

  // cause exception, -9 is not allowed for sqrt, map will not call isDefinedAt
  //val result = list.map(squareRoot) // this will not call isDefinedAt
  //println("List with map ", result)

  val result2 = list.collect(squareRoot) // will call isDefinedAt
  println("List with collect ", result2)
}
