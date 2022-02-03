package workshop.basics

object S024_Implicits extends App {
  // our code is expressed as explicit
  // implicit - compiler takes care of passing certain parameter values, caling conversion function automatically
  // implicit works based on scope, obejct, class or import scope

  {
    // example 1
    // implicit match based on type
    implicit def dtoi(v: Double): Int = v.toInt

    // block scope
    // error by default howerver implict helps compiler to call functions based on data type to convert data
    val d: Int = 45.5 // calls dtoi impplicitly by compiler
  }

  {
    //second example
    class Config(val endPoint: String)

    def startServer(implicit config: Config) = {
      println("Starting server at ", config.endPoint)
    }

    implicit val c = new Config("http://localhost:8080")
    startServer // now config c is passed to startServer method automatically by compiler
  }
}
