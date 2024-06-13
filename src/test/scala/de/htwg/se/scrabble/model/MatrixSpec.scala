package de.htwg.se.scrabble.model

import de.htwg.se.scrabble.model.gameComponent.squareBaseImpl.squareBaseImpl.StandardSquareFactory
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers.*


class MatrixSpec extends AnyWordSpec {
  val standardSquareFractory = new StandardSquareFactory()
  val standardSizedMatrix: Matrix = new Matrix(Vector.fill(15)(Vector.fill(15)(standardSquareFractory.createDoubleSquare(Stone('_'))))).init()
  val matrix3x3: Matrix = new Matrix(Vector.fill(3)(Vector.fill(3)(standardSquareFractory.createDoubleSquare(Stone('_'))))).init()
  val matrix5x5: Matrix = new Matrix(Vector.fill(5)(Vector.fill(5)(standardSquareFractory.createDoubleSquare(Stone('_'))))).init()
  
  
  "fitsInBounds" when {
    "given valid position and direction" should {
      "return true if the word fits within the bounds horizontally" in {
        assert(matrix5x5.fitsInBounds(0, 0, 'H', "HELLO") === true)
      }
      "return true if the word fits within the bounds vertically" in {
        assert(matrix5x5.fitsInBounds(0, 0, 'V', "HELLO") === true)
      }
    }
    "given invalid position or direction" should {
      "return false if the word doesn't fit within the bounds horizontally" in {
        assert(matrix5x5.fitsInBounds(3, 3, 'H', "HELLO") === false)
      }

      "return false if the word doesn't fit within the bounds vertically" in {
        assert(matrix3x3.fitsInBounds(3, 3, 'V', "HELLO") === false)
      }

      "return false if the direction is invalid" in {
        assert(matrix5x5.fitsInBounds(0, 0, 'D', "HELLO") === false)
      }

      "return false if both position and direction are invalid" in {
        assert(matrix5x5.fitsInBounds(6, 6, 'D', "HELLO") === false)
      }
    }
  }


  "placing a word with a random direction " should {
    "do nothing " in {
      val falseFilledField = standardSizedMatrix.placeWord(0, 0, 'T', "Cat")
      assert(standardSizedMatrix === falseFilledField)
    }
  }


  "placing a word that doesn't fit" should {
    "not change the field" in {
      val field = matrix5x5
      val originalField = field
      val updatedField = field.placeWord(4, 4, 'H', "hello")
      updatedField shouldBe originalField
    }
  }

  "checking if a word fits vertically" should {
    "return true if the word fits within bounds and does not overlap with existing tiles" in {
      val field = standardSizedMatrix
      assert(field.wordFits(3, 3, 'V', "DOG"))
    }
    "return true if the letter and current element is the same" in{
      val field = matrix5x5.placeWord(1,0, 'H', "AT")
      assert(field.wordFits(0,0, 'H', "CAT"))
    }
    "same conditions goes for vertical" in {
      val field = standardSizedMatrix.placeWord(1, 0, 'V', "AT")
      assert(field.wordFits(0, 0, 'V', "CAT") && field.getSquare(1,0).letter.symbol === 'A')
    }
    "and return false if the direction is at random" in {
      val field = matrix5x5.placeWord(1, 0, 'V', "AT")
      assert(!field.wordFits(1, 1, 'T', "CAT"))
    }

    "return false if the word does not fit within bounds" in {
      val field = standardSizedMatrix
      assert(!field.wordFits(13, 13, 'V', "ELEPHANT"))
    }

    }
    "removeWord" should {
      "remove the word from the field" in {
        val field = standardSizedMatrix.placeWord(0, 0, 'H', "CAT")
        val updatedField = field.removeWord(0, 0, 'H', "CAT")
        updatedField.getSquare(0, 0).letter.symbol should not be '_'
      }
    }
    "removeVertically" should {
      "remove a vertical word from the field" in {
        val field = standardSizedMatrix.placeWord(0, 0, 'V', "CAT")
        val updatedField = field.removeHorizontally(0, 0, "CAT",0,field)
        updatedField.getSquare(0, 0).letter.symbol should not be '_'      
      }
    }
    "removeHorizontally" should {
      "remove a horizontal word from the field" in {
        val field = standardSizedMatrix.placeWord(0, 0, 'H', "CAT")
        val updatedField = field.removeVertically(0, 0, "CAT",0,field)
        updatedField.getSquare(0, 0).letter.symbol should not be '_'
      }
    }
    "The equals method " should {
      "automatically return false, when comparing" in {
        assert(!standardSizedMatrix.equals(Stone()))
      }
    }
    "adding a word to the dictionary" should {
      "have the word in the dictionary" in {
        val field = new ScrabbleField(15)
        val updatedField = field.addDictionaryWord("testword")
        updatedField.dictionary.set should contain("testword".toUpperCase())
      }
    }

  
}