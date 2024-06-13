package de.htwg.se.scrabble.model.gameComponent
package squareBaseImpl

import de.htwg.se.scrabble.model.gameComponent.StoneInterface

class DoubleLetterSquare(stone: StoneInterface) extends LetterSquare(stone) {
  override def color: String = Console.BLUE
  override def scoreModifier: Int = 2
  override def toString: String = color + letter.toString + Console.RESET

  override def letter: StoneInterface = stone
  override def update(stone: StoneInterface): ScrabbleSquare = new DoubleLetterSquare(stone)
  override def isEmpty: Boolean = stone.symbol == '_'
}
