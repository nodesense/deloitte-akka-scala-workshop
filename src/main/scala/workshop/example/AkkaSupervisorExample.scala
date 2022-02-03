package workshop.example

import akka.actor.{Actor, ActorSystem, PoisonPill, Props}
import akka.pattern.ask
import akka.util.Timeout

import java.security.InvalidParameterException
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
import scala.language.postfixOps
import scala.concurrent.{Await, Future}
import scala.util.{Failure, Success}

object AkkaSupervisorExample extends  App {
  // Supervisor is an actor, responsiblity include to start/stop/restart child actor/actors in general
  // Fault Tolerance

  // MasterActor[ParentActor], WorkerActor [ChildActor]
  // MasterActor receive a job, forward to WorkerActor to get the work done
  case class Job(name: String)
  case class JobCompleted(msg: String)
  case class StopWork() // case object is better

  class MasterActor extends Actor {

    // if one actor throw exception, supervisor want to restart/stop/resume...it affect only that actor
    // if supervisor restart that actor, only that actor restarted
    import akka.actor.OneForOneStrategy
    // if one actor throw exception, supervisor want to restart, it affect all the actors
    // restarts all actor
    import akka.actor.AllForOneStrategy
    import akka.actor.SupervisorStrategy._
    import scala.concurrent.duration._

    override  val supervisorStrategy = OneForOneStrategy(maxNrOfRetries = 3, withinTimeRange = 1 minute) {
      // what are exceptions, how to capture them, what should be remedy for this error
      case _: InvalidParameterException => {
        println("Ooh invalid parameter exception")
        // what should happen now, do you want to stop actor or resume, report parent, restart
        // try to enable one of below
       // Resume // continue running worker
       // Restart // restart the worker which g ot exception/error
       // Stop // stop the worker
        Escalate // escalate to top parent in hierarchy
      }
    }


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
        if (job.name.contains("Scan"))
          throw new InvalidParameterException("I Can't scan")
          else
          sender.tell(JobCompleted(s"Done ${job.name}"), self)
      }
      case _ => println("Worker default message")
    }

    override def preStart(): Unit = {
      println("WorkerActor preStart")
    }

    // called when the actor crashed with exceptions, and  then restarted after the error
    override def preRestart(reason: Throwable, message: Option[Any]): Unit = {
      println("WorkerActor preRestart")
    }

    override def postRestart(reason: Throwable): Unit = {
      // to call base class
      super.postRestart(reason)
      println("WorkerActor postRestart")
    }

    override def postStop() = {
      println("WorkerActor postStop")
    }
  }

  val system = ActorSystem("training")

  // create master worker
  val masterActor = system.actorOf(Props[MasterActor], name="master")

  masterActor.tell(Job("Scan 100 pages"), null)
  Future {
    Thread.sleep(5000)
    masterActor.tell(Job("Print 150 pages"), null)
  }


}
