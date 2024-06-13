package de.htwg.se.scrabble.model.gameComponent.square

import de.htwg.se.scrabble.model.gameComponent.Stone
import de.htwg.se.scrabble.model.gameComponent.ScrabbleSquareInterface

trait ScrabbleSquare extends ScrabbleSquareInterface{
  def scoreModifier: Int
  def color : String
  override def toString: String
  def letter : Stone
  def update(stone: Stone): ScrabbleSquare
  def isEmpty: Boolean
}
