ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.8"

lazy val root = (project in file("."))
  .settings(
    name := "scala-akka-workshop"
  )

lazy val akkaVersion = "2.6.5"
lazy val akkaHttpVersion  = "10.2.0-M1"


libraryDependencies += "com.typesafe.akka" %% "akka-actor" % akkaVersion
libraryDependencies += "com.typesafe.akka" %% "akka-persistence" % akkaVersion

libraryDependencies += "com.typesafe.akka" %% "akka-cluster" % akkaVersion
libraryDependencies += "com.typesafe.akka" %% "akka-cluster-tools" % akkaVersion
libraryDependencies += "com.typesafe.akka" %% "akka-cluster-sharding" % akkaVersion


// https://mvnrepository.com/artifact/com.typesafe.akka/akka-http
libraryDependencies += "com.typesafe.akka" %% "akka-http" % "10.2.0-M1"
libraryDependencies += "com.typesafe.akka" %% "akka-http-spray-json" % "10.2.0-M1"



libraryDependencies += "com.typesafe" % "config" % "1.3.3"

// level db for persistence
libraryDependencies += "org.iq80.leveldb" % "leveldb" % "0.10"
