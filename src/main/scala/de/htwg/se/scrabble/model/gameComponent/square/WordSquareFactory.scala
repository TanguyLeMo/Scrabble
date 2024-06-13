package de.htwg.se.scrabble.model.gameComponent.square

import de.htwg.se.scrabble.model.gameComponent.Stone

class WordSquareFactory extends SquareFactory{
  override def createDoubleSquare(letter: Stone): ScrabbleSquare = new DoubleWordSquare(letter)
  override def createTripleSquare(letter: Stone): ScrabbleSquare = new TripleWordSquare(letter)
}
