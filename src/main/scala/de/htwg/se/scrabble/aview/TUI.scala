package de.htwg.se.scrabble
package aview

import de.htwg.se.scrabble.util.Observer
import de.htwg.se.scrabble.controller.Controller

class TUI(val controller: Controller) extends Observer {
  controller.add(this)

  override def update(): String = {
    println(controller.toString)
    controller.toString
  }

  def processInputLine(input : String): String = {
    input match
      case "exit" => "exit"
      case _ =>
        val inputVector = input.split(" ")
        if (inputVector.length != 4) {
          println("invalid input")
          println("Enter your Word, Coordinate and Direction(H|V) example: myWord A 0 H")
        }
        else if (!validCoordinateInput(inputVector(1),inputVector(2))) {
          println("invalid coordinates")
          println("Enter your Word, Coordinate and Direction(H|V) example: myWord A 0 H")
        } else {
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
          } else if (!controller.wordFits(xCoordinate, yCoordinate, direction.charAt(0), word)) {
            println("Word doesnt fit")
          } else {
            controller.placeWord(xCoordinate, yCoordinate, direction.charAt(0), word)
          }
          println("Enter your Word, Coordinate and Direction(H|V) example: myWord A 0 H")
        }
        ""
  }
  def translateCoordinate(coordinate: String): (Int, Int) = {
    val coordinates = coordinate.split(" ")
    (coordinates(0).toUpperCase().toCharArray.sum - 'A', coordinates(1).toInt)
  }
  def validCoordinateInput(xCoordinate: String, yCoordinate: String): Boolean = {
    ("""[A-Z,a-z]+""".r matches xCoordinate) && ("""[0-9]+""".r matches yCoordinate)
  }
}