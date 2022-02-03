package workshop.basics

// shall be discussed after basic collection
object S028_Generic extends App {
  // Generic is type safe, substitute subtype
  class Stack[T] {
    var elems: List[T] = Nil // immutable list

    def push(x: T) = elems = x :: elems

    def top() = elems.head

    def pop() = elems = elems.tail // everything except head

    def isEmpty = elems.isEmpty
  }

  case class Car(id: String)

  // stack of car
  val cars: Stack[Car] = new Stack
  val strings: Stack[String] = new Stack

  cars.push(Car("312321"))
  cars.push(Car("212321"))
  println(cars.top())


}
