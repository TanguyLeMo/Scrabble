package de.htwg.se.scrabble.model.gameComponent
package squareBaseImpl

import de.htwg.se.scrabble.model.gameComponent.StoneInterface

class TripleWordSquare(stone : StoneInterface) extends WordSquare(stone) {
  override def scoreModifier: Int = 3
  override def color: String = Console.RED
  override def toString: String = color + letter.toString + Console.RESET
  override def letter: StoneInterface = stone
  override def update(stone: StoneInterface): ScrabbleSquare = new TripleWordSquare(stone)
  override def isEmpty: Boolean = stone.symbol == '_'
}
