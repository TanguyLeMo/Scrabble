package de.htwg.se.scrabble.model

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._


class ScrabbleFieldSpec extends AnyWordSpec {

  val standardScrabbleFieldSize: Vector[Vector[Stone]] = Vector.fill(15)(Vector.fill(15)(Stone()))
  val scrabbleFieldSize1: Vector[Vector[Stone]] = Vector.fill(1)(Vector.fill(1)(Stone()))
  val scrabblefieldSize3: Vector[Vector[Stone]] = Vector.fill(3)(Vector.fill(3)(Stone()))
  val scrabblefieldSize4: Vector[Vector[Stone]] = Vector.fill(4)(Vector.fill(4)(Stone()))


  "A Scrabble field is a datatype that contains a two-dimensional arrays of character." when {
    "created" should {
      "The immutable value numSymbolPerColumn expresses the Number needed for each Column to represent a pleasant scale for the playing" +
        " Field concerning the X axis labeling. It" should {
        "represent the minimum number needed " in {
          val scrabbleField = new ScrabbleField(standardScrabbleFieldSize)
          scrabbleField.numSymbolPerColumn shouldEqual 2
        }
        "and apply for a bigger scale " in {
          val scrabbleField = new ScrabbleField(Vector.fill(43)(Vector.fill(43)(Stone())))
          scrabbleField.numSymbolPerColumn shouldEqual 3
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
      "translateLetter represents the Number of the X-Axis alphabetically. After Z it " should {
        "return the correct letter for single-digit positive integers" in {
          val scrabbleField = new ScrabbleField(standardScrabbleFieldSize)
          scrabbleField.translateLetter(1) shouldBe "A"
          scrabbleField.translateLetter(2) shouldBe "B"
          scrabbleField.translateLetter(3) shouldBe "C"
        }
        "return the correct letter for double-digit above 26 positive integers" in {
          val scrabbleField = new ScrabbleField(standardScrabbleFieldSize)
          scrabbleField.translateLetter(27) shouldBe "AA"
          scrabbleField.translateLetter(28) shouldBe "AB"
          scrabbleField.translateLetter(53) shouldBe "BA"
        }
        "return an empty string for non-positive integers" in {
          val scrabbleField = new ScrabbleField(standardScrabbleFieldSize)
          scrabbleField.translateLetter(0) shouldBe ""
          scrabbleField.translateLetter(-1) shouldBe ""
        }
      }


      "concatenateColumnsOfCurrentRow concatenates the columns of the current row index and" should {
        "create the columns of the playing field" in {
          val numRowCols = 1
          val field = new ScrabbleField(scrabbleFieldSize1)
          field.concatenateColumnsOfCurrentRow(0, 0) should be("_  ")
        }
        " and be scalable" in {
          val numRowsCols = 3
          val field = new ScrabbleField(scrabblefieldSize3)
          field.concatenateColumnsOfCurrentRow(0, 0) should be("_  " + "_  " + "_  ")
        }
      }
      "concatenateRows concatenates every row as a string thus " should {
        "create the playing field out of the columns and numbering the rows" in {
          val field = new ScrabbleField(scrabbleFieldSize1)
          field.concatenateRows(0) shouldBe ("0   " + "_  " + "\n")
        }
        "be scalable" in {
          val field = new ScrabbleField(scrabblefieldSize3)
          field.concatenateRows(0) shouldBe ("0   " + "_  " + "_  " + "_  " + "\n"
            + "1   " + "_  " + "_  " + "_  " + "\n"
            + "2   " + "_  " + "_  " + "_  " + "\n")
        }

        "labelingXAxis describes the X-Axis with it properties and scale of the Board. When implemented" +
          "Correctly it" should {
          "return a correct labels for each column for a standard Scrabble field" in {
            val scrabbleField = new ScrabbleField(standardScrabbleFieldSize)
            scrabbleField.labelingXAxis(0) shouldEqual "   A  B  C  D  E  F  G  H  I  J  K  L  M  N  O  "
          }
          "return a correct empty String if the current column is greater than the columns of the Field" in {
            val scrabbleField = new ScrabbleField(scrabblefieldSize3)
            scrabbleField.labelingXAxis(4) shouldEqual ""
          }

          "toString" should {
            "Put the labeled XAxis on top of the playing field" in {
              val numRowsCols = 3
              val field = new ScrabbleField(scrabblefieldSize3)
              field.toString should be("    A  B  C  " + "\n"
                + "0   _  _  _  " + "\n"
                + "1   _  _  _  " + "\n"
                + "2   _  _  _  " + "\n")
            }
          }
        }
      }
      "placeWord" should {
        "correctly place a word on the ScrabbleField" in {
          val scrabbleField = new ScrabbleField(15)
          val updatedField = scrabbleField.placeWord(2, 3, 'H', "HELLO")
          assert(scrabbleField != updatedField)
        }
        "translateCoordinate" should {
          "correctly translate a coordinate string to (Int, Int) tuple" in {
            val translator = new ScrabbleField(15)

            val coordinate1 = "A 1"
            val translatedCoordinate1 = translator.translateCoordinate(coordinate1)
            translatedCoordinate1 shouldEqual(0, 1)

            val coordinate2 = "E 5"
            val translatedCoordinate2 = translator.translateCoordinate(coordinate2)
            translatedCoordinate2 shouldEqual(4, 5)

            val coordinate3 = "H 10"
            val translatedCoordinate3 = translator.translateCoordinate(coordinate3)
            translatedCoordinate3 shouldEqual(7, 10)
          }
        }
      }
      "wordFits" should {
        "return true if the word fits in the specified position and direction" in {
          val field: ScrabbleField = new ScrabbleField(15)
          assert(field.wordFits(1, 2, 'H', "HEINRICH"))
        }
      }
    }
  }
}