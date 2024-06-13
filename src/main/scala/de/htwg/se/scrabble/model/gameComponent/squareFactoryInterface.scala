package de.htwg.se.scrabble.model.gameComponent

trait squareFactoryInterface {
  def createDoubleSquare(letter: StoneInterface): ScrabbleSquare
  def createTripleSquare(letter: StoneInterface): ScrabbleSquare
}
