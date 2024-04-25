package de.htwg.se.scrabble

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._

//noinspection ScalaUnusedExpression
class TestingScrabbleField extends AnyWordSpec {
  val standardScrabbleFieldSize: Vector[Vector[Char]] = Vector.fill(15)(Vector.fill(15)('_'))
  val scrabbleFieldSize1: Vector[Vector[Char]] = Vector.fill(1)(Vector.fill(1)('_'))
  val scrabblefieldSize3: Vector[Vector[Char]] = Vector.fill(3)(Vector.fill(3)('_'))
  val scrabblefieldSize4: Vector[Vector[Char]] = Vector.fill(4)(Vector.fill(4)('_'))

  "A ScrabbleField" when {
    "translating coordinates" should {
      "return the correct (x, y) coordinates" in {
        val scrabbleField = new ScrabbleField(Vector(
          Vector('_', '_', '_'),
          Vector('_', '_', '_'),
          Vector('_', '_', '_')
        ))

        val (x, y) = scrabbleField.translateCoordinate("A 1")
        assert(x == 0 && y == 1)

        val (x2, y2) = scrabbleField.translateCoordinate("B 3")
        assert(x2 == 1 && y2 == 3)

        val (x3, y3) = scrabbleField.translateCoordinate("D 2")
        assert(x3 == 3 && y3 == 2)
      }
    }
  }

  "fitsInBounds" when {
    "given valid position and direction" should {
      "return true if the word fits within the bounds horizontally" in {
        val field = new ScrabbleField(Vector.fill(5)(Vector.fill(5)('_')))
        assert(field.fitsInBounds(0, 0, 'H', "HELLO") === true)
      }

      "return true if the word fits within the bounds vertically" in {
        val field = new ScrabbleField(Vector.fill(5)(Vector.fill(5)('_')))
        assert(field.fitsInBounds(0, 0, 'V', "HELLO") === true)
      }
    }

    "given invalid position or direction" should {
      "return false if the word doesn't fit within the bounds horizontally" in {
        val field = new ScrabbleField(Vector.fill(5)(Vector.fill(5)('_')))
        assert(field.fitsInBounds(3, 3, 'H', "HELLO") === false)
      }

      "return false if the word doesn't fit within the bounds vertically" in {
        val field = new ScrabbleField(Vector.fill(5)(Vector.fill(5)('_')))
        assert(field.fitsInBounds(3, 3, 'V', "HELLO") === false)
      }

      "return false if the direction is invalid" in {
        val field = new ScrabbleField(Vector.fill(5)(Vector.fill(5)('_')))
        assert(field.fitsInBounds(0, 0, 'D', "HELLO") === false)
      }

      "return false if both position and direction are invalid" in {
        val field = new ScrabbleField(Vector.fill(5)(Vector.fill(5)('_')))
        assert(field.fitsInBounds(6, 6, 'D', "HELLO") === false)
      }
    }
  }
  
  
  
  
  "placing a word vertically" should {
    "correctly update the field" in {
      val field = new ScrabbleField(Vector.fill(5)(Vector.fill(5)('_')))
      val updatedField = field.placeWord(1, 0, 'V', "hallo")
      val expectedField = new ScrabbleField(Vector(
        Vector('_', 'h', '_', '_', '_'),
        Vector('_', 'a', '_', '_', '_'),
        Vector('_', 'l', '_', '_', '_'),
        Vector('_', 'l', '_', '_', '_'),
        Vector('_', 'o', '_', '_', '_')
      ))

      updatedField.playfield shouldBe expectedField.playfield
    }
  }

  "placing a word horizontally" should {
    "correctly update the field" in {
      val field = new ScrabbleField(Vector.fill(5)(Vector.fill(5)('_')))
      val updatedField = field.placeWord(0, 1, 'H', "hater")
      val expectedField = new ScrabbleField(Vector(
        Vector('_', '_', '_', '_', '_'),
        Vector('h', 'a', 't', 'e', 'r'),
        Vector('_', '_', '_', '_', '_'),
        Vector('_', '_', '_', '_', '_'),
        Vector('_', '_', '_', '_', '_')
      ))

      updatedField.playfield shouldBe expectedField.playfield
    }
  }
  "placing a word with a random direction " should{
    "do nothing " in {
      val field = new ScrabbleField(standardScrabbleFieldSize)
      val falsyFilldField = field.placeWord( 0, 0, 'T', "Cat")
      assert(field === falsyFilldField)
    }
  }


