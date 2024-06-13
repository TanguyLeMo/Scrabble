package de.htwg.se.scrabble.model.gameComponent.square

import de.htwg.se.scrabble.model.gameComponent.Stone

abstract class LetterSquare(val stone : Stone) extends ScrabbleSquare{
   def scoreModifier: Int 
}
