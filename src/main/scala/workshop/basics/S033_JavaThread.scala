package workshop.basics

import scala.util.control.Breaks._

class OrderProcessor extends Thread {
  override def run() {
    // therad logic
    println("Worker thread ", Thread.currentThread().getId)
    // runs forever
    breakable {
      while (true) {
        println("Long running queue");
        Thread.sleep(5000) // should not use sleep, that will pasue the thread, 5 sec sleep
        break;
      }
    }

    println("Done with thread");

  }
}

object S033_JavaThread extends  App {

  println("Main Thread", Thread.currentThread().getId)

  // write thread code, start thread
  val thread = new OrderProcessor()
  thread.start()
  // waits for the thread to finish
  println("Waiting for thread to complete..")
  thread.join() // wait for thread to complete

}