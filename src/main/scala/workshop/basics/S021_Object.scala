package workshop.basics

object S021_Object extends App {
  // object is an instance
  // typically used to represent static class
  // objects are singleton , only one instance

  object Logger {
    // object body, shall be called to initialize object, lazy
    // invoked when object used first time
    println("Logger initialize")

    // object member variable
    var level = 0

    // object member function
    def debug(msg: String) = println(msg)
  }

  // using object first call object body one time
  Logger

  Logger.level = 1 // access member variable
  Logger.debug("app starting") // call object method
}
