package de.htwg.se.scrabble.model.gameComponent

trait StoneInterface {
  def symbol: Char
  def toString: String
  def equals(obj: Any): Boolean
}
