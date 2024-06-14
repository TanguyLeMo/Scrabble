package de.htwg.se.scrabble.model.gameComponent

import com.google.inject.Inject
import de.htwg.se.scrabble.model.gameComponent.{ScrabbleSquare, StoneInterface}

trait SquareFactory @Inject extends squareFactoryInterface{
  def createDoubleSquare(letter: StoneInterface): ScrabbleSquare
  def createTripleSquare(letter: StoneInterface) : ScrabbleSquare
}
