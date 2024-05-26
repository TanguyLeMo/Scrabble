package de.htwg.se.scrabble.model.square

import de.htwg.se.scrabble.model.Stone

class TripleWordSquare(stone : Stone) extends WordSquare(stone) {
  override def scoreModifier: Int = 3
  override def color: String = Console.RED
  override def toString: String = color + letter.toString + Console.RESET
  override def letter: Stone = stone
  override def update(stone: Stone): ScrabbleSquare = new TripleWordSquare(stone)
  override def isEmpty: Boolean = stone.symbol == '_'
}
