package de.htwg.se.scrabble.model.gameComponent
package squareBaseImpl

import com.google.inject.Inject
import de.htwg.se.scrabble.model.gameComponent.StoneInterface
class StandardSquare @Inject (stone: StoneInterface) extends ScrabbleSquare{
  override def color: String = "white"
  override def scoreModifier: Int = 1
  override def toString: String = letter.toString
  override def letter: StoneInterface = stone
  override def update(stone: StoneInterface): ScrabbleSquare = new StandardSquare(stone)
  override def isEmpty: Boolean = stone.symbol == '_'
}
