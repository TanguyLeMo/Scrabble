package de.htwg.se.scrabble
package aview
import de.htwg.se.scrabble.util.Observer
import de.htwg.se.scrabble.controller.Controller
import de.htwg.se.scrabble.model.ScrabbleField
import de.htwg.se.scrabble.aview.languages.LanguageContext

import scala.io.StdIn.readLine
class TUI(val controller: Controller) extends Observer {
  val languageContext: LanguageContext = start
  
  controller.add(this)
  def this() = this(new Controller(new ScrabbleField(15)))
  def start: LanguageContext = {
    println("Welcome to Scrabble , Bienvenue à Scrabble, Willkommen bei Scrabble, Benvenuti a Scrabble")
    
    println("Please choose your language by typing either: English, German, French or Italian")
    val language = readLine()
    val languageContext = new LanguageContext(language)
    println(languageContext.languageSetting + Console.YELLOW + language + Console.RESET)
    println(languageContext.EnterYourWord)
    println("Enter your personal words, which should be available in the dictionary, apart from the default words")
    println("type: \u001B[1m stop \u001B[0m to finish the input of your personal words")
    languageContext
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

  override def equals(obj: Any): Boolean = {
    obj match {
      case obj: TUI => obj.controller.field == this.controller.field
      case _ => false
    }
  }
  
}