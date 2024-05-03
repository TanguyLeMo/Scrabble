package de.htwg.se.scrabble
package controller

import de.htwg.se.scrabble.util.Observer
import util.Observable
import model.ScrabbleField

class Controller(var field: ScrabbleField) extends Observable:

  override def toString: String = field.toString

  def placeWord(xPosition: Int, yPosition: Int, direction: Char, word: String): Unit =
    field = field.placeWord(xPosition, yPosition, direction, word)
    notifyObservers()

  def wordFits(xPosition: Int, yPosition: Int, direction: Char, word: String): Boolean =
    field.wordFits(xPosition, yPosition, direction, word)

  def fitsinBounds(xPosition: Int, yPosition: Int, direction : Char, word : String): Boolean =
    field.matrix.fitsInBounds(xPosition, yPosition, direction, word)