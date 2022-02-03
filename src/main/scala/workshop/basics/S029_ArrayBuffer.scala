package workshop.basics

import scala.collection.mutable.ArrayBuffer

// mutable collection ArrayBuffer, IndexedSeq
object S029_ArrayBuffer extends App {
  // mutable, add, remove, update
  val products: ArrayBuffer[String] = new ArrayBuffer[String]()

  // add new entries
  products += "iPhone"
  products.insert(1, "Galaaxy")
  products += "Pixel"

  println(products)

  // how to access elements using index /IndexedSeq
  // O(1)
  println(products(1)) // Galaaxy

  // update teh value
  products.update(1, "Galaxy")
  println(products(1)) // Galaxy

  // how to remove
  products -= "Galaxy"
  products.remove(1) // Pixel

  println(products)

  //remove all elements
  products.clear()


}
