package de.htwg.se.scrabble.test
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._


class testingTUI extends AnyWordSpec {
val standardScrabbleFieldSize = 15

// place Tile Tanguy
"A Scrabble field is a datatype that contains a two-dimensional arrays of character." when {
  "created" should {
    "be created by using the given dimension " in {
      val scrabbleField = new ScrabbleField(standardScrabbleFieldSize, standardScrabbleFieldSize)
      scrabbleField.field.length shouldEqual standardScrabbleFieldSize
    }
    "Every position of the Scrabble field " in {
        val scrabbleField = new ScrabbleField(standardScrabbleFieldSize, standardScrabbleFieldSize)
        scrabbleField.field(3)(3) shouldEqual 0
    }
    "scalable invariant number of spaces between Positions and Tiles to ensure prettier prints" should{
      "be settled " in { // addSpaces Tanguy
        val scrabbleField = new ScrabbleField(standardScrabbleFieldSize, standardScrabbleFieldSize)
        scrabbleField.addSpace(3) shouldEqual "    "
      }
    }
    "getLetter represents the Number of the X-Axis alphabetically. After Z it " should { // getLetter Tanguy
      "return the correct letter for single-digit positive integers" in {
        val scrabbleField = new ScrabbleField(standardScrabbleFieldSize, standardScrabbleFieldSize)
        scrabbleField.getLetter(1) shouldBe "A"
        scrabbleField.getLetter(2) shouldBe "B"
        scrabbleField.getLetter(3) shouldBe "C"
      }

      "return the correct letter for double-digit positive integers" in {
        val scrabbleField = new ScrabbleField(standardScrabbleFieldSize, standardScrabbleFieldSize)
        scrabbleField.getLetter(27) shouldBe "AA"
        scrabbleField.getLetter(28) shouldBe "AB"
        scrabbleField.getLetter(53) shouldBe "BA"
      }
      "return an empty string for non-positive integers" in {
        val scrabbleField = new ScrabbleField(standardScrabbleFieldSize, standardScrabbleFieldSize)
        scrabbleField.getLetter(0) shouldBe ""
        scrabbleField.getLetter(-1) shouldBe ""
      }
    }
    "labelingXAxis describes the X-Axis with it properties and scale of the Board. When implemented" +
      "Correctly it" should {
      "return a correct labels for each column for a standard Scrabble field" in {
      val scrabbleField = new ScrabbleField(standardScrabbleFieldSize, standardScrabbleFieldSize)
      scrabbleField.labelingXAxis(0) shouldEqual "    A   B   C   D   E   F   G   H   I   J   K   L   M   N   O   "
      }
      "return a correct empty String if the current column is greater than the columns of the Field" in{
        val scrabbleField = new ScrabbleField(3,3)
        scrabbleField.labelingXAxis(4) shouldEqual ""
      }
    }


  }
}

//labelingXAxis Tanguy

  //goThroughColumn Hannes

  //goThroughRow Hannes

//toString Hannes/Tanguy
}