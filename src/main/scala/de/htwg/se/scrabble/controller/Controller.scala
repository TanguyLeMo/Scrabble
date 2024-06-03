package de.htwg.se.scrabble
package controller
import de.htwg.se.scrabble.aview.languages.LanguageEnum
import util._
import model.ScrabbleField
import model.Player
import model.Matrix
import de.htwg.se.scrabble.model
import de.htwg.se.scrabble.model.placeWordsAsMove


class Controller(var field: ScrabbleField) extends Observable:
  val undoManager = new util.UndoManager[ScrabbleField]
  
  def doAndPublish(doThis: placeWordsAsMove => ScrabbleField, move: placeWordsAsMove): Unit =
    field = doThis(move)
    AddPoints(collectPoints(field.matrix, move.xPosition, move.yPosition, move.direction, move.word), field.player, field.players)
    notifyObservers(new RoundsEvent)

  def doAndPublish(doThis: => ScrabbleField): Unit =
    field = doThis
    notifyObservers(new RoundsEvent)

  def undo: ScrabbleField = {undoManager.undoStep(field) }
  
  def redo: ScrabbleField = undoManager.redoStep(field)
  
  override def toString: String = field.toString

  def placeWord(xPosition: Int, yPosition: Int, direction: Char, word: String): ScrabbleField =
    undoManager.doStep(field, PutCommand(placeWordsAsMove(xPosition, yPosition, direction, word)))

  def wordFits(xPosition: Int, yPosition: Int, direction: Char, word: String): Boolean =
    field.wordFits(xPosition, yPosition, direction, word)

  def fitsinBounds(xPosition: Int, yPosition: Int, direction : Char, word : String): Boolean =
    field.matrix.fitsInBounds(xPosition, yPosition, direction, word)
    
  def contains(word: String): Boolean = field.dictionary.contains(word)

  def add(word: String): String =
    field = new ScrabbleField(field.matrix, field.dictionary.addWord(word), field.squareFactory, field.languageEnum, field.player, field.players)
    word
  
  def CreatePlayersList(playernames: Vector[String]): List[Player] =
    val playerList = field.player.CreatePlayersList(playernames)
    field = new ScrabbleField(field.matrix,field.dictionary,field.squareFactory, field.languageEnum, playerList.head,playerList)
    playerList

  def sortListAfterPoints(players: List[Player]): List[Player] =
    field.player.sortListAfterPoints(players)

  def setLanguageDictionary(language: LanguageEnum): ScrabbleField =
    field = field.setLanguageDictionary(language)
    notifyObservers(new DictionaryEvent)
    field

  def collectPoints(matrix: Matrix, xPosition: Int, yPosition: Int, direction: Char, word: String): Int =
    field.scoringSystem.collectPoints(matrix, xPosition, yPosition, direction, word)
    

  def AddPoints(pointsToAdd: Int, player: Player, ListPlayers: List[Player]): List[Player] =
    val playerList = player.AddPoints(pointsToAdd, player, ListPlayers)
    field = new ScrabbleField(field.matrix,field.dictionary,field.squareFactory, field.languageEnum, field.player , playerList)
    playerList

  def nextTurn(playerList: List[Player], lastTurn: Player): Player =
    val nextPlayer = field.player.nextTurn(playerList, lastTurn)
    field = new ScrabbleField(field.matrix,field.dictionary,field.squareFactory, field.languageEnum, nextPlayer,field.players)
    nextPlayer

  def thisMatrix:Matrix = field.matrix
    
  def requestLanguage: ScrabbleField =
    notifyObservers(RequestEnterLanguage())
    field
  def noSuchLanguage: ScrabbleField =
    notifyObservers(NoSuchLanguageEvent())
    field
  def displayLeaderBoard: List[Player] =
    notifyObservers(GameEndEvent())
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
    
  def thisPlayerList : List[Player] = field.players