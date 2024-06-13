package de.htwg.se.scrabble.controller.ControllerComponent.ControllerBaseImpl

import de.htwg.se.scrabble.controller.ControllerComponent.ControllerInterface
import de.htwg.se.scrabble.model.languageComponent.LanguageContextInterface
import de.htwg.se.scrabble.model.{LanguageEnum, Matrix, Player, ScrabbleField, Stone, StoneContainer, placeWordsAsMove}
import de.htwg.se.scrabble.util
import de.htwg.se.scrabble.util.*

import scala.util.*


class Controller(var field: ScrabbleField) extends ControllerInterface:
  val undoManager = new util.UndoManager[ScrabbleField]
  
  override def doAndPublish(doThis: placeWordsAsMove => ScrabbleField, move: placeWordsAsMove): Unit =
    val newState = doThis(move)
    field = undoManager.doStep(field, newState)
    notifyObservers(new RoundsScrabbleEvent)

  override def doAndPublish(doThis: => ScrabbleField): Unit =
    field = doThis
    notifyObservers(new RoundsScrabbleEvent)

  override def undo: ScrabbleField = {
    field = undoManager.undoStep(field)
    field
  }
  override def redo: ScrabbleField = undoManager.redoStep(field)
  
  override def toString: String = field.toString

  override def placeWord(xPosition: Int, yPosition: Int, direction: Char, word: String): ScrabbleField =
    val move = placeWordsAsMove(xPosition, yPosition, direction, word)
    val newState = field.placeWord(move.yPosition, move.xPosition, move.direction, move.word)
    field = undoManager.doStep(field, newState)
    field = field.addPoints(collectPoints(field.matrix, move.xPosition, move.yPosition, move.direction, move.word), field.player, field.players)
    notifyObservers(new RoundsScrabbleEvent)
    field

  override def wordFits(xPosition: Int, yPosition: Int, direction: Char, word: String): Boolean =
    field.wordFits(xPosition, yPosition, direction, word)

  override def fitsinBounds(xPosition: Int, yPosition: Int, direction : Char, word : String): Boolean =
    field.matrix.fitsInBounds(xPosition, yPosition, direction, word)

  override def contains(word: String): Boolean = field.dictionary.contains(word)

  override def add(word: String): String =
    field = new ScrabbleField(field.matrix, field.dictionary.addWord(word), field.squareFactory, field.languageEnum, field.player, field.players,field.stoneContainer)
    word

  override def CreatePlayersList(playernames: Vector[String]): List[Player] =
    val playerList = field.player.CreatePlayersList(playernames)
    field = new ScrabbleField(field.matrix,field.dictionary,field.squareFactory, field.languageEnum, playerList.head,playerList,field.stoneContainer)
    playerList

  override def sortListAfterPoints(players: List[Player]): List[Player] =
    field.player.sortListAfterPoints(players)

  override def lettersAlreadyThere(xPosition: Int, yPosition: Int, direction: Char, word: String): List[Stone] =
    field.matrix.lettersAlreadyThere(xPosition, yPosition, direction, word)

  override def setLanguageDictionary(language: LanguageEnum): ScrabbleField =
    field = field.setLanguageDictionary(language)
    notifyObservers(new DictionaryScrabbleEvent)
    field

  override def collectPoints(matrix: Matrix, xPosition: Int, yPosition: Int, direction: Char, word: String): Int =
    field.scoringSystem.collectPoints(field.matrix, xPosition, yPosition, direction, word)

  override def hasStones(notRequiredStones: List[Stone], word: String, player: Player): Boolean =
    player.hasStones(notRequiredStones, word, player)

  override def languageContext: LanguageContextInterface = field.languageContext

  override def AddPoints(pointsToAdd: Int, player: Player, ListPlayers: List[Player]): List[Player] =
    val playerList = player.AddPoints(pointsToAdd, player, ListPlayers)
    field = new ScrabbleField(field.matrix,field.dictionary,field.squareFactory, field.languageEnum, field.player , playerList, field.stoneContainer)
    playerList

  override def nextTurn(playerList: List[Player], lastTurn: Player): Player =
    val nextPlayer = field.player.nextTurn(playerList, lastTurn)
    field = new ScrabbleField(field.matrix,field.dictionary,field.squareFactory, field.languageEnum, nextPlayer,field.players,field.stoneContainer)
    nextPlayer

  override def addStones(player: Player, stones: List[Stone]): List[Player] =
    val playerList = field.player.addStones(player, field.players, stones)
    field = new ScrabbleField(field.matrix,field.dictionary,field.squareFactory, field.languageEnum, field.player, playerList, field.stoneContainer)
    playerList

  override def removeStones(player: Player, ListPlayers: List[Player],stones: List[Stone]): List[Player] =
    val playerList = field.player.removeStones(player, ListPlayers, stones)
    field = new ScrabbleField(field.matrix,field.dictionary,field.squareFactory, field.languageEnum, playerList(playerList.indexOf(player)), playerList, field.stoneContainer)
    playerList

  override def OnlyRequiredStones(notRequiredStones: List[Stone], word: String): List[Stone] = {
    field.player.OnlyRequiredStones(notRequiredStones, word)
  }

  override def gamestartPlayStones(languageEnum: LanguageEnum): List[Stone] = {
    val StoneContainerStart = field.stoneContainer.gamestartPlayStones(languageEnum)
    field = new ScrabbleField(field.matrix,field.dictionary,field.squareFactory, field.languageEnum, field.player, field.players, new StoneContainer(StoneContainerStart))
    StoneContainerStart
  }

  override def drawRandomStone( Container: StoneContainer): Stone = {
    Container.drawRandomeStone(Container)
  }

  override def removeStonefromContainer(StoneToRemove : Stone,Container: StoneContainer): StoneContainer = {
    val newContainer = Container.removeStonefromContainer(StoneToRemove,Container)
    field = new ScrabbleField(field.matrix,field.dictionary,field.squareFactory, field.languageEnum, field.player, field.players, newContainer)
    newContainer
  }
  override def requestLanguage: ScrabbleField =
    notifyObservers(RequestEnterLanguage())
    field
  override def noSuchLanguage: ScrabbleField =
    notifyObservers(NoSuchLanguageScrabbleEvent())
    field
  override def displayLeaderBoard: List[Player] =
    notifyObservers(GameEndScrabbleEvent())
    field.players
  override def requestnewWord: ScrabbleField =
    notifyObservers(RequestNewWord())
    field
  override def wordAlreadyAddedToDictionarycontroller: ScrabbleField =
    notifyObservers(WordAlreadyAddedToDictionary())
    field
  override def wordAddedToDictionarycontroller: ScrabbleField =
    notifyObservers(WordAddedToDictionary())
    field
  override def invalidcoordinatescontroller: ScrabbleField =
    notifyObservers(InvalidCoordinates())
    field
  override def invalidInputcontroller: ScrabbleField =
    notifyObservers(InvalidInput())
    field
  override def notInDictionarycontroller: ScrabbleField =
    notifyObservers(NotInDictionary())
    field
  override def wordNotInDictionarycontroller: ScrabbleField =
    notifyObservers(WordNotInDictionary())
    field
  override def enterWordforDictionarycontroller: ScrabbleField =
    notifyObservers(EnterWordForDictionary())
    field
  override def stopcontroller: ScrabbleField =
    notifyObservers(Stop())
    field
  override def languageSettingcontroller: ScrabbleField =
    notifyObservers(LanguageSetting())
    field
  override def enterWordcontroller: ScrabbleField =
    notifyObservers(EnterWord())
    field
  override def NoCorrectDirectioncontroller: ScrabbleField =
    notifyObservers(NoCorrectDirection())
    field
  override def wordDoesntFitcontroller: ScrabbleField =
    notifyObservers(WordDoesntFit())
    field
  override def exitcontroller: ScrabbleField =
    notifyObservers(Exit())
    field
  override def nameAlreadyTakencontroller: ScrabbleField =
    notifyObservers(NameAlreadyTaken())
    field
  override def enterNumberofPlayerscontroller: ScrabbleField =
    notifyObservers(EnterNumberOfPlayers())
    field
  override def invalidNumbercontroller: ScrabbleField =
    notifyObservers(InvalidNumber())
    field
  override def currentPlayercontroller: ScrabbleField =
    notifyObservers(CurrentPlayer())
    field
  override def enterPlayerNamecontroller: ScrabbleField =
    notifyObservers(EnterPlayerName())
    field
  override def nameCantBeEmptycontroller: ScrabbleField =
    notifyObservers(NameCantBeEmpty())
    field
  override def noteEnoughStonescontroller: ScrabbleField =
    notifyObservers(NotEnoughStones())
    field
  override def leaderBoardcontroller: ScrabbleField =
    notifyObservers(DisplayLeaderBoard())
    field
  override def thisPlayerList : List[Player] = field.players

  override def translateCoordinate(coordinate: String): (Int, Int) = {
    val coordinates = coordinate.split(" ")
    (coordinates(0).toUpperCase().toCharArray.sum - 'A', coordinates(1).toInt)
  }