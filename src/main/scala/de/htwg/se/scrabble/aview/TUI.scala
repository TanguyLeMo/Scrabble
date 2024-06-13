package de.htwg.se.scrabble
package aview
import util.*
import de.htwg.se.scrabble.controller.Controller
import de.htwg.se.scrabble.model.gameComponent.{Player, ScrabbleField, placeWordsAsMove}
//import de.htwg.se.scrabble.model.languageComponent.LanguageContext
//import de.htwg.se.scrabble.model.{CreatePlayersListAsMove, Player, ScrabbleField, placeWordsAsMove, setGameLanguageAsMove}

import scala.io.StdIn.readLine
import de.htwg.se.scrabble.model.languageComponent.languages.LanguageEnum.{ENGLISH, FRENCH, GERMAN, ITALIAN}

import scala.annotation.tailrec
import scala.collection.immutable
import scala.util.{Failure, Success, Try}


class TUI(val controller: Controller ) extends Observer {
  controller.add(this)
  

  def dictionaryAddWords: TUI = {
    controller.enterWordforDictionarycontroller
    val word = readLine()
    if (word == controller.languageContext.stop) {
      return this
    }
    if(controller.contains(word)) {
      controller.wordAlreadyAddedToDictionarycontroller
    } else{
      controller.add(word)
      controller.wordAddedToDictionarycontroller
    }
    dictionaryAddWords
  }

