package workshop.basics

// App is trait, [java it is called interface]
// App already has main function
// Same like writing main function, this is most preferred method/style
object S002_AppTrait extends App {
  println("Hello World")
  println(args.length)
  println(args.toList)
}
