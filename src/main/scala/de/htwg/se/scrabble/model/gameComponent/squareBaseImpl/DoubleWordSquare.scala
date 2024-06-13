package de.htwg.se.scrabble.model.gameComponent
package squareBaseImpl

import de.htwg.se.scrabble.model.gameComponent.StoneInterface

class DoubleWordSquare(stone : StoneInterface) extends WordSquare(stone) {
  override def scoreModifier: Int = 2
  override def color: String = Console.MAGENTA
  override def toString: String = color + letter.toString + Console.RESET
  override def letter: StoneInterface = stone
  override def isEmpty: Boolean = stone.symbol == '_'
  override def update(stone: StoneInterface): ScrabbleSquare = new DoubleWordSquare(stone)
}
