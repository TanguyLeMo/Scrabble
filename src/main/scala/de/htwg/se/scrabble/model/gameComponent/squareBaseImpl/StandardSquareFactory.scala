package de.htwg.se.scrabble.model.gameComponent
package squareBaseImpl

import de.htwg.se.scrabble.model.gameComponent.StoneInterface

class StandardSquareFactory extends SquareFactory{
  override def createDoubleSquare(letter: StoneInterface): ScrabbleSquare = new StandardSquare(letter)
  override def createTripleSquare(letter: StoneInterface): ScrabbleSquare = new StandardSquare(letter)
}
