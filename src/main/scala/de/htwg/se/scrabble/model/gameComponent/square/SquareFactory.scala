package de.htwg.se.scrabble.model.gameComponent.square

import de.htwg.se.scrabble.model.gameComponent.squareInterface

import de.htwg.se.scrabble.model.gameComponent.Stone
abstract class SquareFactory extends squareInterface{
  def createDoubleSquare(letter: Stone): ScrabbleSquare
  def createTripleSquare(letter: Stone) : ScrabbleSquare
}
