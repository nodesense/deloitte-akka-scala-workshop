package workshop.basics

object S020_Class extends App {
  // class is blueprint to create object
  // default scope is public
  class Product(val id: Int, val name: String, var price: Double) {
    // default constructor
    println("PRoduct Created $id $name $price")

    // member functions using def, java style
    def getPrice() = price // return price

    def setPrice(v: Double) = price = v // set price value

    var grandTotal = 0.0

    def calculate() = {
      val priceWithDiscount = price - price * discount / 100.0
      grandTotal = priceWithDiscount + priceWithDiscount * .18
    }

    // scala style getter and setter
    private var _discount = 0.0

    // getter
    def discount = _discount

    //setter _= is overload, NO space between _=
    def discount_=(value: Double) = _discount = value

  }

  // create object p1
  val p1 = new Product(1, "Apple iPhone", 60000)
  println(p1.id, p1.name, p1.price)
  p1.price = 61000
  println(p1.id, p1.name, p1.price)

  p1.calculate()
  println(p1.grandTotal)

  // how to call getter
  println(p1.discount) // calls discount getter
  // how to call setter
  p1.discount = 20

  p1.calculate()

  println(p1.grandTotal)


}
