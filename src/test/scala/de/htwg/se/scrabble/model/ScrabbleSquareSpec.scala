package de.htwg.se.scrabble.model

import de.htwg.se.scrabble.model.Stone
import de.htwg.se.scrabble.model.square.*
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class ScrabbleSquareSpec extends AnyWordSpec with Matchers {
  "A ScrabbleSquare" when {
    "new" should {
      val stone = Stone('A') // Use Stone('A') instead of new Stone('A')
      val standardSquare = new StandardSquareFactory().createDoubleSquare(stone)
      val tripleSquare = new StandardSquareFactory().createTripleSquare(stone)
      val doubleWordSquare = new WordSquareFactory().createDoubleSquare(stone)
      val tripleWordSquare = new WordSquareFactory().createTripleSquare(stone)
      val doubleLetterSquare = new LetterSquareFactory().createDoubleSquare(stone)
      val tripleLetterSquare = new LetterSquareFactory().createTripleSquare(stone)

      "have a scoreModifier for standard squares" in {
        standardSquare.scoreModifier shouldEqual 1
        tripleSquare.scoreModifier shouldEqual 1
      }

      "have a scoreModifier for word squares" in {
        doubleWordSquare.scoreModifier shouldEqual 2
        tripleWordSquare.scoreModifier shouldEqual 3
      }

      "have a scoreModifier for letter squares" in {
        doubleLetterSquare.scoreModifier shouldEqual 2
        tripleLetterSquare.scoreModifier shouldEqual 3
      }

      "have a color" in {
        standardSquare.color shouldEqual "white" // Replace with the actual color
      }

      "have a letter" in {
        standardSquare.letter shouldEqual stone
      }

      "be able to update its stone" in {
        val newStone = Stone('B')
        val updatedStandardSquare = standardSquare.update(newStone)
        updatedStandardSquare.letter shouldEqual newStone

        val updatedTripleLetterSquare = tripleLetterSquare.update(newStone)
        updatedTripleLetterSquare.letter shouldEqual newStone
      }

      "be able to convert to string" in {
        standardSquare.toString shouldEqual "A" // Replace with the actual string representation
      }
    }
  }
}
