package de.htwg.se.scrabble
package aview
import de.htwg.se.scrabble.util.Observer
import de.htwg.se.scrabble.controller.Controller
import de.htwg.se.scrabble.model.ScrabbleField
import de.htwg.se.scrabble.model.Player

import scala.io.StdIn.readLine
import de.htwg.se.scrabble.aview.languages.*
import de.htwg.se.scrabble.aview.languages.LanguageEnum.{ENGLISH, FRENCH, GERMAN, ITALIAN}

class TUI(val controller: Controller, val languageContext : LanguageContext,player: Player) extends Observer {
  controller.add(this)
  def this(controller: Controller) = this(controller, new LanguageContext("english"), new Player("Someone", 0))
  def this(controller: Controller, languageContext:LanguageContext) = this(controller, languageContext, new Player("Someone", 0))
  controller.setLanguageDictionary(languageContext.language)

  def dictionaryAddWords: TUI = {
    println(languageContext.enterWordforDictionary)
    val word = readLine()
    if (word == languageContext.stop) {
      println(languageContext.enterWord)
      return this
    }
    if(controller.contains(word)) {
      println( languageContext.wordAlreadyAddedToDictionary)
    } else{
      controller.add(word)
      println(languageContext.wordAddedToDictionary)
    }
    dictionaryAddWords
  }

  override def update(): String = {
    println(controller.toString)
    controller.toString
  }
      def processInputLine(currentPlayer : Player) : TUI = {
      println(currentPlayer)
      println(languageContext.enterWord)
      val input = readLine()
      if (input.equalsIgnoreCase(languageContext.exit)) {
        this
      } else {
        val inputVector = input.split(" ")
        if (inputVector.length != 4) {
          println(languageContext.invalidInput)
          println(languageContext.enterWord)
        }
        else if (!validCoordinateInput(inputVector(1), inputVector(2))) {
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
          if (!controller.contains(word)) {
            println(word + languageContext.notInDictionary)
          } else if (!(direction == "H" | direction == "V")) {
            println(direction)
          } else if (!controller.wordFits(xCoordinate, yCoordinate, direction.charAt(0), word)) {
            println(languageContext.wordDoesntFit)
          } else {
            controller.placeWord(xCoordinate, yCoordinate, direction.charAt(0), word.toUpperCase)
            val points = controller.collectPoints(controller.thisMatrix,xCoordinate,yCoordinate,direction.charAt(0),word)
            controller.AddPoints(points,currentPlayer,controller.thisPlayerList)
            val currentleaderboard = controller.sortListAfterPoints(controller.field.players)
            displayLeaderboard(currentleaderboard)
            println("")
            processInputLine(controller.nextTurn(controller.thisPlayerList,currentPlayer))
          }
          println(languageContext.enterWord)
        }
        processInputLine(currentPlayer)
      }
    }

      def translateCoordinate(coordinate: String): (Int, Int) = {
        val coordinates = coordinate.split(" ")
        (coordinates(0).toUpperCase().toCharArray.sum - 'A', coordinates(1).toInt)
      }

      def validCoordinateInput(xCoordinate: String, yCoordinate: String): Boolean = {
        ("""[A-Z,a-z]+""".r matches xCoordinate) && ("""[0-9]+""".r matches yCoordinate)
      }
      def inputNamesAndCreateList(numberPlayers: Int): List[Player] = controller.CreatePlayersList(readPlayerNames(numberPlayers,Vector().empty))
      def numberOfPLayers(): Int = readLine(languageContext.enterNumberofPlayers + " ").toInt
      def readPlayerNames(numberPlayers: Int, vector : Vector[String]): Vector[String] = {
        if(numberPlayers < 1) vector else
          val name = readLine()
          if(vector.contains(name)) {
            println(languageContext.nameAlreadyTaken + " ")
            readPlayerNames(numberPlayers, vector)
          } else
            readPlayerNames(numberPlayers - 1, vector ++ Vector(name))
      }
      def displayLeaderboard(players: List[Player]): List[Player] = {
        val sortedPlayers = controller.sortListAfterPoints(players)
        println("Leaderboard:")
        sortedPlayers.foreach(player => println(sortedPlayers.indexOf(player) + 1 + ". " + player))
        sortedPlayers
      }
      def setGameLanguage(): TUI = {
        val language = readLine("Enter language: ").toLowerCase()
        val newTUI = language match
          case "english" =>
            controller.setLanguageDictionary(ENGLISH)
            println("language set to " + language)
            new TUI(controller, new LanguageContext(language))
          case "french" =>
            controller.setLanguageDictionary(FRENCH)
            println("language set to " + language)
            new TUI(controller, new LanguageContext(language))
          case "german" =>
            controller.setLanguageDictionary(GERMAN)
            println("language set to " + language)
            new TUI(controller, new LanguageContext(language))
          case "italian" =>
            controller.setLanguageDictionary(ITALIAN)
            println("language set to " + language)
            new TUI(controller, new LanguageContext(language))
          case _ =>
            println(language + " is not a available language")
            println(language + " ist keine verfügbare Sprache")
            println(language + " n'est pas une langue disponible")
            println(language + " non è una lingua disponibile")
            setGameLanguage()
        newTUI
      }

  override def equals(obj: Any): Boolean = {
    obj match {
      case obj: TUI => obj.controller.field == this.controller.field
      case _ => false
    }
  }
}