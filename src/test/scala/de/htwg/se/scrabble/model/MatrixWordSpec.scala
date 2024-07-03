package de.htwg.se.scrabble.model

import de.htwg.se.scrabble.model.gameComponent.*
import de.htwg.se.scrabble.model.gameComponent.gameComponentBaseImpl.{Matrix, Stone}
import de.htwg.se.scrabble.model.gameComponent.squareBaseImpl.StandardSquareFactory
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class MatrixWordSpec extends AnyWordSpec with Matchers {

  "A Matrix" should {

    "initialize with correct parameters" in {
      // Arrange
      val rowsAndColumns = 15

      // Act
      val matrix = new Matrix(rowsAndColumns).init()

      // Assert
      matrix.columns should be(rowsAndColumns)
      matrix.rows should be(rowsAndColumns)
      matrix.field.length should be(rowsAndColumns)
      matrix.fitsInBounds(0, 0, 'k', "HELLO") should be(false)
      matrix.field.forall(_.length == rowsAndColumns) should be(true)
    }

    "update the board correctly" in {
      // Arrange
      val rowsAndColumns = 15
      val matrix = new Matrix(rowsAndColumns)
      val positions = List((0, 0), (1, 1))
      val factory = () => new StandardSquareFactory().createDoubleSquare(new Stone('A'))

      // Act
      val updatedBoard = matrix.updateBoard(matrix.field, positions, factory)

      // Assert
      updatedBoard(0)(0).letter.symbol should be('A')
      updatedBoard(1)(1).letter.symbol should be('A')
    }

    "initialize the standard board correctly" in {
      // Arrange
      val rowsAndColumns = 15
      val matrix = new Matrix(rowsAndColumns)

      // Act
      val initializedBoard = matrix.initializeStandardBoard

      // Assert
      initializedBoard.length should be(rowsAndColumns)
      initializedBoard.forall(_.length == rowsAndColumns) should be(true)
    }

    "place a word correctly" in {
      // Arrange
      val rowsAndColumns = 15
      val matrix = new Matrix(rowsAndColumns)
      val word = "HELLO"
      val xPosition = 0
      val yPosition = 0
      val direction = 'H'

      // Act
      val newMatrix = matrix.placeWord(xPosition, yPosition, direction, word)

      // Assert
      newMatrix.getSquare(xPosition, yPosition).letter.symbol should be('H')
      newMatrix.getSquare(xPosition, yPosition+1).letter.symbol should be('E')
      newMatrix.getSquare(xPosition, yPosition + 2).letter.symbol should be('L')
      newMatrix.getSquare(xPosition, yPosition + 3).letter.symbol should be('L')
      newMatrix.getSquare(xPosition , yPosition+ 4).letter.symbol should be('O')
    }

    "remove a word correctly" in {
      // Arrange
      val rowsAndColumns = 15
      val matrix = new Matrix(rowsAndColumns)
      val word = "HELLO"
      val xPosition = 0
      val yPosition = 0
      val direction = 'H'
      val newMatrix = matrix.placeWord(xPosition, yPosition, direction, word)

      // Act
      val removedMatrix = newMatrix.removeWord(xPosition, yPosition, direction, word)

      // Assert

      removedMatrix.getSquare(xPosition + 4, yPosition).letter.symbol should not equal 'O'
    }
    "The equality of two matrices" in {
      // Arrange
      val rowsAndColumns = 15
      val matrix = new Matrix(rowsAndColumns)
      val word = "HELLO"
      val xPosition = 0
      val yPosition = 0
      val direction = 'H'
      val newMatrix = matrix.placeWord(xPosition, yPosition, direction, word)

      // Act
      val removedMatrix = newMatrix.removeWord(xPosition, yPosition, direction, word)

      // Assert
      removedMatrix should not be matrix
    }

    "check if a word fits correctly" in {
      // Arrange
      val rowsAndColumns = 15
      val matrix = new Matrix(rowsAndColumns)
      val word = "HELLO"
      val xPosition = 0
      val yPosition = 0
      val direction = 'H'

      // Act & Assert
      matrix.wordFits(xPosition, yPosition, direction, word) should be(true)
      matrix.wordFits(xPosition, yPosition, 'V', word) should be(true)
      matrix.wordFits(rowsAndColumns - 1, rowsAndColumns - 1, direction, word) should be(false)
    }

    "check if a word fits in bounds" in {
      // Arrange
      val rowsAndColumns = 15
      val matrix = new Matrix(rowsAndColumns)
      val word = "HELLO"

      // Act & Assert
      matrix.fitsInBounds(0, 0, 'H', word) should be(true)
      matrix.fitsInBounds(0, 0, 'V', word) should be(true)
      matrix.fitsInBounds(rowsAndColumns - 1, rowsAndColumns - 1, 'H', word) should be(false)
      matrix.fitsInBounds(rowsAndColumns - 1, rowsAndColumns - 1, 'V', word) should be(false)
    }
    "WordFits should return false if the word does not fit" in {
      // Arrange
      val rowsAndColumns = 15
      val matrix = new Matrix(rowsAndColumns)
      val word = "HELLO"
      val xPosition = 15
      val yPosition = 15
      val direction = 'H'

      // Act
      val wordfits = matrix.wordFits(xPosition, yPosition, direction, word)

      // Assert
      wordfits should be(false)
    }


    "check for letters already there" in {
      // Arrange
      val rowsAndColumns = 15
      val matrix = new Matrix(rowsAndColumns)
      val word = "HELLO"
      val xPosition = 0
      val yPosition = 0
      val direction = 'H'
      val newMatrix = matrix.placeWord(xPosition, yPosition, direction, word)
      
      // Act
      val lettersThere = newMatrix.lettersAlreadyThere(xPosition, yPosition, direction, word)

      // Assert
      lettersThere.map(_.symbol) should contain theSameElementsAs word.toList
    }
    "initialize A non Standard Board " in {
      // Arrange
      val rowsAndColumns = 17
      val matrix = new Matrix(rowsAndColumns).init()

      // Act
      val initializedBoard = matrix.field

      // Assert
      initializedBoard.length should be(rowsAndColumns)
      initializedBoard.forall(_.length == rowsAndColumns) should be(true)
    }
    "not do anything in place word if the word doesnt fit" in {
      // Arrange
      val rowsAndColumns = 15
      val matrix = new Matrix(rowsAndColumns)
      val word = "HELLO"
      val xPosition = 15
      val yPosition = 15
      val direction = 'H'

      // Act
      val wordfits = matrix.wordFits(xPosition, yPosition, direction, word)

      // Assert
      wordfits should be(false)
    }
    "not place a word if it is out of bounds" in {
      // Arrange
      val rowsAndColumns = 15
      val matrix = new Matrix(rowsAndColumns)
      val word = "HELLO"
      val xPosition = 15
      val yPosition = 15
      val direction = 'H'

      // Act
      val newMatrix = matrix.placeWord(xPosition, yPosition, direction, word)

      // Assert
      newMatrix should be(matrix)
    }
  }

}
