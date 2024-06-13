package de.htwg.se.scrabble.model.gameComponent
package squareBaseImpl

import de.htwg.se.scrabble.model.gameComponent.StoneInterface

class LetterSquareFactory extends SquareFactory{
  override def createDoubleSquare(letter: StoneInterface): ScrabbleSquare = new DoubleLetterSquare(letter)
  override def createTripleSquare(letter: StoneInterface): ScrabbleSquare = new TripleLetterSquare(letter)
}
