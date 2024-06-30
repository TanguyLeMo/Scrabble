package de.htwg.se.scrabble.model.square

import de.htwg.se.scrabble.model.gameComponent.gameComponentBaseImpl.Stone
import de.htwg.se.scrabble.model.gameComponent.squareBaseImpl.{LetterSquare, LetterSquareFactory, StandardSquareFactory, WordSquare, WordSquareFactory}
import org.scalatest.matchers.must.Matchers
import org.scalatest.matchers.should.Matchers.should
import org.scalatest.wordspec.AnyWordSpec

class SquareFactory extends AnyWordSpec with Matchers{
  "A SquareFactory" when {
    val LetterSquareFacotry = new LetterSquareFactory()
    val WordSquareFactory = new WordSquareFactory()
    val standardSquareFactory = new StandardSquareFactory()

    "createDoubleSquare" should {
      "create a double letter square" in {
        LetterSquareFacotry.createDoubleSquare(Stone('A')).letter.symbol should be('A')
      }
      "create a tripple letter square" in {
        LetterSquareFacotry.createTripleSquare(Stone('A')).letter.symbol should be('A')
      }
      "create a double word square" in {
        WordSquareFactory.createDoubleSquare(Stone('A')).letter.symbol should be('A')
      }
      "create a tripple word square" in {
        WordSquareFactory.createTripleSquare(Stone('A')).letter.symbol should be('A')
      }
      "create a standard square" in {
        standardSquareFactory.createDoubleSquare(Stone('A')).letter.symbol should be('A')
      }
      "create a standard squares" in {
        standardSquareFactory.createTripleSquare(Stone('A')).letter.symbol should be('A')
      }

    }


  }

}
