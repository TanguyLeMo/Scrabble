package de.htwg.se.scrabble.model.gameComponent
package gameComponentBaseImpl

import de.htwg.se.scrabble.model.gameComponent.StoneInterface
class Stone (letter: Char)extends StoneInterface:
  def symbol: Char = letter
  def this() =this('_')
  override def toString: String = letter.toString
  override def equals(obj: Any): Boolean = obj match
    case other: Stone => this.letter == other.symbol
    case _ => false

