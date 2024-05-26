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

  def collectConnectedPoints(matrix: Matrix, xPosition: Int, yPosition: Int, direction: Char, isPredecessor: Boolean): Int = {
    def collectPointsRecursively(x: Int, y: Int): Int = {
      val (nextX, nextY) = if (isPredecessor) determinePredecessorCoordinates(matrix, x, y, direction) else determineSuccessorCoordinates(matrix, x, y, direction)
      if (nextX == x && nextY == y) {
        0 // No further predecessors/successors
      } else {
        val word = collectFullWord(matrix, nextX, nextY, direction)
        val (sum, multiplication) = collectPointsR(matrix, nextX, nextY, direction, word, 0, 0, 1)
        sum * multiplication + collectPointsRecursively(nextX, nextY)
      }
    }
    collectPointsRecursively(xPosition, yPosition)
  }

  def hasPredecessor(matrix: Matrix, xPosition: Int, yPosition: Int, direction: Char): Boolean = {
    val (x, y) = determinePredecessorCoordinates(matrix, xPosition, yPosition, direction)
    x != xPosition || y != yPosition
  }

  def hasSuccessor(matrix: Matrix, xPosition: Int, yPosition: Int, direction: Char): Boolean = {
    val (x, y) = determineSuccessorCoordinates(matrix, xPosition, yPosition, direction)
    x != xPosition || y != yPosition
  }

  def collectPointsR(matrix: Matrix, yPosition: Int, xPosition: Int, direction: Char, word: String, currentIndex: Int, sum: Int, multiplication: Int): (Int, Int) = {
    if (currentIndex >= word.length || xPosition >= matrix.columns || yPosition >= matrix.rows || matrix.field(yPosition)(xPosition).isEmpty) {
      return (sum, multiplication)
    }
    val (newXPosition, newYPosition) = direction match {
      case 'H' => (yPosition + 1, xPosition)
      case 'V' => (yPosition, xPosition + 1)
      case _ => (yPosition, xPosition)
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

  def determinePredecessorCoordinates(matrix: Matrix, xPosition: Int, yPosition: Int, direction: Char): (Int, Int) = {
    val (x, y) = direction match {
      case 'H' => (xPosition - 1, yPosition)
      case 'V' => (xPosition, yPosition - 1)
      case _ => (xPosition, yPosition)
    }

    if (x < 0 || y < 0 || matrix.field(y)(x).isEmpty) {
      return (xPosition, yPosition)
    }

    val currentSquare = matrix.field(y)(x)
    currentSquare match {
      case _: WordSquare => determinePredecessorCoordinates(matrix, x, y, direction)
      case _: LetterSquare => (x, y)
      case _ => determinePredecessorCoordinates(matrix, x, y, direction)
    }
  }

  def determineSuccessorCoordinates(matrix: Matrix, xPosition: Int, yPosition: Int, direction: Char): (Int, Int) = {
    val (x, y) = direction match {
      case 'H' => (xPosition + 1, yPosition)
      case 'V' => (xPosition, yPosition + 1)
      case _ => (xPosition, yPosition)
    }

    if (x >= matrix.columns || y >= matrix.rows || matrix.field(y)(x).isEmpty) {
      return (xPosition, yPosition)
    }

    val currentSquare = matrix.field(y)(x)
    currentSquare match {
      case _: WordSquare => determineSuccessorCoordinates(matrix, x, y, direction)
      case _: LetterSquare => (x, y)
      case _ => determineSuccessorCoordinates(matrix, x, y, direction)
    }
  }

  def collectFullWord(matrix: Matrix, xPosition: Int, yPosition: Int, direction: Char): String = {
    val wordBuilder = new StringBuilder

    var (curX, curY) = (xPosition, yPosition)

    while (curX >= 0 && curY >= 0 && curX < matrix.columns && curY < matrix.rows && !matrix.field(curY)(curX).isEmpty) {
      val square = matrix.field(curY)(curX)
      square match {
        case letterSquare: LetterSquare => wordBuilder.append(letterSquare.letter)
        case wordSquare: WordSquare => wordBuilder.append(wordSquare.letter)
        case _ => // Do nothing
      }
      direction match {
        case 'H' => curX += 1
        case 'V' => curY += 1
        case _ => // Do nothing
      }
    }

    wordBuilder.toString()
  }

  def determinPoints(stone: Stone): Int
}
