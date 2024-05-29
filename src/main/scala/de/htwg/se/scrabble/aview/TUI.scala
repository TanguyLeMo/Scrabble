package de.htwg.se.scrabble
package aview
import de.htwg.se.scrabble.util.Observer
import de.htwg.se.scrabble.controller.Controller
import de.htwg.se.scrabble.model.ScrabbleField
import de.htwg.se.scrabble.model.Player
import scala.io.StdIn.readLine

class TUI(val controller: Controller) extends Observer {
  controller.add(this)

  def this() = this(new Controller(new ScrabbleField(15, ENGLISH)))
  def start: LanguageContext = {
    println("Welcome to Scrabble , Bienvenue à Scrabble, Willkommen bei Scrabble, Benvenuti a Scrabble")
    println("Please choose your language by typing either: English, German, French or Italian")
    val language = readLine()
    val languageContext = new LanguageContext(language)
    println(languageContext.languageSetting + Console.YELLOW + languageContext + Console.RESET)
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

  def processInputLine(input : String, currentPlayer : Player, PlayerList: List[Player]): String = {
    input match
      case "exit" => "exit"
      case _ =>
  def processInputLine(input : String): String = {
    if(input.equalsIgnoreCase(languageContext.exit)) {"exit"} else{
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
          val word = inputVector(0).toUpperCase()
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
            controller.placeWord(xCoordinate, yCoordinate, direction.charAt(0), word.toUpperCase)
          }
          println(languageContext.enterWord)
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
    controller.CreatePlayersList(readPlayerNames(numberPlayers))
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

  def inputDictionaryLanguage(): Boolean = {
    val language = readLine("Enter language: ").toLowerCase()

    val availableLanguages = language match
      case "english" =>
        controller.setLanguageDictionary(language)
        println("language set to " + language)
        true
      case "french" =>
        controller.setLanguageDictionary(language)
        println("language set to " + language)
        true
      case "german" =>
        controller.setLanguageDictionary(language)
        println("language set to " + language)
        true
      case "italian" =>
        controller.setLanguageDictionary(language)
        println("language set to " + language)
        true
      case _ =>
        println(language + " is not a available language")
        false
    availableLanguages
  }


  override def equals(obj: Any): Boolean = {
    obj match {
      case obj: TUI => obj.controller.field == this.controller.field
      case _ => false
    }
  }
  
}