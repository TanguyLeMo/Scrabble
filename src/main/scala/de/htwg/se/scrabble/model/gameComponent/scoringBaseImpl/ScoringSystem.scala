package de.htwg.se.scrabble.model.gameComponent
package scoringBaseImpl



import com.google.inject.Inject
import de.htwg.se.scrabble.model.gameComponent.gameComponentBaseImpl.Matrix
import de.htwg.se.scrabble.model.gameComponent.{ScoringSystemInterface, StoneInterface}
import de.htwg.se.scrabble.model.gameComponent.squareBaseImpl.{LetterSquare, WordSquare}

abstract class ScoringSystem @Inject extends ScoringSystemInterface{
  def collectPoints(matrix: MatrixInterface, xPosition: Int, yPosition: Int, direction: Char, word: String): Int = {

    // Collect points for the newly placed word
    print("X position:" + xPosition + " Y position:" + yPosition + " Direction:" + direction + " Word:" + word + "\n")
    val (newWordSum, newWordMultiplication) = collectPointsR(matrix, yPosition, xPosition, direction , word, 0, 0, 1)

    val totalPointsNewWord = newWordSum * newWordMultiplication
    totalPointsNewWord
  }
  def collectPointsR(matrix: MatrixInterface, yPosition: Int, xCoordinates: Int, direction: Char, word: String, currentIndex: Int, sum: Int, multiplication: Int): (Int, Int) = {
    if (currentIndex >= word.length || xCoordinates >= matrix.columns || yPosition >= matrix.rows || matrix.field(xCoordinates)(yPosition).isEmpty) {
    return (sum, multiplication)
  }
    print("X position:" + xCoordinates + " Y position:" + yPosition + " Direction:" + direction + " Word:" + word + "\n")

    val (newXPosition, newYPosition) = direction match {
    case 'V' => (xCoordinates, yPosition + 1)
    case 'H' => (xCoordinates + 1 ,yPosition)
  }
  val currentSquare = matrix.field(xCoordinates)(yPosition)
  val (newSum, newMultiplication) = currentSquare match {
    case wordSquare: WordSquare =>
      (sum + determinPoints(currentSquare.letter) , multiplication * currentSquare.scoreModifier)
    case letterSquare: LetterSquare =>
      (sum + (determinPoints(currentSquare.letter) * currentSquare.scoreModifier), multiplication)
    case _ =>
      (sum + determinPoints(currentSquare.letter) , multiplication)
  }
    collectPointsR(matrix, newYPosition, newXPosition, direction, word, currentIndex + 1, newSum, newMultiplication)
}
  def determinPoints(stone: StoneInterface): Int
}
