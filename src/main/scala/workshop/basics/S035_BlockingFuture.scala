package workshop.basics

import scala.concurrent.{Await, Future}

object S035_BlockingFuture extends App {

  // used by 'time' method
  implicit val baseTime = System.currentTimeMillis

  // 2 - create a Future / Task
  val f = Future {
    println("Future running")
    Thread.sleep(5000)
    println("future done")
    1 + 1
  }

  println("Main thread");
  // 3 - this is blocking (blocking is bad)
  // wait until we get result or max 10 secods
  val result = Await.result(f, 10 second)
  println(result)
  Thread.sleep(1000)

}
