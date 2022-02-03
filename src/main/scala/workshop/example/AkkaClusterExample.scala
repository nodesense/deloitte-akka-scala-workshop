package workshop.example


import akka.actor.{Actor, ActorSystem, PoisonPill, Props}
import akka.cluster.ClusterEvent.{InitialStateAsEvents, MemberDowned, MemberRemoved, MemberUp}
import akka.pattern.ask
import akka.util.Timeout

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
import scala.language.postfixOps
import scala.concurrent.{Await, Future}
import scala.util.{Failure, Success}
import akka.cluster.{Cluster, ClusterEvent, Member, MemberStatus}


object AkkaClusterExample extends  App {


  class HelloActor extends Actor {
    println("hello actor created")
  println("Actor path ", akka.serialization.Serialization.serializedActorPath(self))

    // if the actors wants to receive notificaiton on member up, down, removed, events
    // we need to susbcribe from cluser
    // get reference to cluster
    val cluster = Cluster(context.system)

    def receive = {

    case MemberUp(m) => {
      println("New Member up ", m)
    }
    case MemberDowned(m) => {
      println("Member Down ", m)
    }
    case "what is time now?" => sender.tell(System.nanoTime(), self)
    //handle tell
    case msg: String => println("message is", msg)
    case _ => println("there is a msg")
  }

    override def preStart(): Unit = {
      super.preStart()
      println("HelloActor PresStart")

      // self mean, this /current actor/hello actor
      cluster.subscribe(self, initialStateMode = InitialStateAsEvents,
        classOf[MemberUp],
        classOf[MemberDowned],

        classOf[MemberRemoved],

      )
    }

} // HelloActor

val system = ActorSystem("training")

  // create a cluster

val cluster = Cluster(system)

  // we need to register the actors after cluster Member up
  cluster registerOnMemberUp {
     println("on register phase")
    // akka://training@127.0.0.1:2551/user/helloworld#-2137257253
     system.actorOf(Props[HelloActor], name="helloworld")
  }
}
