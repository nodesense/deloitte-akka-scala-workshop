package workshop.basics

import scala.concurrent.Future
import scala.util.{Failure, Random, Success}
object S034_FutureBasicsApp extends App {
  implicit val ec: scala.concurrent.ExecutionContext = scala.concurrent.ExecutionContext.global

  val r = new Random()

  case class User(name: String)


  // we will get user from DB, we use random number for example
  // if random is even, we get user, else we get exception
  def getUser() = {
    if (r.nextInt(1000) % 2 == 0)
      User("Krish")
    else throw new Exception("Ooh odd number, no user now")
  }

  println("Main thread id ", Thread.currentThread().getId)
  // The code in future block is executed inside thread pool
  val future = Future {
    Thread.sleep(3000)
    println("Ftuture Worker id ", Thread.currentThread().getId)
    getUser()
  }

  //non blocking, the main thread continue its own work..
  // async, afterh future is executed, based on result, we will get a callback onComplete
  future.onComplete {
    // called when the future completed without error
    case Success(user) => println("yay!", user)
    // called when future completed with error/exception
    case Failure(exception) => println("On no!", exception)
  }

  println("almost at end.....")

  Thread.sleep(10000)

}
