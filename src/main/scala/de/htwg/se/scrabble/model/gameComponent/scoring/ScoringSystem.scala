package de.htwg.se.scrabble.model.gameComponent.scoring

import de.htwg.se.scrabble.model.gameComponent.{Matrix, Stone}
import de.htwg.se.scrabble.model.gameComponent.ScoringSystemInterface
import de.htwg.se.scrabble.model.gameComponent.square.{LetterSquare, WordSquare}
import de.htwg.se.scrabble.model.gameComponent.square.*

trait ScoringSystem extends ScoringSystemFactory{

  def collectPoints(matrix: Matrix, xPosition: Int, yPosition: Int, direction: Char, word: String): Int = {
    // Collect points for the newly placed word
    val (newWordSum, newWordMultiplication) = collectPointsR(matrix, xPosition, yPosition, if(direction == 'H') 'V' else 'H', word, 0, 0, 1)
    val totalPointsNewWord = newWordSum * newWordMultiplication
    totalPointsNewWord
  }
    def collectPointsR(matrix: Matrix, yPosition: Int, xCoordinates: Int, direction: Char, word: String, currentIndex: Int, sum: Int, multiplication: Int): (Int, Int) = {


      if (currentIndex >= word.length || xCoordinates >= matrix.columns || yPosition >= matrix.rows || matrix.field(yPosition)(xCoordinates).isEmpty) {
      return (sum, multiplication)
    }
    val (newXPosition, newYPosition) = direction match {
      case 'V' => (yPosition + 1, xCoordinates)
      case 'H' => (yPosition, xCoordinates + 1)
    }
    val currentSquare = matrix.field(yPosition)(xCoordinates)
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
