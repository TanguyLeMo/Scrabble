package de.htwg.se.scrabble
package aview


import de.htwg.se.scrabble.util.Observer
import de.htwg.se.scrabble.controller.Controller

import scala.annotation.tailrec
import scala.io.StdIn.readLine


class TUI(val controller: Controller) extends Observer {
  controller.add(this)

  def run(): Unit =
    println(controller.toString)
    println("Enter your Word, Coordinate and Direction(H|V) example: myWord A 0 H")
    getInputAndPrintLoop(readLine())

  override def update(): Unit = {
    println(controller.toString)
  }

  def getInputAndPrintLoop(input : String): Unit = {
    input match
      case "exit" => return
      case _ =>
        val inputVector = input.split(" ")
        if (inputVector.length != 4) {
          println("invalid input")
          getInputAndPrintLoop(readLine())
        }
        if (!validCoordinateInput(inputVector(1),inputVector(2))) {
          println("invalid coordinates")
          getInputAndPrintLoop(readLine())
        }
          val direction = inputVector(3) match
            case "H" => "H"
            case "V" => "V"
            case _ => inputVector(3) + " is not a correct direction"

          val word = inputVector(0)
          val coordinates = translateCoordinate(inputVector(1) + " " + inputVector(2))
          val yCoordinate = coordinates._1
          val xCoordinate = coordinates._2
          if (!(direction == "H" | direction == "V")) {
            println(direction)
          } else if (! controller.wordFitsController(xCoordinate, yCoordinate, direction.charAt(0), word)) {
            println("Word doesnt fit")
          } else {
            controller.placeWordController(xCoordinate, yCoordinate, direction.charAt(0), word)
          }
        println("Enter your Word, Coordinate and Direction(H|V) example: myWord A 0 H")
        val newInput = readLine()
        getInputAndPrintLoop(newInput)
  }
  def translateCoordinate(coordinate: String): (Int, Int) = {
    val coordinates = coordinate.split(" ")
    (coordinates(0).toUpperCase().toCharArray.sum - 'A', coordinates(1).toInt)
  }
  def validCoordinateInput(xCoordinate: String, yCoordinate: String): Boolean = {
    ("""[A-Z,a-z]+""".r matches xCoordinate) && ("""[0-9]+""".r matches yCoordinate)
  }
}