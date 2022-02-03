package workshop.example


import akka.actor.{Actor, ActorRef, ActorSystem, PoisonPill, Props, Terminated}
import akka.cluster.ClusterEvent.{InitialStateAsEvents, MemberDowned, MemberRemoved, MemberUp}
import akka.pattern.ask
import akka.util.Timeout

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
import scala.language.postfixOps
import scala.concurrent.{Await, Future}
import scala.util.{Failure, Success}
import akka.cluster.{Cluster, ClusterEvent, Member, MemberStatus}
import akka.routing.RoundRobinGroup
import workshop.models.ClusterJob


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
      println("Role is ", m.roles)
    }
    case MemberDowned(m) => {
      println("Member Down ", m)
    }

    case MemberRemoved(m, s) => {
      println("MemberRemoved ", m, s)
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


  class MasterActor extends Actor {
    println("MasterActor created")
    println("Actor path ", akka.serialization.Serialization.serializedActorPath(self))

    val cluster = Cluster(context.system)
    // a parent actor can create child actor
    // master actor is parent, worker1 is child actor
    // let us create worker actor here

    // maintain index seq that can be muted when new cluster added/removed
    // IndexedSeq is immutable
    var backends =  IndexedSeq.empty[ActorRef]
    // routes shall ahve all the workers actor path in string format
    var routees = Set[String] ()

    var routes = buildRouter() // empty routes

    def buildRouter() = context.actorOf(RoundRobinGroup(routees).props)


//    // self mean, this /current actor/hello actor
//    cluster.subscribe(self, initialStateMode = InitialStateAsEvents,
//
//    )

    def receive = {
      case msg:String if msg == "RegisterWorker" => {
        println("RegisterWorker at master")
        backends = backends :+ sender() // add the workers in seq

        // workor actor path
        val workerPath = sender().path.toString()
        println("Adding worker to master/router ", workerPath)
        routees += workerPath

        routes = buildRouter() // update the routes with new worker added
        // here receive the worker information
       // backends = sender() // sender is the worker running on cluster 2
      }

      // TODO: when the member is terminaed removed from backends
      case Terminated(actorRef) => {
        println("Terminated Called, the actor removed is  ", actorRef.path.toString)
      }

      case job: ClusterJob =>  {
        //TODO: forward to worker running on cluster 2
         println("At master worker", job)

        //context.actorSelection("akka://training@127.0.0.1:2552/user/worker").forward(job)


        routes.forward(job)

      }
      case _ => println("default message")
    }
  }


  val system = ActorSystem("training")

  // create a cluster

val cluster = Cluster(system)

  // we need to register the actors after cluster Member up
  cluster registerOnMemberUp {
     println("on register phase")
    // akka://training@127.0.0.1:2551/user/helloworld#-2137257253
     system.actorOf(Props[HelloActor], name="helloworld")
    system.actorOf(Props[MasterActor], name="master")

  }

  Future {
    Thread.sleep(60 * 1000)
    for (jobCount <- List(100,200,300,400,500,600,700,800,900,1000)) {
      println("Sending job")
      system.actorSelection("akka://training@127.0.0.1:2551/user/master")
        .tell(ClusterJob(s"Print ${jobCount} pages"), null)

    }
    }
}
