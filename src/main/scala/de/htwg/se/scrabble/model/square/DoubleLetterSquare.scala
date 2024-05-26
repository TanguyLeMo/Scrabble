package de.htwg.se.scrabble.model.square

import de.htwg.se.scrabble.model.Stone

class DoubleLetterSquare(stone: Stone) extends LetterSquare(stone) {
  override def color: String = Console.BLUE
  override def scoreModifier: Int = 2
  override def toString: String = color + letter.toString + Console.RESET
  override def letter: Stone = stone
  override def update(stone: Stone): ScrabbleSquare = new DoubleLetterSquare(stone)
  override def isEmpty: Boolean = stone.symbol == '_'
}
