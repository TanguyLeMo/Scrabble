package de.htwg.se.scrabble.model.gameComponent
import de.htwg.se.scrabble.model.gameComponent.square.ScrabbleSquare

trait squareInterface {
  def createDoubleSquare(letter: Stone): ScrabbleSquare

  def createTripleSquare(letter: Stone): ScrabbleSquare
}
