package workshop.example


import akka.actor.{Actor, ActorSystem, PoisonPill, Props}
import akka.pattern.ask
import akka.util.Timeout

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
import scala.language.postfixOps
import scala.concurrent.{Await, Future}
import scala.util.{Failure, Success}


object AkkaHelloWorld extends  App {
   // untyped actor
  class HelloActor extends Actor {
     println("hello actor created")

     // actor path, tree hierarchy , print the path
     // actor name is used at end path
     println("Actor path ", akka.serialization.Serialization.serializedActorPath(self))
     // every actor should have receive method
     // this function is called on every message
     // who will call this method, called by dispatcher
     // dispatcher will fetch message from mailbox, get a  thread from pool, call receive on the thread
     def receive = {
       // handle ask
           // sender represent who ask/send message
           // self is hello actor reference
       case "what is time now?" => sender.tell(System.nanoTime(), self)
           //handle tell
       case msg: String => println("message is", msg)
       case _ => println("there is a msg")
     }

     // life cycle method for actors
     // this method is called when the actor is created, before starting the actor
     // initialize your actor, connect db, create resources
     override def preStart(): Unit = {
       println("HelloActor preStart")
     }

     // called after actor is stopped
     // uninitialize memory, disconnect db, clean resources
     override def postStop() = {
       println("HelloActor postStop")
     }

     // called when the actor crashed with exceptions, and  then restarted after the error
     override def preRestart(reason: Throwable, message: Option[Any]): Unit = {
        println("HelloActor preRestart")
     }

     override def postRestart(reason: Throwable): Unit = {
       // to call base class
       super.postRestart(reason)
       println("HelloActor postRestart")
     }

   } // HelloActor

  // create actor system
  // every actor system will have name
  val system = ActorSystem("training")

  // create an actor with in system
  // name is used for actor name, in the path herarchy
  // akka://training/user/helloworld#966108051
  val helloActor =system.actorOf(Props[HelloActor], name="helloworld")

  // now we can send message t oactor
  // no response from actor
  // telling something to someone witout expecting response
  helloActor.tell("Hello Akka", null)

  // use ! to tell teh message   ! = tell
  helloActor ! "Hello Scala"

  // Ask, you ask someone, wait for response..until timeout
  // response can be sync or async

  implicit  val timeout = Timeout(5 seconds)
  // concurrent
  val future = ask(helloActor, "what is time now?")
 // blocking call, not good
  // we wait for reponse, meanwhile the main thread wait
  val result = Await.result((future), 2 second)
  println("Time is ", result)

  // Good non blocking wait
  //caller thread/main thread will not be blocked
  // symbol ? = ask we either use ? or ask
  val future2 = helloActor ? "what is time now?"
  future2.onComplete  {
    case Success(value) => println("time now " + value)
    case Failure(exception) => println("Got error ", exception)
  }

  val future3 = Future {
    Thread.sleep(10000)

    // 1. stop the actor , will trigger postStop
   // println("Stopping actor")
   // system.stop(helloActor)

    // 2. stop the actor by sending poisonPill message
    //println("Sending poinsonpill")
    //helloActor ! PoisonPill

    // 3. when the whole actor system terminated
    println("shutdown actor system")
    system.terminate()
  }
}