  override def update(event: ScrabbleEvent): String = {
    event match
      case event: RoundsScrabbleEvent =>
        println(controller.toString)
        println(controller.field.player)
        println(controller.languageContext.currentPlayer + controller.field.player.nextTurn(controller.thisPlayerList,controller.field.player))

      case event: DictionaryScrabbleEvent =>
        println(controller.field.languageSettings)
      case event: RequestEnterLanguage =>
        println("please enter language")
      case event: NoSuchLanguageScrabbleEvent =>
        println(" Entered Language not a available language")
        println(" Es handelt sich um keine verfügbare Sprache")
        println(" il s'agit pas une langue disponible")
        println(" non è una lingua disponibile")
      case event: GameEndScrabbleEvent => displayLeaderboard()
      case event: CurrentPlayer => println("Current Player: " + controller.field.player.getName)
      case event: Exit => println("Goodbye!")
      case event: InvalidCoordinates => println(controller.languageContext.invalidcoordinates)
      case event: NotInDictionary => println(controller.languageContext.notInDictionary)
      case event: NoCorrectDirection => println(controller.languageContext.noCorrectDirection)
      case event: WordDoesntFit => println(controller.languageContext.wordDoesntFit)
      case event: EnterNumberOfPlayers => println(controller.languageContext.enterNumberofPlayers)
      case event: EnterPlayerName => println(controller.languageContext.enterPlayerNames)
      case event: NameAlreadyTaken => println(controller.languageContext.nameAlreadyTaken)
      case event: NameCantBeEmpty => println(controller.languageContext.nameCantBeEmpty)
      case event: EnterWord => println(controller.languageContext.enterWord)
      case event: InvalidInput => println(controller.languageContext.invalidInput)
      case event: InvalidNumber => println(controller.languageContext.invalidNumber)
      case event: RequestNewWord => println(controller.languageContext.requestNewWord)
      case event: WordAlreadyAddedToDictionary => println(controller.languageContext.wordAlreadyAddedToDictionary)
      case event: WordAddedToDictionary => println(controller.languageContext.wordAddedToDictionary)
      case event: EnterWordForDictionary => println(controller.languageContext.enterWordForDictionary)
      case event: LanguageSetting => println(controller.languageContext.languageSetting)
      case event: WordNotInDictionary => println(controller.languageContext.wordNotInDictionary)
      case event: DisplayLeaderBoard => println(controller.languageContext.leaderBoard)
                val players = controller.field.players
                val sortedPlayers = controller.sortListAfterPoints(players)
                sortedPlayers.foreach(player => println(sortedPlayers.indexOf(player) + 1 + ". " + player));
      case event: NotEnoughStones => println(controller.languageContext.notEnoughStones)

      case event: phaseChooseLanguage => controller.setLanguageDictionary(ENGLISH);controller.gamestartPlayStones(controller.field.languageEnum); controller.notifyObservers(phasePlayerAndNames())
      case event: phasePlayerAndNames => controller.CreatePlayersList(getPlayersAndNames); controller.notifyObservers(phaseaddWordsToDictionary())
      case event: phaseaddWordsToDictionary => controller.notifyObservers(phaseMainGame())
      case event: phaseMainGame => processInputLine()
      case event: phaseEndGame => controller.notifyObservers(phaseExit())
      case event: phaseExit => println("Goodbye!"); System.exit(0)

    controller.toString


  }
  def processInputLine() : TUI = {
    val currentPlayer = controller.field.player
    println(controller.field.player)
    //println(controller.field.player.playerTiles.toString)
    controller.enterWordcontroller
    val input = readLine()
    val exitWord: String = controller.languageContext.exit
    input match {
      case "z" => controller.doAndPublish(controller.undo); processInputLine()
      case "y" => controller.doAndPublish(controller.redo); processInputLine()
      case _ =>
    }
    if(input.equalsIgnoreCase(exitWord))
    {
      controller.displayLeaderBoard; this
    } else
      if (input.equalsIgnoreCase(controller.languageContext.exit)) {
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
            case _ => inputVector(3) + controller.languageContext.noCorrectDirection
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
            val lettersAlreadyThere = controller.lettersAlreadyThere(xCoordinate, yCoordinate, direction.charAt(0), word)
            val onlyRequiredStones = controller.OnlyRequiredStones(lettersAlreadyThere, word)

          //  if (controller.hasStones(lettersAlreadyThere, word, currentPlayer)) {
              controller.removeStones(currentPlayer, controller.field.players, onlyRequiredStones)
              drawStonesAfterRound(controller.field.player, onlyRequiredStones.length, controller.field.players)
              controller.placeWord(xCoordinate, yCoordinate, direction.charAt(0), word)
              controller.nextTurn(controller.thisPlayerList,currentPlayer)
              processInputLine()
            /*} else {
              controller.noteEnoughStonescontroller
              processInputLine()
          }*/
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

  def inputNamesAndCreateList(): List[Player] = {
    val playerListWithoutStones = controller.CreatePlayersList(getPlayersAndNames)
    val playerListWithStones = playerStartStones(7, playerListWithoutStones)
    playerListWithStones
  }


  def getPlayersAndNames: Vector[String] = {

    @tailrec
    def numberOfPlayers(): Int = {
      controller.enterNumberofPlayerscontroller
      val isNumber: Try[Int] = Try(readLine().toInt)
      isNumber match {
        case Success(value) =>
          if (value > 0 && (controller.field.stoneContainer.Stones.length / (value * 7.0)) >= 1.0) value
          else {
            controller.invalidNumbercontroller
            numberOfPlayers()
          }
        case Failure(exception) =>
          controller.invalidNumbercontroller
          numberOfPlayers()
      }
    }
    @tailrec
    def readPlayerNames(numberPlayers: Int, vector: Vector[String]): Vector[String] = {
      if (numberPlayers < 1) vector
      else {
        controller.enterPlayerNamecontroller
        val name = readLine()
        name match {
          case "" =>
            readPlayerNames(numberPlayers, vector)
          case _ if vector.contains(name) =>
            controller.nameAlreadyTakencontroller
            readPlayerNames(numberPlayers, vector)
          case _ =>
            readPlayerNames(numberPlayers - 1, vector :+ name)
        }
      }
    }

    //val numberPlayers = numberOfPlayers()
    //readPlayerNames(numberPlayers, Vector.empty[String])
     Vector("Player1", "Player2")
  }

  def displayLeaderboard(): List[Player] = {
    val players = controller.field.players
    val sortedPlayers = controller.sortListAfterPoints(players)
    controller.leaderBoardcontroller
    sortedPlayers
  }

  def drawStonesAfterRound(player: Player, numberOfCards: Int, players: List[Player]): List[Player] = {
    if (controller.field.stoneContainer.Stones.isEmpty || numberOfCards == 0) {
      players
    }
    else {
      val newStone = controller.drawRandomStone(controller.field.stoneContainer)
      println(newStone)
      controller.removeStonefromContainer(newStone, controller.field.stoneContainer)
      val newPlayer = new Player(player.getName, player.getPoints, player.playerTiles :+ newStone)
      val newPlayerList = controller.field.players.updated(controller.field.players.indexOf(player), newPlayer)
      controller.field = new ScrabbleField(controller.field.matrix, controller.field.dictionary, controller.field.squareFactory, controller.field.languageEnum, newPlayer, newPlayerList, controller.field.stoneContainer)
      drawStonesAfterRound(newPlayer, numberOfCards - 1, newPlayerList)

    }
  }

  def playerStartStones(numberStones: Int, playerList: List[Player]): List[Player] = {
    if (numberStones == 0) playerList
    else
      val updatedPlayerList = playerList.foldLeft(playerList) { (updatedList, player) =>
        val StonetoAdd = controller.drawRandomStone(controller.field.stoneContainer)
        controller.removeStonefromContainer(StonetoAdd, controller.field.stoneContainer)
        val AddStonetoPlayer = new Player(player.getName, player.getPoints, player.playerTiles :+ StonetoAdd)
        updatedList.updated(updatedList.indexOf(player), AddStonetoPlayer)
      }
      controller.field = new ScrabbleField(controller.field.matrix, controller.field.dictionary, controller.field.squareFactory, controller.field.languageEnum, updatedPlayerList.head, updatedPlayerList, controller.field.stoneContainer)
      playerStartStones(numberStones - 1, updatedPlayerList)
  }

  def setGameLanguage(): TUI = {
    controller.requestLanguage
    val language = readLine().toLowerCase()
    val newTUI = language match
      case "english" =>
        controller.setLanguageDictionary(ENGLISH)
      case "french" =>
        controller.setLanguageDictionary(FRENCH)
      case "german" =>
        controller.setLanguageDictionary(GERMAN)
      case "italian" =>
        controller.setLanguageDictionary(ITALIAN)
      case _ =>
        setGameLanguage()
    controller.gamestartPlayStones(controller.field.languageEnum)
    this
  }

  override def equals(obj: Any): Boolean = {
    obj match {
      case obj: TUI => obj.controller.field == this.controller.field
      case _ => false
    }
  }
  def placeWordAsFunction: placeWordsAsMove => ScrabbleField = move => {
    controller.placeWord(move.xPosition, move.yPosition, move.direction, move.word)
  }
}