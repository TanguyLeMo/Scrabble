package de.htwg.se.scrabble.test
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._


class testingTUI extends AnyWordSpec {
val standardScrabbleFieldSize = 15
// place Tile Tanguy
"A Scrabble field is a datatype that contains a two-dimensional arrays of character. A Matrix" when {
  "empty" should {
    "be created by using a dimention and a sample cell" in {
      val scrabbleField = new ScrabbleField(standardScrabbleFieldSize, standardScrabbleFieldSize)
      scrabbleField.field.length should be (standardScrabbleFieldSize)
    }
 /*   "The function \"PlaceTile\" should be placing a character at given position with given character. A tile" when {
      "placed" should {
        val scrabbleField = new ScrabbleField(standardScrabbleFieldSize, standardScrabbleFieldSize)
        val character = 'B'
        scrabbleField.placeTile(3, 7, character) shouldEqual(character)

      } */
    }
  }
}

// addSpaces Tanguy

// getLetter Tanguy

//getMaxNumOfChar Tanguy

//getMaxNumOfChar Tanguy

//labelingXAxis Tanguy

  //goThroughColumn Hannes

  //goTrhoughRow Hannes

//toString Hannes/Tanguy
}
