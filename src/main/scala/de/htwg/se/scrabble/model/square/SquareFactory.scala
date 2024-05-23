package de.htwg.se.scrabble.model.square
import de.htwg.se.scrabble.model.Stone
abstract class SquareFactory{
  def createDoubleSquare(letter: Stone): ScrabbleSquare
  def createTripleSquare(letter: Stone) : ScrabbleSquare
}
