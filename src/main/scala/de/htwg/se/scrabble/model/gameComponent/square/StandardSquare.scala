package de.htwg.se.scrabble.model.gameComponent.square

import de.htwg.se.scrabble.model.gameComponent.Stone
class StandardSquare(stone: Stone) extends ScrabbleSquare{
  override def color: String = "white"
  override def scoreModifier: Int = 1
  override def toString: String = letter.toString
  override def letter: Stone = stone
  override def update(stone: Stone): ScrabbleSquare = new StandardSquare(stone)
  override def isEmpty: Boolean = stone.symbol == '_'
}
