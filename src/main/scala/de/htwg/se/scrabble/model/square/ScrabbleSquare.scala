package de.htwg.se.scrabble.model.square

import de.htwg.se.scrabble.model.Stone

trait ScrabbleSquare {
  def scoreModifier: Int
  def color : String
  override def toString: String
  def letter : Stone
}
