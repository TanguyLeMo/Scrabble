package de.htwg.se.scrabble.controller.ControllerComponent

import de.htwg.se.scrabble.model.languageComponent.LanguageContextInterface
import de.htwg.se.scrabble.util.LanguageEnum
import de.htwg.se.scrabble.model.gameComponent.*
import de.htwg.se.scrabble.util.placeWordsAsMove
import de.htwg.se.scrabble.util.Observable

trait ControllerInterface extends Observable{
  var field : ScrabbleFieldInterface
  def doAndPublish(doThis: placeWordsAsMove => ScrabbleFieldInterface, move: placeWordsAsMove): Unit
  def doAndPublish(doThis: => ScrabbleFieldInterface): Unit
  def undo: ScrabbleFieldInterface
  def redo: ScrabbleFieldInterface
  def placeWord(xPosition: Int, yPosition: Int, direction: Char, word: String): ScrabbleFieldInterface
  def wordFits(xPosition: Int, yPosition: Int, direction: Char, word: String): Boolean
  def contains(word: String): Boolean
  def add(word: String): String
  def CreatePlayersList(playernames: Vector[String]): List[PlayerInterface]
  def sortListAfterPoints(players:List[PlayerInterface]): List[PlayerInterface]
  def lettersAlreadyThere(xPosition: Int, yPosition: Int, direction: Char, word: String): List[StoneInterface]
  def setLanguageDictionary(language: LanguageEnum): ScrabbleFieldInterface
  def collectPoints(matrix: MatrixInterface,xPosition: Int, yPosition: Int, direction: Char, word: String): Int
  def hasStones(notRequiredStones: List[StoneInterface], word: String, player: PlayerInterface): Boolean
  def languageContext: LanguageContextInterface
  def AddPoints(pointsToAdd: Int, player: PlayerInterface, ListPlayers: List[PlayerInterface]): List[PlayerInterface]
  def nextTurn(playerList: List[PlayerInterface], lastTurn:PlayerInterface) : PlayerInterface
  def addStones(player: PlayerInterface, stones: List[StoneInterface]): List[PlayerInterface]
  def removeStones(player: PlayerInterface, ListPlayers: List[PlayerInterface], stones: List[StoneInterface]): List[PlayerInterface]
  def OnlyRequiredStones(notRequiredStones: List[StoneInterface], word: String): List[StoneInterface]
  def gamestartPlayStones(languageEnum: LanguageEnum):List[StoneInterface]
  def drawRandomStone(container: StoneContainerInterface): StoneInterface
  def removeStonefromContainer(StoneToRemove: StoneInterface, Container: StoneContainerInterface): StoneContainerInterface
  def requestLanguage: ScrabbleFieldInterface
  def noSuchLanguage: ScrabbleFieldInterface
  def displayLeaderBoard: List[PlayerInterface]
  def requestnewWord: ScrabbleFieldInterface
  def wordAlreadyAddedToDictionarycontroller: ScrabbleFieldInterface
  def wordAddedToDictionarycontroller: ScrabbleFieldInterface
  def invalidcoordinatescontroller: ScrabbleFieldInterface
  def invalidInputcontroller: ScrabbleFieldInterface
  def notInDictionarycontroller: ScrabbleFieldInterface
  def wordNotInDictionarycontroller: ScrabbleFieldInterface
  def enterWordforDictionarycontroller: ScrabbleFieldInterface
  def stopcontroller: ScrabbleFieldInterface
  def languageSettingcontroller: ScrabbleFieldInterface
  def enterWordcontroller: ScrabbleFieldInterface
  def NoCorrectDirectioncontroller: ScrabbleFieldInterface
  def wordDoesntFitcontroller: ScrabbleFieldInterface
  def exitcontroller: ScrabbleFieldInterface
  def nameAlreadyTakencontroller: ScrabbleFieldInterface 
  def enterNumberofPlayerscontroller: ScrabbleFieldInterface
  def invalidNumbercontroller: ScrabbleFieldInterface
  def currentPlayercontroller: ScrabbleFieldInterface
  def enterPlayerNamecontroller: ScrabbleFieldInterface
  def nameCantBeEmptycontroller: ScrabbleFieldInterface
  def noteEnoughStonescontroller: ScrabbleFieldInterface
  def leaderBoardcontroller: ScrabbleFieldInterface
  def thisPlayerList : List[PlayerInterface]
  def translateCoordinate(coordinate: String): (Int, Int)
  def save: Boolean
  def load: ScrabbleFieldInterface
  def changeLanguage(language: LanguageEnum): ScrabbleFieldInterface
  

}
