package de.htwg.se.scrabble
package aview
import de.htwg.se.scrabble.util.Observer
import de.htwg.se.scrabble.controller.Controller
import de.htwg.se.scrabble.model.{Move, Player, ScrabbleField}
import scala.io.StdIn.readLine
import de.htwg.se.scrabble.aview.languages.*
import de.htwg.se.scrabble.aview.languages.LanguageEnum.{ENGLISH, FRENCH, GERMAN, ITALIAN}
import scala.util.{Try,Success,Failure}


class TUI(val controller: Controller, val languageContext : LanguageContext) extends Observer {
  controller.add(this)
  def this(controller: Controller) = this(controller, new LanguageContext("english"))
  controller.setLanguageDictionary(languageContext.language)


  def dictionaryAddWords: TUI = {
    println(languageContext.enterWordforDictionary)
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

  override def update(): String = {
    println(controller.toString)
    controller.toString
  }
      def processInputLine(currentPlayer : Player) : TUI = {
      println(currentPlayer.getName)
      println("Stones:")
      println(controller.field.player.playerTiles.map(stone => " |" + stone.toString + "| ").mkString)
      println(languageContext.enterWord)
      val input = readLine()
      val exitWord: String = languageContext.exit
      input match {
        case "z" => controller.doAndPublish(controller.undo); processInputLine(currentPlayer)
        case "y" => controller.doAndPublish(controller.redo); processInputLine(currentPlayer)
        case _ =>
      }
      if( input == null || input.equalsIgnoreCase(exitWord)) this else
      if (input.equalsIgnoreCase(languageContext.exit)) {
        this
      } else {
        val inputVector = input.split(" ")
        if (inputVector.length != 4) {
          println(languageContext.invalidInput)
          processInputLine(currentPlayer)
        }
        else if (!validCoordinateInput(inputVector(1), inputVector(2))) {
          println(languageContext.invalidcoordinates)
          processInputLine(currentPlayer)
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
            processInputLine(currentPlayer)
          } else if (!(direction == "H" | direction == "V")) {
            println(direction)
            processInputLine(currentPlayer)
          } else if (!controller.wordFits(xCoordinate, yCoordinate, direction.charAt(0), word)) {
            println(languageContext.wordDoesntFit)
            processInputLine(currentPlayer)
          } else {
            val lettersAlreadyThere = controller.lettersAlreadyThere(xCoordinate, yCoordinate, direction.charAt(0), word)
            val onlyRequiredStones = controller.OnlyRequiredStones(lettersAlreadyThere, word)
            
            if(controller.hasStones(lettersAlreadyThere,word,currentPlayer)) {
              controller.removeStones(currentPlayer,controller.field.players,onlyRequiredStones)
              drawStonesAfterRound(controller.field.player,onlyRequiredStones.length,controller.field.players)
              val move = Move(yCoordinate, xCoordinate, direction.charAt(0), word)
              controller.doAndPublish(placeWordAsFunction, move)
              processInputLine(controller.nextTurn(controller.thisPlayerList, currentPlayer))
            } else {
              println("not enough stones")
              processInputLine(currentPlayer)
            }
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
      def inputNamesAndCreateList(numberPlayers: Int): List[Player] = {

        val playerListWithoutStones = controller.CreatePlayersList(readPlayerNames(numberPlayers, Vector[String]()))
        val playerListWithStones = playerStartStones(7, playerListWithoutStones)
        playerListWithStones
      }
      def numberOfPlayers(): Int =
        val isNumber : Try[Int] =  Try(readLine("Enter number of players: ").toInt)
        isNumber match {
          case Success(value) =>
            if(value>0 & (controller.field.stoneContainer.Stones.length/(value*7.0))>=1.0) value
            else
              println(languageContext.invalidNumber)
              numberOfPlayers()
          case Failure(exception) =>
            println("Invalid input. Please enter a valid number.")
            numberOfPlayers()
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

      def drawStonesAfterRound(player:Player,numberOfCards:Int,players:List[Player]):List[Player]={
        if(controller.field.stoneContainer.Stones.length==0||numberOfCards==0){
          players
        }
        else{
          val newStone = controller.drawRandomStone(controller.field.stoneContainer)
          println(newStone)
          controller.removeStonefromContainer(newStone,controller.field.stoneContainer)
          val newPlayer = new Player(player.getName,player.getPoints,player.playerTiles :+ newStone)
          val newPlayerList = controller.field.players.updated(controller.field.players.indexOf(player),newPlayer)
          controller.field = new ScrabbleField(controller.field.matrix,controller.field.dictionary,controller.field.squareFactory,controller.field.languageEnum,newPlayer,newPlayerList,controller.field.stoneContainer)
          drawStonesAfterRound(newPlayer,numberOfCards-1,newPlayerList)

        }
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
        controller.gamestartPlayStones(controller.field.languageEnum)
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