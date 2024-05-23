package de.htwg.se.scrabble.model.square

import de.htwg.se.scrabble.model.Stone

class TripleWordSquare(letter : Stone) extends ScrabbleSquare {
  override def scoreModifier: Int = 3
  override def color: String = Console.RED
  override def toString: String = color + letter.toString + Console.RESET
}
