package workshop.example

import akka.actor.ActorSystem
import akka.cluster.Cluster
import akka.pattern.ask
import akka.util.Timeout
import com.typesafe.config.ConfigFactory
import workshop.models.{ClusterJob, ClusterJobCompleted, GetAllInvoicesResponse, GetInvoiceResponse, Invoice}

import scala.concurrent.Future
import scala.concurrent.duration._
// 2 second    [postfix operator, compilation flag/ import statement
import akka.http.scaladsl.Http
import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Directives._

import scala.language.postfixOps


import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import spray.json.DefaultJsonProtocol

// convert Scala case class to json, json to scala case class object
trait JsonSupport extends SprayJsonSupport {
  import DefaultJsonProtocol._
  implicit val invoiceJsonFormat = jsonFormat3(Invoice)
  implicit val getAllInvoicesResponseFormat = jsonFormat1(GetAllInvoicesResponse)
  implicit  val getInvoiceFormat = jsonFormat1(GetInvoiceResponse)

  implicit val clusterJobJsonFormat = jsonFormat1(ClusterJob)
  implicit val clusterJobCompletedJsonFormat = jsonFormat1(ClusterJobCompleted)


}

// akka-http is low level api to build http(s) end points
// alternatively you can play frmaewokr which is build on top of akka
// both play and akka-http very well integrated and deployed as part of clusters
object AkkaHttpClusterExample extends  App with JsonSupport {


  println("Application config path ", args)

  val configPath = if (args.length == 0) "http-cluster1" else args(0)
  println("Starting http cluster with config ", configPath)


  implicit  val timeout = Timeout(10 seconds) // postfix imports help
  implicit val ec: scala.concurrent.ExecutionContext = scala.concurrent.ExecutionContext.global


  val config = ConfigFactory.load(configPath)
  implicit val system = ActorSystem("training", config)

  // create a cluster

  val cluster = Cluster(system)

  // we need to register the actors after cluster Member up
  cluster registerOnMemberUp {
    println("on register phase for http")
  }

  // Let use use SCALA Case classes to receive message from rest api
  // use scala case classes to send message

  // routing, DSL - domain specific language

  val homeRoute = path("") {
    get {
      complete(HttpEntity(ContentTypes.`text/html(UTF-8)`,
        "<h2>Home</h2>"))
    }
  }

  // REST API to work with invoices
  // GET /invoices  -- get all the invoices
  // GET /invoices/12323 -- get speicifc invoice from api
  // DELETE /invoices/12323 -- delete a specific invoice in db
  // PUT /invoices/12323  -- update existing invoice
      // payload json body
  // POST /invoices -- create new invoice
  /* in postman, ensure that Body content should raw, application type should be json
      { "id": 1000, "customerId" : 11111, "amount": 10000.00}

   */

  val invoicesRoute = pathPrefix("invoices") {
    concat(
      pathEnd {  // /invoices
        concat (
          get {
            // GET /invoices
            // typically this response comes from actor

            val getInvoicesResponseFuture = Future {
              GetAllInvoicesResponse(Seq(
                Invoice(1, 100, 1000),
                Invoice(2, 200, 2000)))
            }

            complete(getInvoicesResponseFuture)
          },

          post {
            entity(as[Invoice]) { // ensure that json body converted into pojo/case class
              invoice => {
                println("REceived ", invoice) // we should have invoices extracted from rest client
                // after this, we will connect to actor and ask actor to write to db etc
                complete(StatusCodes.OK)
              }
            }
            //complete(StatusCodes.OK)
          }
        )
      },
      path(Segment) {
        // GET /invoices/12323
        id => // 12323
          concat(
            get {
              println("REceived ", id)

              val getInvoiceResponseFuture = Future {
                GetInvoiceResponse(
                  Invoice(id.toInt, 100, 1000))
              }

              complete(getInvoiceResponseFuture)


            },
            delete {
              // use postman
              // DELETE /invoices/1234
              println("REceived delete ", id)
              complete(StatusCodes.OK)
            }
          )
      }
    )
  }



  val jobsRoute = pathPrefix("jobs") {
    concat(
      pathEnd {  // /invoices
        concat (
            post {
            entity(as[ClusterJob]) { // ensure that json body converted into pojo/case class
              job => {
                println("ClusterJob ", job) // we should have invoices extracted from rest client
                // ask the master do the job, master in turn ask the worker to get the job done
                // response is Future
                val response = (system.actorSelection("akka://training@127.0.0.1:2551/user/master") ? job)
                    .mapTo[ClusterJobCompleted] // response from actor
                // after this, we will connect to actor and ask actor to write to db etc
                complete(response)
              }
            }
            //complete(StatusCodes.OK)
          }
        )
      }
    )
  }

  // ~ concat multiple routes
  // GET http://localhost:8888
  // GET http://localhost:8888/invoices

  // GET http://localhost:8888/invoices/100

  // DELETE http://localhost:8888/invoices/100

  // POST http://localhost:8888/jobs
  // payload, in postman, text, json format
       // {"name": "Print 100 pages"}

  Http().bindAndHandle(homeRoute ~ invoicesRoute ~ jobsRoute , "localhost", 8888)


}
