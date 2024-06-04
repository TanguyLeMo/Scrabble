package de.htwg.se.scrabble
package controller
import de.htwg.se.scrabble.aview.languages.LanguageEnum
import util.Observable
import model.ScrabbleField
import model.Player
import model.Matrix
import model.Stone
import model.StoneContainer
import de.htwg.se.scrabble.model
import de.htwg.se.scrabble.model.Move

class Controller(var field: ScrabbleField) extends Observable:
  val undoManager = new util.UndoManager[ScrabbleField]
  
  def doAndPublish(doThis: Move => ScrabbleField, move: Move): Unit =
    field = doThis(move)
    AddPoints(collectPoints(field.matrix, move.xPosition, move.yPosition, move.direction, move.word), field.player, field.players)
    notifyObservers()

  def doAndPublish(doThis: => ScrabbleField): Unit =
    field = doThis
    notifyObservers()

  def undo: ScrabbleField = {undoManager.undoStep(field) }
  
  def redo: ScrabbleField = undoManager.redoStep(field)
  
  override def toString: String = field.toString

  def placeWord(xPosition: Int, yPosition: Int, direction: Char, word: String): ScrabbleField =
    undoManager.doStep(field, PutCommand(Move(xPosition, yPosition, direction, word)))

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
    field

  def collectPoints(matrix: Matrix, xPosition: Int, yPosition: Int, direction: Char, word: String): Int =
    field.scoringSystem.collectPoints(matrix, xPosition, yPosition, direction, word)

  def hasStones(notRequiredStones: List[Stone], word: String, player: Player): Boolean =
    player.hasStones(notRequiredStones, word, player)
  
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

  
  
  def thisPlayerList : List[Player] = field.players