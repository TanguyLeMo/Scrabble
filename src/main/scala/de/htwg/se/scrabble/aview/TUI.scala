package de.htwg.se.scrabble
package aview
import de.htwg.se.scrabble.util.Observer
import de.htwg.se.scrabble.controller.Controller
import de.htwg.se.scrabble.model.ScrabbleField
import de.htwg.se.scrabble.model.Player
import scala.io.StdIn.readLine

class TUI(val controller: Controller) extends Observer {
  controller.add(this)

  //def this() = this(new Controller(new ScrabbleField(15)))????

  def start: TUI = {
    println("Welcome to Scrabble")
    println("Enter your personal words, which should be available in the dictionary, apart from the default words")
    println("type: \u001B[1m stop \u001B[0m to finish the input of your personal words")
    this
    }

  def dictionaryAddWords(word: String): String = {
    if (word == "stop") {
      println("Enter your Word, Coordinate and Direction(H|V) example: myWord A 0 H")
      return "stop"
    }
    if(controller.contains(word)) {
      println( word + " already in dictionary")
    } else{
      controller.add(word)
      println(word + " is added to dictionary")
    }
    word
  }
  

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
          if(!controller.contains(word)) {
            println(word + " is not in dictionary, sorry!")
          } else if (!(direction == "H" | direction == "V")) {
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

  def inputNamesAndCreateList(numberPlayers: Int): List[Player] = {
    controller.CreatePlayersListc(readPlayerNames(numberPlayers))
  }

  def numberOfPLayers(): Int = {
    readLine("Enter number of players: ").toInt
  }

  def readPlayerNames(numberPlayers: Int): Vector[String] = {
    if (numberPlayers > 0) {
      val name = readLine()
      Vector(name) ++ readPlayerNames(numberPlayers - 1)
    } else {
      Vector.empty
    }

  }

  def displayLeaderboard(players: List[Player]): Unit = {
    val sortedPlayers = controller.sortListAfterPoints(players)
    println("Leaderboard:")
    sortedPlayers.foreach(player => println(sortedPlayers.indexOf(player) + 1 + ". " + player))
  }


  override def equals(obj: Any): Boolean = {
    obj match {
      case obj: TUI => obj.controller.field == this.controller.field
      case _ => false
    }
  }
  
}