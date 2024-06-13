package de.htwg.se.scrabble.model.square
import de.htwg.se.scrabble.model.Stone
trait SquareFactory{
  def createDoubleSquare(letter: Stone): ScrabbleSquare
  def createTripleSquare(letter: Stone) : ScrabbleSquare
}
