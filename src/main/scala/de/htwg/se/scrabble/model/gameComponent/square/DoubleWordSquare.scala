package de.htwg.se.scrabble.model.gameComponent.square

import de.htwg.se.scrabble.model.gameComponent.Stone

class DoubleWordSquare(stone : Stone) extends WordSquare(stone) {
  override def scoreModifier: Int = 2
  override def color: String = Console.MAGENTA
  override def toString: String = color + letter.toString + Console.RESET
  override def letter: Stone = stone
  override def isEmpty: Boolean = stone.symbol == '_'
  override def update(stone: Stone): ScrabbleSquare = new DoubleWordSquare(stone)
}