  "placing a word that doesn't fit" should {
    "not change the field" in {
      val field = new ScrabbleField(Vector.fill(5)(Vector.fill(5)('_')))
      val originalField = field.playfield
      val updatedField = field.placeWord(4, 4, 'H', "hello")
      updatedField.playfield shouldBe originalField
    }
  }
  "checking if a word fits vertically" should {
    "return true if the word fits within bounds and does not overlap with existing tiles" in {
      val field = new ScrabbleField(Vector.fill(15)(Vector.fill(15)('_')))
      assert(field.wordFits(3, 3, 'V', "DOG"))
    }
    "return true if the letter and current element is the same" in{
      val field = new ScrabbleField(standardScrabbleFieldSize).placeWord(1,0, 'H', "AT")
      assert(field.wordFits(0,0, 'H', "CAT"))
    }
    "same conditions goes for vertical" in {
      val field = new ScrabbleField(standardScrabbleFieldSize).placeWord(1, 0, 'V', "AT")
      assert(field.wordFits(0, 0, 'V', "CAT"))
    }
    "and return false if the direction is at random" in {
      val field = new ScrabbleField(standardScrabbleFieldSize).placeWord(1, 0, 'V', "AT")
      assert(!field.wordFits(1, 1, 'T', "CAT"))
    }

    "return false if the word does not fit within bounds" in {
      val field = new ScrabbleField(Vector.fill(15)(Vector.fill(15)('_')))
      assert(!field.wordFits(13, 13, 'V', "ELEPHANT"))
    }
    "return false if the word overlaps with existing tiles" in {
      val field = new ScrabbleField(Vector(
        Vector('_', '_', '_'),
        Vector('_', '_', 'A'),
        Vector('_', '_', 'T')
      ))
      assert(field.wordFits(0, 0, 'V', "CAT"))
    }
  }
  "checking if a word fits horizontally" should {
    "return true if the word fits within bounds and does not overlap with existing tiles" in {
      val field = new ScrabbleField(Vector.fill(15)(Vector.fill(15)('_')))
      assert(field.wordFits(3, 3, 'H', "DOG"))
    }
    "return false if the word does not fit within bounds" in {
      val field = new ScrabbleField(Vector.fill(15)(Vector.fill(15)('_')))
      assert(!field.wordFits(13, 13, 'H', "ELEPHANT"))
    }
    "return false if the word overlaps with existing tiles" in {
      val field = new ScrabbleField(Vector(
        Vector('_', '_', '_'),
        Vector('_', '_', 'A'),
        Vector('_', '_', 'T')
      ))
      assert(field.wordFits(0, 0, 'H', "CAT"))
    }
  }
  "checking if a word fits in an invalid direction" should {
    "return false" in {
      val field = new ScrabbleField(Vector.fill(15)(Vector.fill(15)('_')))
      assert(!field.wordFits(3, 3, 'X', "DOG"))
    }
  }

