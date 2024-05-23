package de.htwg.se.scrabble.model.square

import de.htwg.se.scrabble.model.Stone

class DoubleWordSquare(betterletter : Stone) extends ScrabbleSquare{
  override def scoreModifier: Int = 2
  override def color: String = Console.MAGENTA
  override def toString: String = color + letter.toString + Console.RESET
  override def letter: Stone = betterletter

  override def update(stone: Stone): ScrabbleSquare = new DoubleWordSquare(stone)
}
