package workshop.example

import akka.actor.{Actor, ActorSystem, Props, RootActorPath}
import akka.cluster.Cluster
import akka.cluster.ClusterEvent.{InitialStateAsEvents, MemberDowned, MemberRemoved, MemberUp}
import com.typesafe.config.ConfigFactory
import workshop.models.{ClusterJob, ClusterJobCompleted}

import scala.language.postfixOps


object AkkaCluster2Example extends  App {


  println("Application config path ", args)

  val configPath = if (args.length == 0) "application2" else args(0)
  println("Starting worker cluster with config ", configPath)

  class WorkerActor extends Actor {
    println("WorkerActor Created")
    println("Actor path ", akka.serialization.Serialization.serializedActorPath(self))

    // self mean, this /current actor/hello actor
    cluster.subscribe(self, initialStateMode = InitialStateAsEvents,
      classOf[MemberUp],
      classOf[MemberDowned],

      classOf[MemberRemoved],

    )



    def receive = {
      case MemberUp(m) => {
        println("New Member up ", m)
        println("Role is ", m.roles)
        // good practice
        // publish worker [backend] address to frontend
        val actorPath = akka.serialization.Serialization.serializedActorPath(self)

        if (m.roles.contains("frontend")) {
           // we don't have actor reference..
          println("Root Actor Path ", RootActorPath(m.address))
          val targetMasterPath = RootActorPath(m.address) / "user" / "master"
          println("Master Actor Path is ", targetMasterPath)
          // now send message to master work dynamically

          //context.actorSelection("akka://training@127.0.0.1:2551/user/master").tell("RegisterWorker", self)

          context.actorSelection(targetMasterPath).tell("RegisterWorker", self)
        }

      }

      case job: ClusterJob => {
        println("Job received by worker ", job)
        sender.tell(ClusterJobCompleted(s"Done ${job.name}"), self)
      }
      case _ => println("Worker default message")
    }

    override def preStart(): Unit = {
      println("Cluster 2 Worker preStart")
      // publish the message that contains the address of the worker
      // BUGGY- preStart called only once, the node join later will not receive address
    }
  }


  // we should not reference application.conf ie cluster config
  // we should refere application2.conf

val config = ConfigFactory.load(configPath)
val system = ActorSystem("training", config)

  // create a cluster

val cluster = Cluster(system)

  // we need to register the actors after cluster Member up
  cluster registerOnMemberUp {
     println("on register phase")
    system.actorOf(Props[WorkerActor], name="worker")

  }
}
