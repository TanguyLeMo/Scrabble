package de.htwg.se.scrabble.model
class Stone (letter: Char):
  def symbol: Char = letter
  def this() =this('_')
  override def toString: String = letter.toString
  override def equals(obj: Any): Boolean = obj match
    case other: Stone => this.letter == other.symbol
    case _ => false

