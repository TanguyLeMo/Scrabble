package de.htwg.se.scrabble.model.gameComponent

import de.htwg.se.scrabble.util.LanguageEnum

trait ScrabbleFieldInterface {
  def labelingXAxis(currcolum: Int): String
  def addSpace(numSpaceToAdd: Int): String
  def placeWord(yPosition: Int, xCoordinates : Int, direction :Char, word : String): ScrabbleField
  def wordFits(xPosition: Int, yPosition: Int, direction: Char, word: String) : Boolean
  def translateLetter(n: Int): String
  def concatenateRows(currentRow: Int): String
  def concatenateColumnsOfCurrentRow(currentRow: Int, currentColumn: Int): String
  def toString: String
  def equals(that: Any): Boolean
  def removeWord (yPosition: Int, xPosition: Int, direction: Char, word: String): ScrabbleField
  def addDictionaryWord(word: String): ScrabbleField
  def setLanguageDictionary(languagee: LanguageEnum): ScrabbleField
  def previousTurn(currentTurn: Player): ScrabbleField
  def addPoints(pointsToAdd: Int, player: Player, ListPlayers: List[Player]): ScrabbleField
  
}
