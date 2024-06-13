package de.htwg.se.scrabble.model.square

import de.htwg.se.scrabble.model.Stone

class StandardSquareFactory extends SquareFactory{
  override def createDoubleSquare(letter: Stone): ScrabbleSquare = new StandardSquare(letter)
  override def createTripleSquare(letter: Stone): ScrabbleSquare = new StandardSquare(letter)
}
