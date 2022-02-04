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
object AkkaHttpExample extends  App {
  implicit  val system = ActorSystem("training")
  implicit  val timeout = Timeout(10 seconds) // postfix imports help

  val requestHandler: HttpRequest => HttpResponse = {
    case HttpRequest(GET, Uri.Path("/"), _, _, _) =>
      HttpResponse(entity = HttpEntity(ContentTypes.`text/html(UTF-8)`,
        "<h2>Hello</h2>"))

    case HttpRequest(GET, Uri.Path("/morning"), _, _, _) =>
      HttpResponse(entity = HttpEntity(ContentTypes.`text/html(UTF-8)`,
        "<h2>Hello good morning</h2>"))
  }

  // server listen on port number 8888
  // two end points
  // http://localhost:8888
  // http://localhost:8888/morning
  Http().bindAndHandleSync(requestHandler, "localhost", 8888)


}
