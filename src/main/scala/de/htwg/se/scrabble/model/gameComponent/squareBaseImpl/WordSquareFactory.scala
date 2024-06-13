package de.htwg.se.scrabble.model.gameComponent
package squareBaseImpl

import de.htwg.se.scrabble.model.gameComponent.{SquareFactory, StoneInterface}

class WordSquareFactory extends SquareFactory{
  override def createDoubleSquare(letter: StoneInterface): ScrabbleSquare = new DoubleWordSquare(letter)
  override def createTripleSquare(letter: StoneInterface): ScrabbleSquare = new TripleWordSquare(letter)
}
