package de.htwg.se.scrabble.model.square

trait ScrabbleSquare {
  def scoreModifier: Int
  def color : String
  override def toString: String
}
