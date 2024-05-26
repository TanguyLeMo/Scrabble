package de.htwg.se.scrabble.model.square

import de.htwg.se.scrabble.model.Stone

abstract class LetterSquare(val stone : Stone) extends ScrabbleSquare{
   def scoreModifier: Int 
}
