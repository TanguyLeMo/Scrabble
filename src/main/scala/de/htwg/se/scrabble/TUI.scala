package de.htwg.se.scrabble

import de.htwg.se.scrabble.model.ScrabbleField

import scala.annotation.tailrec
import scala.io.StdIn.readLine

@main def run(): Unit = {
  val field = new ScrabbleField(15)
  println(field)
  getInputAndPrintLoop(field)
}


@tailrec
def getInputAndPrintLoop(field: ScrabbleField): Unit = {
  println("Enter your Word, Coordinate and Direction(H|V) example: myWord A 0 H")
  val input = readLine()
  parseInput(input, field) match
    case None => field
    case Some(newfield) =>
      println(newfield)
      getInputAndPrintLoop(newfield)
}

def parseInput(input: String, field: ScrabbleField): Option[ScrabbleField] = {
  input match
    case "exit" => None
    case _ =>
      val inputArray = input.split(" ")
      if (inputArray.length != 4){
        println("invalid input")
        Some(field)
      }else if(!("""[A-Z,a-z]+""".r matches inputArray(1)) || !("""[0-9]+""".r matches inputArray(2))){
        println("invalid coordinates")
        Some(field)
      } else {
        val direction = inputArray(3) match
          case "H" => "H"
          case "V" => "V"
          case _ => inputArray(3) + " is not a correct direction"

        val word = inputArray(0)
        val coordinates = field.translateCoordinate(inputArray(1) + " " + inputArray(2))
        val yCoordinate = coordinates._1
        val xCoordinate = coordinates._2
        if (!(direction == "H" | direction == "V")) {
          println(direction)
          Some(field)
        } else if (!field.wordFits(xCoordinate, yCoordinate, direction.charAt(0), word)) {
          println("Word doesnt fit")
          Some(field)
        } else {
          Some(field.placeWord(xCoordinate, yCoordinate, direction.charAt(0), word))
        }
      }
}