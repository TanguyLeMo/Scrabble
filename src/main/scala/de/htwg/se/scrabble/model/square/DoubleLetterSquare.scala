package de.htwg.se.scrabble.model.square

import de.htwg.se.scrabble.model.Stone

class DoubleLetterSquare(betterletter: Stone) extends ScrabbleSquare{
  override def color: String = Console.BLUE
  override def scoreModifier: Int = 2
  override def toString: String = color + letter.toString + Console.RESET
  override def letter: Stone = betterletter
  override def update(stone: Stone): ScrabbleSquare = new DoubleLetterSquare(stone)
}
