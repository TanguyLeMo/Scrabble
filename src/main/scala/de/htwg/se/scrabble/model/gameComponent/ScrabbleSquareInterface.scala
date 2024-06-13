package de.htwg.se.scrabble.model.gameComponent
import de.htwg.se.scrabble.model.gameComponent.square.ScrabbleSquare

trait ScrabbleSquareInterface{
  def scoreModifier: Int

  def color: String

  override def toString: String

  def letter: Stone

  def update(stone: Stone): ScrabbleSquare

  def isEmpty: Boolean

}
