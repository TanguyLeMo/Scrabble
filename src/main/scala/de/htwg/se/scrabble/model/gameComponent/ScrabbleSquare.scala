package de.htwg.se.scrabble.model.gameComponent

import de.htwg.se.scrabble.model.gameComponent.{ScrabbleSquareInterface, StoneInterface}

trait ScrabbleSquare extends ScrabbleSquareInterface{
  def scoreModifier: Int
  def color : String
  override def toString: String
  def letter : StoneInterface
  def update(stone: StoneInterface): ScrabbleSquare
  def isEmpty: Boolean
}
