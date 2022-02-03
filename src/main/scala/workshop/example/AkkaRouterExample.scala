package workshop.example

import akka.actor.{Actor, ActorSystem, PoisonPill, Props}
import akka.pattern.ask
import akka.util.Timeout

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
import scala.language.postfixOps
import scala.concurrent.{Await, Future}
import scala.util.{Failure, Success}
import akka.routing.{RoundRobinGroup, RoundRobinPool}
import workshop.example.AkkaRouterExample.masterActor

object AkkaRouterExample extends  App {

  // MasterActor[ParentActor], WorkerActor [ChildActor]
  // MasterActor receive a job, forward to WorkerActor to get the work done
  case class Job(name: String)
  case class JobCompleted(msg: String)
  case class StopWork() // case object is better

  class MasterActor extends Actor {
    println("MasterActor created")
    println("Actor path ", akka.serialization.Serialization.serializedActorPath(self))

    // a parent actor can create child actor

    // creating 3 worker actors as child, which will be routed
    val router = context.actorOf( RoundRobinPool(3).props(Props[WorkerActor]))

    def receive = {
          // rouer forward/tell/ask will eb send to one of 3 workers mentioned above
      case job: Job => router.forward(job)
      case _ => println("default message")
    }
  }

  class WorkerActor extends Actor {
    println("WorkerActor Created")
    println("Actor path ", akka.serialization.Serialization.serializedActorPath(self))

    def receive = {
      case job: Job => {
        println(s"Job received by worker ${akka.serialization.Serialization.serializedActorPath(self)} ", job)
        sender.tell(JobCompleted(s"Done ${job.name}"), self)
      }
      case _ => println("Worker default message")
    }
  }

  val system = ActorSystem("training")

  // create master worker
  val masterActor = system.actorOf(Props[MasterActor], name="master")

  // we send message to master, master forward to worker
  masterActor.tell(Job("Print 100 pages"), null)
  masterActor.tell(Job("Print 101 pages"), null)
  masterActor.tell(Job("Print 102 pages"), null)
  masterActor.tell(Job("Print 103 pages"), null)
  masterActor.tell(Job("Print 104 pages"), null)

}
