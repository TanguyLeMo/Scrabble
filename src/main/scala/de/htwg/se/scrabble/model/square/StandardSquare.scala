package de.htwg.se.scrabble.model.square
import de.htwg.se.scrabble.model.Stone
class StandardSquare(betterletter: Stone) extends ScrabbleSquare{
  override def color: String = "white"
  override def scoreModifier: Int = 1
  override def toString: String = letter.toString
  override def letter: Stone = betterletter
}
