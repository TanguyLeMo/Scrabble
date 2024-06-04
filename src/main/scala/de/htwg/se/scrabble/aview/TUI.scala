package de.htwg.se.scrabble
package aview
import util._
import de.htwg.se.scrabble.controller.Controller
import de.htwg.se.scrabble.model.{CreatePlayersListAsMove, Player, ScrabbleField, placeWordsAsMove, setGameLanguageAsMove}

import scala.io.StdIn.readLine
import de.htwg.se.scrabble.aview.languages.*
import de.htwg.se.scrabble.aview.languages.LanguageEnum.{ENGLISH, FRENCH, GERMAN, ITALIAN}

import scala.util.{Failure, Success, Try}


class TUI(val controller: Controller, val languageContext : LanguageContext) extends Observer {
  controller.add(this)


  def this(controller: Controller) = this(controller, new LanguageContext("english"))


  def dictionaryAddWords: TUI = {
    controller.enterWordforDictionarycontroller
    val word = readLine()
    if (word == languageContext.stop) {
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

  override def update(event: Event): String = {
    event match
      case event: RoundsEvent =>
        println(controller.toString)
        println(controller.field.player)
        println(languageContext.currentPlayer + controller.field.player.nextTurn(controller.thisPlayerList,controller.field.player))
      case event: DictionaryEvent =>
        println(controller.field.languageSettings)
      case event: RequestEnterLanguage =>
        println("please enter language")
      case event: NoSuchLanguageEvent =>
        println(" Entered Language not a available language")
        println(" Es handelt sich um keine verfügbare Sprache")
        println(" il s'agit pas une langue disponible")
        println(" non è una lingua disponibile")
      case event: GameEndEvent => displayLeaderboard()
      case event: CurrentPlayer => println("Current Player: " + controller.field.player.getName)
      case event: Exit => println("Goodbye!")
      case event: InvalidCoordinates => println(languageContext.invalidcoordinates)
      case event: NotInDictionary => println(languageContext.notInDictionary)
      case event: NoCorrectDirection => println(languageContext.noCorrectDirection)
      case event: WordDoesntFit => println(languageContext.wordDoesntFit)
      case event: EnterNumberOfPlayers => println(languageContext.enterNumberofPlayers)
      case event: EnterPlayerName => println(languageContext.enterPlayerNames)
      case event: NameAlreadyTaken => println(languageContext.nameAlreadyTaken)
      case event: NameCantBeEmpty => println(languageContext.nameCantBeEmpty)
      case event: EnterWord => println(languageContext.enterWord)
      case event: InvalidInput => println(languageContext.invalidInput)
      case event: InvalidNumber => println(languageContext.invalidNumber)
      case event: RequestNewWord => println(languageContext.requestNewWord)
      case event: WordAlreadyAddedToDictionary => println(languageContext.wordAlreadyAddedToDictionary)
      case event: WordAddedToDictionary => println(languageContext.wordAddedToDictionary)
      case event: EnterWordForDictionary => println(languageContext.enterWordforDictionary)
      case event: LanguageSetting => println(languageContext.languageSetting)
      case event: WordNotInDictionary => println(languageContext.wordNotInDictionary)
      controller.toString
  }
      def processInputLine() : TUI = {
      val currentPlayer = controller.field.player
     // controller.currentPlayercontroller
      controller.enterWordcontroller
      def processInputLine(currentPlayer : Player) : TUI = {
      println(currentPlayer.getName)
      println("Stones:")
      println(controller.field.player.playerTiles.map(stone => " |" + stone.toString + "| ").mkString)
      println(languageContext.enterWord)
      val input = readLine()
      val exitWord: String = languageContext.exit
      input match {
        case "z" => controller.doAndPublish(controller.undo); processInputLine()
        case "y" => controller.doAndPublish(controller.redo); processInputLine()
        case _ =>
      }
      if(input.equalsIgnoreCase(exitWord))
      {
        controller.displayLeaderBoard; this
      } else
      if (input.equalsIgnoreCase(languageContext.exit)) {
        controller.exitcontroller
        this
      } else {
        val inputVector = input.split(" ")
        if (inputVector.length != 4) {
          controller.invalidInputcontroller
          processInputLine()
        }
        else if (!validCoordinateInput(inputVector(1), inputVector(2))) {
          controller.invalidcoordinatescontroller
          processInputLine()
        } else {
          val direction = inputVector(3) match
            case "H" => "H"
            case "V" => "V"
            case _ => inputVector(3) + languageContext.noCorrectDirection
          val word = inputVector(0).toUpperCase()
          val coordinates = translateCoordinate(inputVector(1) + " " + inputVector(2))
          val yCoordinate = coordinates._1
          val xCoordinate = coordinates._2
          if (!controller.contains(word)) {
            controller.notInDictionarycontroller
            processInputLine()
          } else if (!(direction == "H" | direction == "V")) {
            controller.NoCorrectDirectioncontroller
            processInputLine()
          } else if (!controller.wordFits(xCoordinate, yCoordinate, direction.charAt(0), word)) {
            controller.wordDoesntFitcontroller
            processInputLine()
          } else {
            controller.placeWord(xCoordinate, yCoordinate, direction.charAt(0), word)
            controller.nextTurn(controller.thisPlayerList,currentPlayer)
            processInputLine()
          }
        }
      }
    }

      def translateCoordinate(coordinate: String): (Int, Int) = {
        val coordinates = coordinate.split(" ")
        (coordinates(0).toUpperCase().toCharArray.sum - 'A', coordinates(1).toInt)
      }

      def validCoordinateInput(xCoordinate: String, yCoordinate: String): Boolean = {
        ("""[A-Z,a-z]+""".r matches xCoordinate) && ("""[0-9]+""".r matches yCoordinate)
      }
      def inputNamesAndCreateList(numberPlayers: Int): List[Player] = controller.CreatePlayersList(readPlayerNames(numberPlayers, Vector[String]()))
      def numberOfPlayers(): Int =
        val isNumber : Try[Int] =  Try(readLine("Enter number of players: ").toInt)
        isNumber match {
          case Success(value) =>
            if(value>0) value
            else
              println(languageContext.invalidNumber)
              numberOfPlayers()
          case Failure(exception) =>
            println("Invalid input. Please enter a valid number.")
            numberOfPlayers()
        }
      def readPlayerNames(numberPlayers: Int, vector: Vector[String]): Vector[String] = {
        if(numberPlayers < 1) vector else
          val name = readLine("Player " + (vector.length+1) + ": ")
          name match {
            case "" =>
              println("Name cant be empty")
              readPlayerNames(numberPlayers, vector)
            case _ if vector.contains(name) =>
              println("Name already taken")
              readPlayerNames(numberPlayers, vector)
            case _ =>
              readPlayerNames(numberPlayers - 1, vector ++ Vector(name))
         }     
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
            println("La langue sera " + language)
            new TUI(controller, new LanguageContext(language))
          case "german" =>
            controller.setLanguageDictionary(GERMAN)
            println("Eingestellte Sprache: " + language)
            new TUI(controller, new LanguageContext(language))
          case "italian" =>
            controller.setLanguageDictionary(ITALIAN)
            println("La lingua e " + language)
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

  def placeWordAsFunction: Move => ScrabbleField = move => {
    controller.placeWord(move.xPosition, move.yPosition, move.direction, move.word)
  }
}