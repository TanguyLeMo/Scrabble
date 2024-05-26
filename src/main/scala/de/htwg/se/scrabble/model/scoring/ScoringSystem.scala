package de.htwg.se.scrabble.model.scoring

import de.htwg.se.scrabble.model.square.WordSquareFactory
import de.htwg.se.scrabble.model.{Matrix, Stone}
import de.htwg.se.scrabble.model.square._

trait ScoringSystem {

  def collectPoints(matrix: Matrix, xPosition: Int, yPosition: Int, direction: Char, word: String): Int = {
    // Collect points for the newly placed word
    val (newWordSum, newWordMultiplication) = collectPointsR(matrix, xPosition, yPosition, direction, word, 0, 0, 1)
    val totalPointsNewWord = newWordSum * newWordMultiplication
    totalPointsNewWord
  }
    def collectPointsR(matrix: Matrix, yPosition: Int, xPosition: Int, direction: Char, word: String, currentIndex: Int, sum: Int, multiplication: Int): (Int, Int) = {
    if (currentIndex >= word.length || xPosition >= matrix.columns || yPosition >= matrix.rows || matrix.field(yPosition)(xPosition).isEmpty) {
      return (sum, multiplication)
    }
    val (newXPosition, newYPosition) = direction match {
      case 'H' => (yPosition + 1, xPosition)
      case 'V' => (yPosition, xPosition + 1)
    }
    val currentSquare = matrix.field(yPosition)(xPosition)
    val (newSum, newMultiplication) = currentSquare match {
      case wordSquare: WordSquare =>
        (sum + determinPoints(currentSquare.letter) , multiplication * currentSquare.scoreModifier)
      case letterSquare: LetterSquare =>
        (sum + (determinPoints(currentSquare.letter) * currentSquare.scoreModifier), multiplication)
      case _ =>
        (sum + determinPoints(currentSquare.letter) , multiplication)
    }

    print("newSum " + newSum + " newMultiplication " + newMultiplication + "\n")
    collectPointsR(matrix, newXPosition, newYPosition, direction, word, currentIndex + 1, newSum, newMultiplication)
  }



  def determinPoints(stone: Stone): Int
}
