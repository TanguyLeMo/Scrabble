package de.htwg.se.scrabble
package controller

import util.*
import model.ScrabbleField
import model.Player
import model.Matrix
import model.Stone
import model.StoneContainer
import de.htwg.se.scrabble.model
import de.htwg.se.scrabble.model.languages.{LanguageContext, LanguageEnum}
import de.htwg.se.scrabble.model.placeWordsAsMove


class Controller(var field: ScrabbleField) extends Observable:
  val undoManager = new util.UndoManager[ScrabbleField]
  
  def doAndPublish(doThis: placeWordsAsMove => ScrabbleField, move: placeWordsAsMove): Unit =
    val newState = doThis(move)
    field = undoManager.doStep(field, newState)
    notifyObservers(new RoundsScrabbleEvent)

  def doAndPublish(doThis: => ScrabbleField): Unit =
    field = doThis
    notifyObservers(new RoundsScrabbleEvent)

  def undo: ScrabbleField = {
    field = undoManager.undoStep(field)
    field
  }
  def redo: ScrabbleField = undoManager.redoStep(field)
  
  override def toString: String = field.toString

  def placeWord(xPosition: Int, yPosition: Int, direction: Char, word: String): ScrabbleField =
    val move = placeWordsAsMove(xPosition, yPosition, direction, word)
    val newState = field.placeWord(move.yPosition, move.xPosition, move.direction, move.word)
    field = undoManager.doStep(field, newState)
    field = field.addPoints(collectPoints(field.matrix, move.xPosition, move.yPosition, move.direction, move.word), field.player, field.players)
    notifyObservers(new RoundsScrabbleEvent)
    field

  def wordFits(xPosition: Int, yPosition: Int, direction: Char, word: String): Boolean =
    field.wordFits(xPosition, yPosition, direction, word)

  def fitsinBounds(xPosition: Int, yPosition: Int, direction : Char, word : String): Boolean =
    field.matrix.fitsInBounds(xPosition, yPosition, direction, word)
    
  def contains(word: String): Boolean = field.dictionary.contains(word)

  def add(word: String): String =
    field = new ScrabbleField(field.matrix, field.dictionary.addWord(word), field.squareFactory, field.languageEnum, field.player, field.players,field.stoneContainer)
    word
  
  def CreatePlayersList(playernames: Vector[String]): List[Player] =
    val playerList = field.player.CreatePlayersList(playernames)
    field = new ScrabbleField(field.matrix,field.dictionary,field.squareFactory, field.languageEnum, playerList.head,playerList,field.stoneContainer)
    playerList

  def sortListAfterPoints(players: List[Player]): List[Player] =
    field.player.sortListAfterPoints(players)

  def lettersAlreadyThere(xPosition: Int, yPosition: Int, direction: Char, word: String): List[Stone] =
    field.matrix.lettersAlreadyThere(xPosition, yPosition, direction, word)

  def setLanguageDictionary(language: LanguageEnum): ScrabbleField =
    field = field.setLanguageDictionary(language)
    notifyObservers(new DictionaryScrabbleEvent)
    field

  def collectPoints(matrix: Matrix, xPosition: Int, yPosition: Int, direction: Char, word: String): Int =
    field.scoringSystem.collectPoints(matrix, xPosition, yPosition, direction, word)

  def hasStones(notRequiredStones: List[Stone], word: String, player: Player): Boolean =
    player.hasStones(notRequiredStones, word, player)
    
  def languageContext: LanguageContext = field.languageContext

  def AddPoints(pointsToAdd: Int, player: Player, ListPlayers: List[Player]): List[Player] =
    val playerList = player.AddPoints(pointsToAdd, player, ListPlayers)
    field = new ScrabbleField(field.matrix,field.dictionary,field.squareFactory, field.languageEnum, field.player , playerList, field.stoneContainer)
    playerList

  def nextTurn(playerList: List[Player], lastTurn: Player): Player =
    val nextPlayer = field.player.nextTurn(playerList, lastTurn)
    field = new ScrabbleField(field.matrix,field.dictionary,field.squareFactory, field.languageEnum, nextPlayer,field.players,field.stoneContainer)
    nextPlayer

  def addStones(player: Player, stones: List[Stone]): List[Player] =
    val playerList = field.player.addStones(player, field.players, stones)
    field = new ScrabbleField(field.matrix,field.dictionary,field.squareFactory, field.languageEnum, field.player, playerList, field.stoneContainer)
    playerList

  def removeStones(player: Player, ListPlayers: List[Player],stones: List[Stone]): List[Player] =
    val playerList = field.player.removeStones(player, ListPlayers, stones)
    field = new ScrabbleField(field.matrix,field.dictionary,field.squareFactory, field.languageEnum, playerList(playerList.indexOf(player)), playerList, field.stoneContainer)
    playerList

  def OnlyRequiredStones(notRequiredStones: List[Stone], word: String): List[Stone] = {
    field.player.OnlyRequiredStones(notRequiredStones, word)
  }

  def gamestartPlayStones(languageEnum: LanguageEnum): List[Stone] = {
    val StoneContainerStart = field.stoneContainer.gamestartPlayStones(languageEnum)
    field = new ScrabbleField(field.matrix,field.dictionary,field.squareFactory, field.languageEnum, field.player, field.players, new StoneContainer(StoneContainerStart))
    StoneContainerStart
  }

  def drawRandomStone( Container: StoneContainer): Stone = {
    Container.drawRandomeStone(Container)
  }

  def removeStonefromContainer(StoneToRemove : Stone,Container: StoneContainer): StoneContainer = {
    val newContainer = Container.removeStonefromContainer(StoneToRemove,Container)
    field = new ScrabbleField(field.matrix,field.dictionary,field.squareFactory, field.languageEnum, field.player, field.players, newContainer)
    newContainer
  }


  def thisMatrix:Matrix =
    field.matrix

  def requestLanguage: ScrabbleField =
    notifyObservers(RequestEnterLanguage())
    field
  def noSuchLanguage: ScrabbleField =
    notifyObservers(NoSuchLanguageScrabbleEvent())
    field
  def displayLeaderBoard: List[Player] =
    notifyObservers(GameEndScrabbleEvent())
    field.players
  def requestnewWord: ScrabbleField =
    notifyObservers(RequestNewWord())
    field
  def wordAlreadyAddedToDictionarycontroller: ScrabbleField =
    notifyObservers(WordAlreadyAddedToDictionary())
    field
  def wordAddedToDictionarycontroller: ScrabbleField =
    notifyObservers(WordAddedToDictionary())
    field
  def invalidcoordinatescontroller: ScrabbleField =
    notifyObservers(InvalidCoordinates())
    field
  def invalidInputcontroller: ScrabbleField =
    notifyObservers(InvalidInput())
    field
  def notInDictionarycontroller: ScrabbleField =
    notifyObservers(NotInDictionary())
    field
  def wordNotInDictionarycontroller: ScrabbleField =
    notifyObservers(WordNotInDictionary())
    field
  def enterWordforDictionarycontroller: ScrabbleField =
    notifyObservers(EnterWordForDictionary())
    field
  def stopcontroller: ScrabbleField =
    notifyObservers(Stop())
    field
  def languageSettingcontroller: ScrabbleField =
    notifyObservers(LanguageSetting())
    field
  def enterWordcontroller: ScrabbleField =
    notifyObservers(EnterWord())
    field
  def NoCorrectDirectioncontroller: ScrabbleField =
    notifyObservers(NoCorrectDirection())
    field
  def wordDoesntFitcontroller: ScrabbleField =
    notifyObservers(WordDoesntFit())
    field
  def exitcontroller: ScrabbleField =
    notifyObservers(Exit())
    field
  def nameAlreadyTakencontroller: ScrabbleField =
    notifyObservers(NameAlreadyTaken())
    field
  def enterNumberofPlayerscontroller: ScrabbleField =
    notifyObservers(EnterNumberOfPlayers())
    field
  def invalidNumbercontroller: ScrabbleField =
    notifyObservers(InvalidNumber())
    field
  def currentPlayercontroller: ScrabbleField =
    notifyObservers(CurrentPlayer())
    field
  def enterPlayerNamecontroller: ScrabbleField =
    notifyObservers(EnterPlayerName())
    field
  def nameCantBeEmptycontroller: ScrabbleField =
    notifyObservers(NameCantBeEmpty())
    field
    
  def noteEnoughStonescontroller: ScrabbleField =
    notifyObservers(NotEnoughStones())
    field
  def leaderBoardcontroller: ScrabbleField =
    notifyObservers(DisplayLeaderBoard())
    field
  def thisPlayerList : List[Player] = field.players