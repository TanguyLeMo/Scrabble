package de.htwg.se.scrabble.model

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers.*


class MatrixSpec extends AnyWordSpec {
  val standardSizedMatrix = new Matrix(Vector.fill(15)(Vector.fill(15)(Stone('_'))))
  val matrix3x3 = new Matrix(Vector.fill(3)(Vector.fill(3)(Stone('_'))))
  val matrix5x5 = new Matrix(Vector.fill(5)(Vector.fill(5)(Stone('_'))))

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
  "placing a word vertically" should {
    "correctly update the field" in {

      val updatedField = matrix5x5.placeWord(1, 0, 'V', "hallo")
      val expectedField = new Matrix(Vector(
        Vector(new Stone('_'), new Stone('h'), new Stone('_'), new Stone('_'), new Stone('_')),
        Vector(new Stone('_'), new Stone('a'), new Stone('_'), new Stone('_'), new Stone('_')),
        Vector(new Stone('_'), new Stone('l'), new Stone('_'), new Stone('_'), new Stone('_')),
        Vector(new Stone('_'), new Stone('l'), new Stone('_'), new Stone('_'), new Stone('_')),
        Vector(new Stone('_'), new Stone('o'), new Stone('_'), new Stone('_'), new Stone('_')),
      ))
      updatedField shouldEqual expectedField
    }
  }
  "placing a word horizontally" should {
    "correctly update the field" in {
      val updatedField = matrix5x5.placeWord(0, 1, 'H', "HATER")
      val expectedField = new Matrix(Vector(
        Vector(new Stone('_'), new Stone('_'), new Stone('_'), new Stone('_'), new Stone('_')),
        Vector(new Stone('H'), new Stone('A'), new Stone('T'), new Stone('E'), new Stone('R')),
        Vector(new Stone('_'), new Stone('_'), new Stone('_'), new Stone('_'), new Stone('_')),
        Vector(new Stone('_'), new Stone('_'), new Stone('_'), new Stone('_'), new Stone('_')),
        Vector(new Stone('_'), new Stone('_'), new Stone('_'), new Stone('_'), new Stone('_'))
      ))
      updatedField shouldBe expectedField
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
      assert(field.wordFits(0, 0, 'V', "CAT") && field.getStone(1,0).symbol === 'A')
    }
    "and return false if the direction is at random" in {
      val field = matrix5x5.placeWord(1, 0, 'V', "AT")
      assert(!field.wordFits(1, 1, 'T', "CAT"))
    }

    "return false if the word does not fit within bounds" in {
      val field = standardSizedMatrix
      assert(!field.wordFits(13, 13, 'V', "ELEPHANT"))
    }
    "return false if the word overlaps with existing tiles" in {
      val field = new Matrix(Vector(
        Vector(new Stone('_'), new Stone('_'), new Stone('_')),
        Vector(new Stone('_'), new Stone('_'), new Stone('A')),
        Vector(new Stone('_'), new Stone('_'), new Stone('T')),
      ))
      assert(field.wordFits(0, 0, 'V', "CAT"))
    }
    "The auxiliary Constructor " should {
      "also work just by inserting a Primitive Integer which represents the Columns and Rows " in {
        val field = new Matrix(Vector.fill(15)(Vector.fill(15)(Stone('_'))))
        field shouldEqual standardSizedMatrix
      }
    }
    "The values rows and columns " should {
      "represent correct number of columns and rows " in {
        val field = new Matrix(5)
        assert(field.rows == 5)
      }
      "with the case of a 0x0 Matrix " in {
        val field = new Matrix(0)
        assert(field.rows == 0)
      }
    }
    "The equals method " should {
      "automatically return false, when comparing" in {
        assert(!standardSizedMatrix.equals(Stone()))
      }
    }

  }
}