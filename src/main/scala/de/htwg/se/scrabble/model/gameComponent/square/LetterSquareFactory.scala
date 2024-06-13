package de.htwg.se.scrabble.model.gameComponent.square

import de.htwg.se.scrabble.model.gameComponent.Stone

class LetterSquareFactory extends SquareFactory{
  override def createDoubleSquare(letter: Stone): ScrabbleSquare = new DoubleLetterSquare(letter)
  override def createTripleSquare(letter: Stone): ScrabbleSquare = new TripleLetterSquare(letter)
}
