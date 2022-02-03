package workshop.example

import akka.actor.{Actor, ActorSystem, PoisonPill, Props}
import akka.pattern.ask
import akka.util.Timeout

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
import scala.language.postfixOps
import scala.concurrent.{Await, Future}
import scala.util.{Failure, Success}
import akka.persistence._

object AkkaPersistenceExample extends  App {
  // MasterActor[ParentActor], WorkerActor [ChildActor]
  // MasterActor receive a job, forward to WorkerActor to get the work done
  case class Job(name: String)
  case class JobCompleted(msg: String)
  case class StopWork() // case object is better
  case class State(count: Int)

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

  // how to make WorkerActor as persisted so that if we restart, error it maintain/retain old values
  class WorkerActor extends PersistentActor {
    println("WorkerActor Created")
    println("Actor path ", akka.serialization.Serialization.serializedActorPath(self))

    // persistence id is unique id, should be used to store and retrive state
    override def persistenceId = "worker-actor-state"

    // initialize the state for that initial value will be set
    var state: State = State(count = 0)

    override def receive = {
      case job: Job => {
        println("Job received by worker ", job)
        // try to increase the state counter by 1
        state = State(count = state.count + 1)
        // persist now.. todo
        // whenever there is state change, where you want to persiste state
        // the state object data shall be seriazlied and then it will be saved
        // in application.config
//        akka.actor.warn-about-java-serializer-usage=off
//        akka.actor.allow-java-serialization=on
//
        saveSnapshot(state)
        println("Total jobs done ", state)
        sender.tell(JobCompleted(s"Done ${job.name}"), self)
      }
      case t:Object => println("Worker default message",  t)
    }

    // when akka goes into recovery mode, we have few messages send by system
    // to pul the data from snapshop and recover teh data
    val receiveRecover : Receive = {
          // message send here for snapshop recovery, completion notification
      case SnapshotOffer(metadata, snapshot: State ) => {
        println("now snapshot given from akka persistance to actor", snapshot)
        // now initialize akka state from snapshop
        state = snapshot
      }
      case RecoveryCompleted => {
        println("Recovery completed")
      }
      case _ => println("receiveRecover")
    }

    // persistent on normal mode
    val receiveCommand: Receive = {
      case _ => println("receiveCommand")
    }
  }


  val system = ActorSystem("training")

  // create master worker
  val masterActor = system.actorOf(Props[MasterActor], name="master")

  // we send message to master, master forward to worker
  masterActor.tell(Job("Print 100 pages"), null)
  masterActor.tell(Job("Print 200 pages"), null)
  masterActor.tell(Job("Print 300 pages"), null)

}
