package de.htwg.se.scrabble.model.square

import de.htwg.se.scrabble.model.Stone

class TripleLetterSquare(betterletter : Stone) extends ScrabbleSquare{
  override def scoreModifier: Int = 3
  override def color: String = Console.BLUE
  override def toString: String = color + letter.toString + Console.RESET
  override def letter: Stone = betterletter

  override def update(stone: Stone): ScrabbleSquare = new TripleLetterSquare(stone)
}
