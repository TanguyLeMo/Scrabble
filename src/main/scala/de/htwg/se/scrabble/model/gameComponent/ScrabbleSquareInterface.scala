package de.htwg.se.scrabble.model.gameComponent

trait ScrabbleSquareInterface{
  def scoreModifier: Int

  def color: String

  override def toString: String

  def letter: StoneInterface

  def update(stone: StoneInterface): ScrabbleSquare

  def isEmpty: Boolean

}
