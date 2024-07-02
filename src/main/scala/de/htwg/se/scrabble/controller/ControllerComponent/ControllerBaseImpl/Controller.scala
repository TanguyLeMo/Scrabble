package de.htwg.se.scrabble.controller.ControllerComponent.ControllerBaseImpl

import com.google.inject.{Guice, Inject}
import de.htwg.se.scrabble.controller.ControllerComponent.ControllerInterface
import de.htwg.se.scrabble.model.gameComponent.ScrabbleFieldInterface
import de.htwg.se.scrabble.model.languageComponent.LanguageContextInterface
import de.htwg.se.scrabble.model.gameComponent.*
import de.htwg.se.scrabble.model.gameComponent.gameComponentBaseImpl.{ScrabbleField, StoneContainer}
import de.htwg.se.scrabble.model.gameState.GameStateBaseImpl.{JsonGameState, XmlGameState}
import de.htwg.se.scrabble.model.gameState.GameStateInterface
import de.htwg.se.scrabble.{Modules, util}
import de.htwg.se.scrabble.util.*
import scala.util.*


class Controller @Inject (var field: ScrabbleFieldInterface) extends ControllerInterface:
  val undoManager = new util.UndoManager[ScrabbleFieldInterface]
  
  val fileIO: GameStateInterface = Guice.createInjector(new Modules).getInstance(classOf[GameStateInterface])
  
  override def doAndPublish(doThis: placeWordsAsMove => ScrabbleFieldInterface, move: placeWordsAsMove): Unit =
    val newState = doThis(move)
    field = undoManager.doStep(field, newState)
    notifyObservers(new RoundsScrabbleEvent)

  override def save: Boolean = fileIO.save(field)
  override def changeLanguage(language: LanguageEnum): ScrabbleFieldInterface = {
    field = field.setLanguageDictionary(language)
    notifyObservers(new RoundsScrabbleEvent)
    field
  }
  
  override def doAndPublish(doThis: => ScrabbleFieldInterface): Unit =
    field = doThis
    notifyObservers(new RoundsScrabbleEvent)

  override def undo: ScrabbleFieldInterface =
    field = undoManager.undoStep(field)
    field

  override def redo: ScrabbleFieldInterface = undoManager.redoStep(field)

  override def load: ScrabbleFieldInterface =
    field = fileIO.load 
    notifyObservers(new RoundsScrabbleEvent)
    field

  override def toString: String = field.toString

  override def placeWord(xPosition: Int, yPosition: Int, direction: Char, word: String): ScrabbleFieldInterface =
    val move = placeWordsAsMove(xPosition, yPosition, direction, word)
    val newState = field.placeWord(move.yPosition, move.xPosition, move.direction, move.word)
    field = undoManager.doStep(field, newState)
    field = field.addPoints(collectPoints(field.matrix, move.xPosition, move.yPosition, move.direction, move.word), field.player, field.players)
    field

  override def wordFits(xPosition: Int, yPosition: Int, direction: Char, word: String): Boolean =
    field.wordFits(xPosition, yPosition, direction, word)
  
  override def contains(word: String): Boolean = field.dictionary.contains(word)

  override def add(word: String): String =
    field = new ScrabbleField(field.matrix, field.dictionary.addWord(word), field.squareFactory, field.languageEnum, field.player, field.players, field.stoneContainer)
    word

  override def CreatePlayersList(playernames: Vector[String]): List[PlayerInterface] =
    val playerList = field.player.CreatePlayersList(playernames)
    field = new ScrabbleField(field.matrix,field.dictionary,field.squareFactory, field.languageEnum, playerList.head, playerList,field.stoneContainer)
    playerList

  override def sortListAfterPoints(players: List[PlayerInterface]): List[PlayerInterface] =
    field.player.sortListAfterPoints(players)

  override def lettersAlreadyThere(xPosition: Int, yPosition: Int, direction: Char, word: String): List[StoneInterface] =
    field.matrix.lettersAlreadyThere(xPosition, yPosition, direction, word)

  override def setLanguageDictionary(language: LanguageEnum): ScrabbleFieldInterface =
    field = field.setLanguageDictionary(language)
    notifyObservers(new DictionaryScrabbleEvent)
    field

  override def collectPoints(matrix: MatrixInterface, xPosition: Int, yPosition: Int, direction: Char, word: String): Int =
    field.scoringSystem.collectPoints(field.matrix, xPosition, yPosition, direction, word)

  override def hasStones(notRequiredStones: List[StoneInterface], word: String, player: PlayerInterface): Boolean =
    player.hasStones(notRequiredStones, word, player)

  override def languageContext: LanguageContextInterface = field.languageContext

  override def AddPoints(pointsToAdd: Int, player: PlayerInterface, ListPlayers: List[PlayerInterface]): List[PlayerInterface] =
    val playerList = player.AddPoints(pointsToAdd, player, ListPlayers)
    field = new ScrabbleField(field.matrix,field.dictionary,field.squareFactory, field.languageEnum, field.player , playerList, field.stoneContainer)
    playerList

  override def nextTurn(playerList: List[PlayerInterface], lastTurn: PlayerInterface): PlayerInterface =
    val nextPlayer = field.player.nextTurn(playerList, lastTurn)
    field = new ScrabbleField(field.matrix,field.dictionary,field.squareFactory, field.languageEnum, nextPlayer,field.players,field.stoneContainer)
    notifyObservers(new RoundsScrabbleEvent)
    notifyObservers(new EnterWord)
    nextPlayer

  override def addStones(player: PlayerInterface, stones: List[StoneInterface]): List[PlayerInterface] =
    val playerList = field.player.addStones(player, field.players, stones)
    field = new ScrabbleField(field.matrix,field.dictionary,field.squareFactory, field.languageEnum, field.player, playerList, field.stoneContainer)
    playerList

  override def removeStones(player: PlayerInterface, ListPlayers: List[PlayerInterface],stones: List[StoneInterface]): List[PlayerInterface] =
    val playerList = field.player.removeStones(player, ListPlayers, stones)
    field = new ScrabbleField(field.matrix,field.dictionary,field.squareFactory, field.languageEnum, playerList(playerList.indexOf(player)), playerList, field.stoneContainer)
    playerList

  override def OnlyRequiredStones(notRequiredStones: List[StoneInterface], word: String): List[StoneInterface] =
    field.player.OnlyRequiredStones(notRequiredStones, word)

  override def gamestartPlayStones(languageEnum: LanguageEnum): List[StoneInterface] =
    val StoneContainerStart = field.stoneContainer.gamestartPlayStones(languageEnum)
    field = new ScrabbleField(field.matrix,field.dictionary,field.squareFactory, field.languageEnum, field.player, field.players, new StoneContainer(StoneContainerStart))
    StoneContainerStart

  override def drawRandomStone( Container: StoneContainerInterface): StoneInterface =
    Container.drawRandomeStone(Container)

  override def removeStonefromContainer(StoneToRemove : StoneInterface,Container: StoneContainerInterface): StoneContainerInterface =
    val newContainer = Container.removeStonefromContainer(StoneToRemove,Container)
    field = new ScrabbleField(field.matrix,field.dictionary,field.squareFactory, field.languageEnum, field.player, field.players, newContainer)
    newContainer

  override def requestLanguage: ScrabbleFieldInterface =
    notifyObservers(RequestEnterLanguage())
    field
  override def noSuchLanguage: ScrabbleFieldInterface =
    notifyObservers(NoSuchLanguageScrabbleEvent())
    field

  override def displayLeaderBoard: List[PlayerInterface] =
    notifyObservers(GameEndScrabbleEvent())
    field.players

  override def requestnewWord: ScrabbleFieldInterface =
    notifyObservers(RequestNewWord())
    field

  override def wordAlreadyAddedToDictionarycontroller: ScrabbleFieldInterface =
    notifyObservers(WordAlreadyAddedToDictionary())
    field

  override def wordAddedToDictionarycontroller: ScrabbleFieldInterface =
    notifyObservers(WordAddedToDictionary())
    field

  override def invalidcoordinatescontroller: ScrabbleFieldInterface =
    notifyObservers(InvalidCoordinates())
    field

  override def invalidInputcontroller: ScrabbleFieldInterface =
    notifyObservers(InvalidInput())
    field

  override def notInDictionarycontroller: ScrabbleFieldInterface =
    notifyObservers(NotInDictionary())
    field

  override def wordNotInDictionarycontroller: ScrabbleFieldInterface =
    notifyObservers(WordNotInDictionary())
    field

  override def enterWordforDictionarycontroller: ScrabbleFieldInterface =
    notifyObservers(EnterWordForDictionary())
    field

  override def stopcontroller: ScrabbleFieldInterface =
    notifyObservers(Stop())
    field

  override def languageSettingcontroller: ScrabbleFieldInterface =
    notifyObservers(LanguageSetting())
    field

  override def enterWordcontroller: ScrabbleFieldInterface =
    notifyObservers(EnterWord())
    field

  override def NoCorrectDirectioncontroller: ScrabbleFieldInterface =
    notifyObservers(NoCorrectDirection())
    field

  override def wordDoesntFitcontroller: ScrabbleFieldInterface =
    notifyObservers(WordDoesntFit())
    field

  override def exitcontroller: ScrabbleFieldInterface =
    notifyObservers(Exit())
    field

  override def nameAlreadyTakencontroller: ScrabbleFieldInterface =
    notifyObservers(NameAlreadyTaken())
    field

  override def enterNumberofPlayerscontroller: ScrabbleFieldInterface =
    notifyObservers(EnterNumberOfPlayers())
    field

  override def invalidNumbercontroller: ScrabbleFieldInterface =
    notifyObservers(InvalidNumber())
    field

  override def currentPlayercontroller: ScrabbleFieldInterface =
    notifyObservers(CurrentPlayer())
    field

  override def enterPlayerNamecontroller: ScrabbleFieldInterface =
    notifyObservers(EnterPlayerName())
    field

  override def nameCantBeEmptycontroller: ScrabbleFieldInterface =
    notifyObservers(NameCantBeEmpty())
    field

  override def noteEnoughStonescontroller: ScrabbleFieldInterface =
    notifyObservers(NotEnoughStones())
    field

  override def leaderBoardcontroller: ScrabbleFieldInterface =
    notifyObservers(DisplayLeaderBoard())
    field

  override def thisPlayerList : List[PlayerInterface] = field.players

  override def translateCoordinate(coordinate: String): (Int, Int) =
    val coordinates = coordinate.split(" ")
    (coordinates(0).toUpperCase().toCharArray.sum - 'A', coordinates(1).toInt)
  