package workshop.example


import akka.actor.{Actor, ActorSystem, PoisonPill, Props}
import akka.pattern.ask
import akka.util.Timeout

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
import scala.language.postfixOps
import scala.concurrent.{Await, Future}
import scala.util.{Failure, Success}


object AkkaParentChildExample extends  App {
  // how forward works
  // supervisor

  // MasterActor[ParentActor], WorkerActor [ChildActor]
  // MasterActor receive a job, forward to WorkerActor to get the work done
  case class Job(name: String)
  case class JobCompleted(msg: String)
  case class StopWork() // case object is better

  class MasterActor extends Actor {
    println("MasterActor created")
    println("Actor path ", akka.serialization.Serialization.serializedActorPath(self))

    // a parent actor can create child actor
    // master actor is parent, worker1 is child actor
    // let us create worker actor here
    val worker1 = context.actorOf(Props[WorkerActor], "worker1")

    def receive = {
      case job: Job => worker1.forward(job)
      case _ => println("default message")
    }
  }

  class WorkerActor extends Actor {
    println("WorkerActor Created")
    println("Actor path ", akka.serialization.Serialization.serializedActorPath(self))

    def receive = {
      case job: Job => {
        println("Job received by worker ", job)
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

  implicit  val timeout = Timeout(5 seconds)
  // now we ask master, the master forward the job to worker,
  // worker should respond back with answer JobCompleted
  val future = ask(masterActor, Job("Print 200 pages"))

  val result = Await.result(future, 2 second)
  println("Result ", result)
}
