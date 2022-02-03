package workshop.example


import akka.actor.{Actor, ActorSystem, PoisonPill, Props}
import akka.pattern.ask
import akka.util.Timeout

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
import scala.language.postfixOps
import scala.concurrent.{Await, Future}
import scala.util.{Failure, Success}
import akka.cluster.{Cluster, Member, MemberStatus, ClusterEvent}


object AkkaClusterExample extends  App {
  println("hello actor created")

  class HelloActor extends Actor {
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


} // HelloActor

// create actor system
// every actor system will have name
val system = ActorSystem("training")

  // create a cluster

val cluster = Cluster(system)
}
