package de.htwg.se.scrabble.model.gameComponent
package squareBaseImpl

import de.htwg.se.scrabble.model.gameComponent.StoneInterface

class TripleLetterSquare(stone : StoneInterface) extends LetterSquare(stone) {
  override def scoreModifier: Int = 3
  override def color: String = Console.YELLOW
  override def toString: String = color + letter.toString + Console.RESET
  override def letter: StoneInterface = stone

  override def isEmpty: Boolean = stone.symbol == '_'
  override def update(stone: StoneInterface): ScrabbleSquare = new TripleLetterSquare(stone)
}
