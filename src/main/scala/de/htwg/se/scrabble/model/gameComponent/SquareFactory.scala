package de.htwg.se.scrabble.model.gameComponent

import de.htwg.se.scrabble.model.gameComponent.{ScrabbleSquare, StoneInterface}

trait SquareFactory extends squareFactoryInterface{
  def createDoubleSquare(letter: StoneInterface): ScrabbleSquare
  def createTripleSquare(letter: StoneInterface) : ScrabbleSquare
}
