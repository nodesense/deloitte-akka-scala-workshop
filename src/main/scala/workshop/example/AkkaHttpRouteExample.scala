package workshop.example

import scala.concurrent.ExecutionContext.Implicits.global
import akka.actor.{Actor, ActorRef, ActorSystem, PoisonPill, Props}
import akka.pattern.ask
import akka.util.Timeout


import scala.concurrent.duration._
// 2 second    [postfix operator, compilation flag/ import statement
import scala.language.postfixOps
import scala.concurrent.{Future, Await}
import scala.util.{Failure, Success}

import akka.http.scaladsl.Http
import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.model.HttpMethods.{GET}


import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import spray.json.DefaultJsonProtocol


// akka-http is low level api to build http(s) end points
// alternatively you can play frmaewokr which is build on top of akka
// both play and akka-http very well integrated and deployed as part of clusters
object AkkaHttpRouteExample extends  App {
  implicit  val system = ActorSystem("training")
  implicit  val timeout = Timeout(10 seconds) // postfix imports help

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
       //payload

  val invoicesRoute = pathPrefix("invoices") {
    concat(
      pathEnd {  // /invoices
        concat (
          get {
            // GET /invoices
            val response =  HttpEntity(ContentTypes.`application/json`,
              """
                |[
                |  {
                |    "id":1,"Amount":123.45
                |    },
                |      {
                |    "id":2,"Amount":2222.65
                |    }
                |    ]
                |""".stripMargin)
            complete(response)
          }
        )
      },
      path(Segment) {
        // GET /invoices/12323
        id => // 12323
          concat(
            get {
              println("REceived ", id)
              val response =  HttpEntity(ContentTypes.`application/json`,
                """
                  |  {
                  |    "id":100,"Amount":123.45
                  |    }
                  |""".stripMargin)
              complete(response)
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

  // ~ concat multiple routes
  // GET http://localhost:8888
  // GET http://localhost:8888/invoices

  // GET http://localhost:8888/invoices/100

  // DELETE http://localhost:8888/invoices/100

  Http().bindAndHandle(homeRoute ~ invoicesRoute  , "localhost", 8888)


}
