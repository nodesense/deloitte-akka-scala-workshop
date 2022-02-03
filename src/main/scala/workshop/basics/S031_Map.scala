package workshop.basics

import scala.collection.mutable.Map

object S031_Map extends App {
  // Key and value
  val countries: Map[String, String] = Map()
  //add element to map with key India
  countries += Tuple2("India", "IN") //tuple 2
  countries += "United Kingdom" -> "UK" //tuple 2

  println(countries)

  println(countries.contains("India")) //True
  countries.update("United Kingdom", "BR")
  println(countries)

  countries -= "United Kingdom"
  println(countries)
}
