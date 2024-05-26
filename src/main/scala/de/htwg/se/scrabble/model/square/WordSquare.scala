package de.htwg.se.scrabble.model.square

abstract class WordSquare extends ScrabbleSquare{
  override def scoreModifier: Int 
}
