package de.htwg.se.scrabble.model.gameComponent.square

import de.htwg.se.scrabble.model.gameComponent.Stone

abstract class WordSquare(val stone : Stone) extends ScrabbleSquare{
  override def scoreModifier: Int 
}
