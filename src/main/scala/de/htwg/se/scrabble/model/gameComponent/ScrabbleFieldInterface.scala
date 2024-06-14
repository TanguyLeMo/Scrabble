package de.htwg.se.scrabble.model.gameComponent

import de.htwg.se.scrabble.model.gameComponent.squareBaseImpl.StandardSquareFactory
import de.htwg.se.scrabble.model.languageComponent.LanguageContextInterface
import de.htwg.se.scrabble.util.LanguageEnum

trait ScrabbleFieldInterface {
  val matrix: MatrixInterface
  val dictionary: DictionaryInterface
  val squareFactory: StandardSquareFactory
  val languageEnum: LanguageEnum
  val player : PlayerInterface
  val players: List[PlayerInterface]
  val stoneContainer: StoneContainerInterface
  val scoringSystem: ScoringSystemInterface
  val languageSettings: String
  val languageContext: LanguageContextInterface

  def labelingXAxis(currcolum: Int): String
  def addSpace(numSpaceToAdd: Int): String
  def placeWord(yPosition: Int, xCoordinates : Int, direction :Char, word : String): ScrabbleFieldInterface
  def wordFits(xPosition: Int, yPosition: Int, direction: Char, word: String) : Boolean
  def translateLetter(n: Int): String
  def concatenateRows(currentRow: Int): String
  def concatenateColumnsOfCurrentRow(currentRow: Int, currentColumn: Int): String
  def toString: String
  def equals(that: Any): Boolean
  def removeWord (yPosition: Int, xPosition: Int, direction: Char, word: String): ScrabbleFieldInterface
  def addDictionaryWord(word: String): ScrabbleFieldInterface
  def setLanguageDictionary(languagee: LanguageEnum): ScrabbleFieldInterface
  def previousTurn(currentTurn: PlayerInterface): ScrabbleFieldInterface
  def addPoints(pointsToAdd: Int, player: PlayerInterface, ListPlayers: List[PlayerInterface]): ScrabbleFieldInterface

}
