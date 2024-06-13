package de.htwg.se.scrabble.model.gameComponent

trait ScoringSystemInterface {
  def collectPoints(matrix: Matrix, xPosition: Int, yPosition: Int, direction: Char, word: String): Int
  def collectPointsR(matrix: Matrix, yPosition: Int, xCoordinates: Int, direction: Char, word: String, currentIndex: Int, sum: Int, multiplication: Int): (Int, Int)
  def determinPoints(stone: Stone): Int

}