  "A Scrabble field is a datatype that contains a two-dimensional arrays of character." when {
    "created" should {
      "be created by using the given dimension " in {
        val scrabbleField = new ScrabbleField(standardScrabbleFieldSize)
        scrabbleField.columns shouldEqual standardScrabbleFieldSize.length
      }
      "Every position of the Scrabble field " in {
        val scrabbleField = new ScrabbleField(standardScrabbleFieldSize)
        scrabbleField.playfield(3)(3) shouldBe '_'
      }
      "The immutable value numSymbolPerColumn expresses the Number needed for each Column to represent a pleasant scale for the playing" +
        " Field concerning the X axis labeling. It" should{
        "represent the minimum number needed " in{
          val scrabbleField = new ScrabbleField(standardScrabbleFieldSize)
          scrabbleField.numSymbolPerColumn shouldEqual 3
        }
        "and apply for a bigger scale " in {
          val scrabbleField = new ScrabbleField(Vector.fill(43)(Vector.fill(43)('_')))
          scrabbleField.numSymbolPerColumn shouldEqual 4
        }
      }


      "placeTile" should {
        "place a Character in the given position of the field Matrix and return nothing " in {
          val scrabbleField = new ScrabbleField(standardScrabbleFieldSize)
          val newField = scrabbleField.placeTile(4, 4, 'A')

          assert(newField.playfield(4)(4) == 'A')
        } 
        "not do Anything if the placeTile function arguments are out of Bonds of the matrix" in {
          val scrabbleField = new ScrabbleField(scrabblefieldSize4)
          val compareField = scrabbleField.playfield
          val newField = scrabbleField.placeTile(-1, -1, 'Z')
          newField.playfield shouldEqual compareField
        }

      }
      "add Space's task is to represent scalable invariant number of spaces between Positions and Tiles to ensure" +
        " prettier prints it" should {
        "be settled " in {
          val scrabbleField = new ScrabbleField(standardScrabbleFieldSize)
          scrabbleField.addSpace(3) shouldEqual "    "
        }
        "if the input argument is 0 or or lower, one space character will be returned" in {
          val scrabbleField = new ScrabbleField(standardScrabbleFieldSize)
          scrabbleField.addSpace(0) shouldEqual " "
        }
      }


      "getLetter represents the Number of the X-Axis alphabetically. After Z it " should {
        "return the correct letter for single-digit positive integers" in {
          val scrabbleField = new ScrabbleField(standardScrabbleFieldSize)
          scrabbleField.getLetter(1) shouldBe "A"
          scrabbleField.getLetter(2) shouldBe "B"
          scrabbleField.getLetter(3) shouldBe "C"
        }
        "return the correct letter for double-digit above 26 positive integers" in {
          val scrabbleField = new ScrabbleField(standardScrabbleFieldSize)
          scrabbleField.getLetter(27) shouldBe "AA"
          scrabbleField.getLetter(28) shouldBe "AB"
          scrabbleField.getLetter(53) shouldBe "BA"
        }
        "return an empty string for non-positive integers" in {
          val scrabbleField = new ScrabbleField(standardScrabbleFieldSize)
          scrabbleField.getLetter(0) shouldBe ""
          scrabbleField.getLetter(-1) shouldBe ""
        }
      }


      "concatenateColumnsOfCurrentRow concatenates the columns of the current row index and" should {
        "create the columns of the playing field" in {
          val numRowCols = 1
          val field = new ScrabbleField(scrabbleFieldSize1)
          field.concatenateColumnsOfCurrentRow(0, 0) should be("_   ")
        }
        " and be scalable" in {
          val numRowsCols = 3
          val field = new ScrabbleField(scrabblefieldSize3)
          field.concatenateColumnsOfCurrentRow(0, 0) should be("_   " + "_   " + "_   ")
        }
      }


      "concatenateCurrentRow concatenates every row as a string thus " should {
        "create the playing field out of the columns and numbering the rows" in {
          val numRowCols = 1
          val field = new ScrabbleField(scrabbleFieldSize1)
          field.concatenateCurrentRow(0) should be("0   " + "_   " + "\n")
        }
        "be scalable" in {
          val field = new ScrabbleField(scrabblefieldSize3)
          field.concatenateCurrentRow(0) should be("0   " + "_   " + "_   " + "_   " + "\n"
            + "1   " + "_   " + "_   " + "_   " + "\n"
            + "2   " + "_   " + "_   " + "_   " + "\n")
        }
        "in case that the Matrix has a placed a Character within a position, given specimen" should {
          "be appear in given String" in {
            val scrabbleField = new ScrabbleField(scrabblefieldSize3)
            val newField = scrabbleField.placeTile(2, 2, 'B')
            newField.concatenateCurrentRow(0) should be("0   " + "_   " + "_   " + "_   " + "\n"
              + "1   " + "_   " + "_   " + "_   " + "\n"
              + "2   " + "_   " + "_   " + "B   " + "\n")
          }
        }
      }

      "labelingXAxis describes the X-Axis with it properties and scale of the Board. When implemented" +
        "Correctly it" should {
        "return a correct labels for each column for a standard Scrabble field" in {
          val scrabbleField = new ScrabbleField(standardScrabbleFieldSize)
          scrabbleField.labelingXAxis(0) shouldEqual "    A   B   C   D   E   F   G   H   I   J   K   L   M   N   O   "
        }
        "return a correct empty String if the current column is greater than the columns of the Field" in {
          val scrabbleField = new ScrabbleField(scrabblefieldSize3)
          scrabbleField.labelingXAxis(4) shouldEqual ""
        }

        "toString" should {
          "Put the labeled XAxis on top of the playing field" in {
            val numRowsCols = 3
            val field = new ScrabbleField(scrabblefieldSize3)
            field.toString should be("    A   B   C   " + "\n"
              + "0   _   _   _   " + "\n"
              + "1   _   _   _   " + "\n"
              + "2   _   _   _   " + "\n")
          }
        }
      }
    }
  }
}
