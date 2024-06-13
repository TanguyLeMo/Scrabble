package de.htwg.se.scrabble.model.gameComponent.square

import de.htwg.se.scrabble.model.gameComponent.Stone

class StandardSquareFactory extends SquareFactory{
  override def createDoubleSquare(letter: Stone): ScrabbleSquare = new StandardSquare(letter)
  override def createTripleSquare(letter: Stone): ScrabbleSquare = new StandardSquare(letter)
}
