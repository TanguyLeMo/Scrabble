package de.htwg.se.scrabble.model.square

import de.htwg.se.scrabble.model.Stone

abstract class WordSquare(val stone : Stone) extends ScrabbleSquare{
  override def scoreModifier: Int 
}
