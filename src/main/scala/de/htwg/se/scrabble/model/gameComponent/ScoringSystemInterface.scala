package de.htwg.se.scrabble.model.gameComponent

trait ScoringSystemInterface {
  def collectPoints(matrix: MatrixInterface, xPosition: Int, yPosition: Int, direction: Char, word: String): Int
}
