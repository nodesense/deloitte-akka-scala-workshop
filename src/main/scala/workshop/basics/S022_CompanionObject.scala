package workshop.basics

object S022_CompanionObject extends App {
  // in the same scala file, where Class name and Object name are same then the object is called companion object
  // companion object is useful to build object functionally

  class Brand(val id: Int, val name: String) {
    println("Brand created $id $name")
  }

  object Brand {
    def apply(id: Int, name: String) = new Brand(id, name)
  }

  // we create object using new keyword
  // new keyword is not an expression, it is not functional
  val b1 = new Brand(1, "Apple")

  // using companion object, objection creation made it like functional
  //  1. create object using apply function
  //  2. extract data out of object

  // let us create object without new keyword
  val b2 = Brand(2, "Samsung") // calls Brand.apply method internally

  println(b1.name)
  println(b2.name)

}
