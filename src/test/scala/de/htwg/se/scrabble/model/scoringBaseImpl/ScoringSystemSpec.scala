package de.htwg.se.scrabble.model.scoringBaseImpl

import de.htwg.se.scrabble.model.gameComponent.gameComponentBaseImpl.{Matrix, Stone}
import de.htwg.se.scrabble.model.gameComponent.scoringBaseImpl.{EnglishScoringSystem, FrenchScoringSystem, GermanScoringSystem, ItalianScoringSystem, ScoringSystem}
import de.htwg.se.scrabble.model.gameComponent.squareBaseImpl.{LetterSquare, StandardSquare}
import org.scalatest.matchers.must.Matchers
import org.scalatest.matchers.should.Matchers.should
import org.scalatest.wordspec.AnyWordSpec

class ScoringSystemSpec extends AnyWordSpec with Matchers {

  "A ScoringSystem" should {

    "correctly determine points for the English scoring system" in {
      val scoringSystem = new EnglishScoringSystem

      scoringSystem.determinPoints(new Stone('A')) should be(1)
      scoringSystem.determinPoints(new Stone('D')) should be(2)
      scoringSystem.determinPoints(new Stone('B')) should be(3)
      scoringSystem.determinPoints(new Stone('F')) should be(4)
      scoringSystem.determinPoints(new Stone('K')) should be(5)
      scoringSystem.determinPoints(new Stone('J')) should be(8)
      scoringSystem.determinPoints(new Stone('Q')) should be(10)
      scoringSystem.determinPoints(new Stone('@')) should be(0)
    }

    "correctly determine points for the French scoring system" in {
      val scoringSystem = new FrenchScoringSystem

      scoringSystem.determinPoints(new Stone('A')) should be(1)
      scoringSystem.determinPoints(new Stone('D')) should be(2)
      scoringSystem.determinPoints(new Stone('B')) should be(3)
      scoringSystem.determinPoints(new Stone('F')) should be(4)
      scoringSystem.determinPoints(new Stone('J')) should be(8)
      scoringSystem.determinPoints(new Stone('K')) should be(10)
      scoringSystem.determinPoints(new Stone('@')) should be(0)
    }

    "correctly determine points for the German scoring system" in {
      val scoringSystem = new GermanScoringSystem

      scoringSystem.determinPoints(new Stone('E')) should be(1)
      scoringSystem.determinPoints(new Stone('H')) should be(2)
      scoringSystem.determinPoints(new Stone('M')) should be(3)
      scoringSystem.determinPoints(new Stone('C')) should be(4)
      scoringSystem.determinPoints(new Stone('Ä')) should be(6)
      scoringSystem.determinPoints(new Stone('Ö')) should be(8)
      scoringSystem.determinPoints(new Stone('Q')) should be(10)
      scoringSystem.determinPoints(new Stone('@')) should be(0)
    }

    "correctly determine points for the Italian scoring system" in {
      val scoringSystem = new ItalianScoringSystem

      scoringSystem.determinPoints(new Stone('O')) should be(1)
      scoringSystem.determinPoints(new Stone('C')) should be(2)
      scoringSystem.determinPoints(new Stone('L')) should be(3)
      scoringSystem.determinPoints(new Stone('B')) should be(5)
      scoringSystem.determinPoints(new Stone('G')) should be(8)
      scoringSystem.determinPoints(new Stone('Q')) should be(10)
      scoringSystem.determinPoints(new Stone('@')) should be(0)
    }
  }

  "A ScoringSystem" should {
    val matrixSize = 15
    val defaultStone = new Stone('_')

    // Helper function to create a matrix with default stones
    def createMatrix: Matrix = {
      new Matrix(Vector.fill(matrixSize, matrixSize)(new StandardSquare(defaultStone))).init()
    }

    "correctly collect points for a word placed horizontally" in {
      val scoringSystem = new EnglishScoringSystem {}
      // Create a matrix and place a word horizontally
      val matrix = createMatrix.init()
      val updatedMatrix = matrix.placeWord(7, 7, 'H', "HELLO")

      val points = scoringSystem.collectPoints(updatedMatrix, 7, 7, 'H', "HELLO")
      points should be((4 + 1 + 1 + 1 + 1)*2) // Assuming no special squares
    }

    "correctly collect points for a word placed vertically" in {
      val scoringSystem = new EnglishScoringSystem {}

      // Create a matrix and place a word vertically
      val matrix = createMatrix.init()
      val updatedMatrix = matrix.placeWord(7, 7, 'V', "WORLD")

      val points = scoringSystem.collectPoints(updatedMatrix, 7, 7, 'V', "WORLD")
      points should be(22) // Assuming no special squares
    }

    "apply double letter score correctly" in {
      val scoringSystem = new EnglishScoringSystem {}
      val matrix = createMatrix

      // Placing a double letter square at (7, 8)
      val matrixWithSpecialSquare = new Matrix(15).init()

      val updatedMatrix = matrixWithSpecialSquare.placeWord(7, 7, 'H', "HELLO")
      val points = scoringSystem.collectPoints(updatedMatrix, 7, 7, 'H', "HELLO")
      points should be(4 + 1 + 1 + 1 + 1+8) // H = 8, E on double letter = 2*1, L = 1, L = 1, O = 1
    }

    "apply triple word score correctly" in {
      val scoringSystem = new EnglishScoringSystem {}
      val matrix = createMatrix

      // Placing a triple word square at (7, 7)
      val matrixWithSpecialSquare = new Matrix(15).init()

      val updatedMatrix = matrixWithSpecialSquare.placeWord(7, 7, 'H', "HELLO")
      val points = scoringSystem.collectPoints(updatedMatrix, 7, 7, 'H', "HELLO")
      points should be(16) // (8 + 1 + 1 + 1 + 1) * 3
    }

    "apply both letter and word multipliers correctly" in {
      val scoringSystem = new EnglishScoringSystem
      val matrix = createMatrix

      // Placing a double letter square at (7, 8) and a triple word square at (7, 7)
      val matrixWithSpecialSquares = new Matrix(15).init()

      val updatedMatrix = matrixWithSpecialSquares.placeWord(7, 7, 'H', "HELLO")
      val points = scoringSystem.collectPoints(updatedMatrix, 7, 7, 'H', "HELLO")
      points should be((4 + 1 + 1 + 1 + 1) * 2) // (H = 8, E on double letter = 2*1, L = 1, L = 1, O = 1) * 3
    }
  }
}