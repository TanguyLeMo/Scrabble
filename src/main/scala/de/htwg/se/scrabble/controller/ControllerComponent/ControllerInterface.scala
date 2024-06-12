package de.htwg.se.scrabble.controller.ControllerComponent

import de.htwg.se.scrabble.model.languageComponent.LanguageContextInterface
import de.htwg.se.scrabble.model.languageComponent.languages.LanguageEnum
import de.htwg.se.scrabble.model.{Matrix, Player, ScrabbleField, Stone, StoneContainer, placeWordsAsMove}
import de.htwg.se.scrabble.util.Observable

trait ControllerInterface extends Observable{
  var field : ScrabbleField
  def doAndPublish(doThis: placeWordsAsMove => ScrabbleField, move: placeWordsAsMove): Unit
  def doAndPublish(doThis: => ScrabbleField): Unit
  def undo: ScrabbleField
  def redo: ScrabbleField
  def placeWord(xPosition: Int, yPosition: Int, direction: Char, word: String): ScrabbleField
  def wordFits(xPosition: Int, yPosition: Int, direction: Char, word: String): Boolean
  def fitsinBounds(xPosition: Int, yPosition: Int, direction : Char, word : String): Boolean
  def contains(word: String): Boolean
  def add(word: String): String
  def CreatePlayersList(playernames: Vector[String]): List[Player]
  def sortListAfterPoints(players:List[Player]): List[Player]
  def lettersAlreadyThere(xPosition: Int, yPosition: Int, direction: Char, word: String): List[Stone]
  def setLanguageDictionary(language: LanguageEnum): ScrabbleField
  def collectPoints(matrix: Matrix, xPosition: Int, yPosition: Int, direction: Char, word: String): Int
  def hasStones(notRequiredStones: List[Stone], word: String, player: Player): Boolean
  def languageContext: LanguageContextInterface
  def AddPoints(pointsToAdd: Int, player: Player, ListPlayers: List[Player]): List[Player]
  def nextTurn(playerList: List[Player], lastTurn:Player) : Player
  def addStones(player: Player, stones: List[Stone]): List[Player]
  def removeStones(player: Player, ListPlayers: List[Player], stones: List[Stone]): List[Player]
  def OnlyRequiredStones(notRequiredStones: List[Stone], word: String): List[Stone]
  def gamestartPlayStones(languageEnum: LanguageEnum):List[Stone]
  def drawRandomStone(container: StoneContainer): Stone
  def removeStonefromContainer(StoneToRemove: Stone, Container: StoneContainer): StoneContainer
  def requestLanguage: ScrabbleField
  def noSuchLanguage: ScrabbleField
  def displayLeaderBoard: List[Player]
  def requestnewWord: ScrabbleField
  def wordAlreadyAddedToDictionarycontroller: ScrabbleField
  def wordAddedToDictionarycontroller: ScrabbleField
  def invalidcoordinatescontroller: ScrabbleField
  def invalidInputcontroller: ScrabbleField
  def notInDictionarycontroller: ScrabbleField
  def wordNotInDictionarycontroller: ScrabbleField 
  def enterWordforDictionarycontroller: ScrabbleField
  def stopcontroller: ScrabbleField
  def languageSettingcontroller: ScrabbleField
  def enterWordcontroller: ScrabbleField 
  def NoCorrectDirectioncontroller: ScrabbleField
  def wordDoesntFitcontroller: ScrabbleField
  def exitcontroller: ScrabbleField
  def nameAlreadyTakencontroller: ScrabbleField 
  def enterNumberofPlayerscontroller: ScrabbleField
  def invalidNumbercontroller: ScrabbleField
  def currentPlayercontroller: ScrabbleField
  def enterPlayerNamecontroller: ScrabbleField
  def nameCantBeEmptycontroller: ScrabbleField 
  def noteEnoughStonescontroller: ScrabbleField 
  def leaderBoardcontroller: ScrabbleField 
  def thisPlayerList : List[Player]
  def translateCoordinate(coordinate: String): (Int, Int)




}
