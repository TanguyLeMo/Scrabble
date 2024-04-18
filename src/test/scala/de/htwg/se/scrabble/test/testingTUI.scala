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
      scrabbleField.field.length shouldEqual(standardScrabbleFieldSize)
    }
    "Every position of the Scrabble field" should {
      val scrabbleField = new ScrabbleField(standardScrabbleFieldSize, standardScrabbleFieldSize)
      
      
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
