package de.htwg.se.scrabble.test
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._


class testingTUI extends AnyWordSpec {

// place Tile Tanguy

// addSpaces Tanguy

// getLetter Tanguy

//getMaxNumOfChar Tanguy

//getMaxNumOfChar Tanguy

//labelingXAxis Tanguy
  "goThroughColumn" should {
    "creates the columns of the playing field" in {
      val numRowCols = 1
      val field = new ScrabbleField(numRowCols,numRowCols)
      field.goThroughColumn(0,0) should be("\u001B[33m" + "_   " + "\u001B[0m")
    }
    "be scaleable" in {
      val numRowsCols = 3
      val field = new ScrabbleField(numRowsCols,numRowsCols)
      field.goThroughColumn(0,0) should be("\u001B[31m" + "_   " + "\u001B[31m" + "_   " + "\u001B[31m" + "_   " + "\u001B[0m\u001B[0m\u001B[0m")
    }
  }

  "goThrougRow" should{
    "create the playingfield out of the columns and numbering the rows" in {
      val numRowCols = 1
      val field = new ScrabbleField(numRowCols, numRowCols)
      field.goThroughRow(0) should be("0   " + "\u001B[33m" + "_   " + "\u001B[0m" + "\n")
    }
    "be scaleable" in{
      val numRowsCols = 3
      val field = new ScrabbleField(numRowsCols, numRowsCols)
      field.goThroughRow(0) should be("0   " + "\u001B[31m" + "_   " + "\u001B[31m" + "_   " + "\u001B[31m" + "_   " + "\u001B[0m\u001B[0m\u001B[0m" +"\n"
                                    + "1   " + "\u001B[31m" + "_   " + "\u001B[33m" + "_   " + "\u001B[31m" + "_   " + "\u001B[0m\u001B[0m\u001B[0m" +"\n"
                                    + "2   "+ "\u001B[31m" + "_   " + "\u001B[31m" + "_   " + "\u001B[31m" + "_   " + "\u001B[0m\u001B[0m\u001B[0m" + "\n")
    }
  }


//toString Hannes/Tanguy
}
