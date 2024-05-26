package de.htwg.se.scrabble
package aview
import de.htwg.se.scrabble.util.Observer
import de.htwg.se.scrabble.controller.Controller
import de.htwg.se.scrabble.model.ScrabbleField
import de.htwg.se.scrabble.aview.languages.LanguageContext
import de.htwg.se.scrabble.aview.languages.LanguageEnum.ENGLISH

import scala.io.StdIn.readLine
class TUI(val controller: Controller) extends Observer {
  val languageContext: LanguageContext = start

  controller.add(this)

  def this() = this(new Controller(new ScrabbleField(15, ENGLISH)))
  def start: LanguageContext = {
    println("Welcome to Scrabble , Bienvenue à Scrabble, Willkommen bei Scrabble, Benvenuti a Scrabble")
    println("Please choose your language by typing either: English, German, French or Italian")
    val language = readLine()
    val languageContext = new LanguageContext(language)
    println(languageContext.languageSetting + Console.YELLOW + language + Console.RESET)
    println(languageContext.enterWordforDictionary)
    controller.field = new ScrabbleField(15, languageContext.language)
    languageContext
    }
  def dictionaryAddWords(word: String): String = {
    if (word == languageContext.stop) {
      println(languageContext.enterWord)
      return "stop"
    }
    if(controller.contains(word)) {
      println( languageContext.wordAlreadyAddedToDictionary)
    } else{
      controller.add(word)
      println(languageContext.wordAddedToDictionary)
    }
    word
  }
  override def update(): String = {
    println(controller.toString)
    controller.toString
  }
  def processInputLine(input : String): String = {
    if(input == languageContext.exit) {"exit"} else{
        val inputVector = input.split(" ")
        if (inputVector.length != 4) {
          println(languageContext.invalidInput)
          println(languageContext.enterWord)
        }
        else if (!validCoordinateInput(inputVector(1),inputVector(2))) {
          println(languageContext.invalidcoordinates)
          println(languageContext.enterWord)
        } else {
          val direction = inputVector(3) match
            case "H" => "V"
            case "V" => "H"
            case _ => inputVector(3) + languageContext.noCorrectDirection
          val word = inputVector(0)
          val coordinates = translateCoordinate(inputVector(1) + " " + inputVector(2))
          val yCoordinate = coordinates._1
          val xCoordinate = coordinates._2
          if(!controller.contains(word)) {
            println(word + languageContext.notInDictionary)
          } else if (!(direction == "H" | direction == "V")) {
            println(direction)
          } else if (!controller.wordFits(xCoordinate, yCoordinate, direction.charAt(0), word)) {
            println(languageContext.wordDoesntFit)
          } else {
            controller.placeWord(xCoordinate, yCoordinate, direction.charAt(0), word)
          }
          println(languageContext.enterWord)
        }
        ""
    }
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