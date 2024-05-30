package de.htwg.se.scrabble.model.scoring

import de.htwg.se.scrabble.model.square.WordSquareFactory
import de.htwg.se.scrabble.model.{Matrix, Stone}
import de.htwg.se.scrabble.model.square._

trait ScoringSystem {

  def collectPoints(matrix: Matrix, xPosition: Int, yPosition: Int, direction: Char, word: String): Int = {
    // Collect points for the newly placed word
    val (newWordSum, newWordMultiplication) = collectPointsR(matrix, xPosition, yPosition, if(direction == 'H') 'V' else 'H', word, 0, 0, 1)
    val totalPointsNewWord = newWordSum * newWordMultiplication
    totalPointsNewWord
  }
    def collectPointsR(matrix: Matrix, xCoordinates: Int, yCoordinates: Int, direction: Char, word: String, currentIndex: Int, sum: Int, multiplication: Int): (Int, Int) = {
    if (currentIndex >= word.length || yCoordinates >= matrix.columns || xCoordinates >= matrix.rows || matrix.field(xCoordinates)(yCoordinates).isEmpty) {
      return (sum, multiplication)
    }
    val (newXPosition, newYPosition) = direction match {
      case 'H' => (xCoordinates + 1, yCoordinates)
      case 'V' => (xCoordinates, yCoordinates + 1)
    }
      print("xPosition: " + yCoordinates + " yPosition: " + xCoordinates + " direction: " + direction + " word: " + word + " currentIndex: " + currentIndex + " sum: " + sum + " multiplication: " + multiplication + "\n")
    val currentSquare = matrix.field(xCoordinates)(yCoordinates)
    val (newSum, newMultiplication) = currentSquare match {
      case wordSquare: WordSquare =>
        (sum + determinPoints(currentSquare.letter) , multiplication * currentSquare.scoreModifier)
      case letterSquare: LetterSquare =>
        (sum + (determinPoints(currentSquare.letter) * currentSquare.scoreModifier), multiplication)
      case _ =>
        (sum + determinPoints(currentSquare.letter) , multiplication)
    }
    collectPointsR(matrix, newXPosition, newYPosition, direction, word, currentIndex + 1, newSum, newMultiplication)
  }



  def determinPoints(stone: Stone): Int
}
