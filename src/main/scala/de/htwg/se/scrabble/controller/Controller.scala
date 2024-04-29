package de.htwg.se.scrabble
package controller

import util.Observable
import model.ScrabbleField

class Controller(scrabbleField: ScrabbleField) extends Observable:

  override def toString: String = scrabbleField.toString

  def placeWord(xPosition: Int, yPosition: Int, direction: Char, word: String): Unit =
    scrabbleField.placeWord(xPosition, yPosition, direction, word)
    notifyObservers()

  def wordFits(xPosition: Int, yPosition: Int, direction: Char, word: String): Unit =
    scrabbleField.wordFits(xPosition, yPosition, direction, word)
    notifyObservers()

  def fitsinBounds(xPosition: Int, yPosition: Int, direction : Char, word : String): Unit =
    scrabbleField.matrix.fitsInBounds(xPosition, yPosition, direction, word)
    notifyObservers()
