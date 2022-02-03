package workshop.basics

// To run the app, Right click, clik Run your application name
object S001_Main {
  // Unit is return type, similar to void
  // Array[String] - Generic Data type, [ represent sub-type
  // arg is passed as input from command line
  def main(args: Array[String]): Unit = {
    // semi colon is optional
    println("Hello World")
    // to pass command line arg, use intelli configuration
    println(args.length)
    println(args.toList)
  }
}
