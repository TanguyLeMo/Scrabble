package de.htwg.se.scrabble.model.gameComponent.square

import de.htwg.se.scrabble.model.gameComponent.Stone

class TripleLetterSquare(stone : Stone) extends LetterSquare(stone) {
  override def scoreModifier: Int = 3
  override def color: String = Console.YELLOW
  override def toString: String = color + letter.toString + Console.RESET
  override def letter: Stone = stone

  override def isEmpty: Boolean = stone.symbol == '_'
  override def update(stone: Stone): ScrabbleSquare = new TripleLetterSquare(stone)
}
