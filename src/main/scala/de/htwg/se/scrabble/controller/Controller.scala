package de.htwg.se.scrabble
package controller
import de.htwg.se.scrabble.aview.languages.LanguageEnum
import util.Observable
import model.ScrabbleField
import model.Player
import model.Matrix
import de.htwg.se.scrabble.model

class Controller(var field: ScrabbleField) extends Observable:

  override def toString: String = field.toString

  def placeWord(xPosition: Int, yPosition: Int, direction: Char, word: String): Unit =
    field = field.placeWord(xPosition, yPosition, direction, word)
    notifyObservers()

  def wordFits(xPosition: Int, yPosition: Int, direction: Char, word: String): Boolean =
    field.wordFits(xPosition, yPosition, direction, word)

  def fitsinBounds(xPosition: Int, yPosition: Int, direction : Char, word : String): Boolean =
    field.matrix.fitsInBounds(xPosition, yPosition, direction, word)
    
  def contains(word: String): Boolean = field.dictionary.contains(word)

  def add(word: String): String =
    field = new ScrabbleField(field.matrix, field.dictionary.addWord(word), field.squareFactory, field.languageEnum, field.player, field.players)
    word
  
  def CreatePlayersList(playernames: Vector[String]): List[Player] =
    player.CreatePlayersList(playernames)

  def sortListAfterPoints(players: List[Player]): List[Player] =
    field.player.sortListAfterPoints(players)

  def setLanguageDictionary(language: LanguageEnum): ScrabbleField =
    field.setLanguageDictionary(language)