package de.htwg.se.scrabble.model.ScoringSystem

import de.htwg.se.scrabble.model.square.StandardSquareFactory
import de.htwg.se.scrabble.model.{Dictionary, Matrix, ScrabbleField, Stone}
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import de.htwg.se.scrabble.aview.languages.*
import de.htwg.se.scrabble.aview.languages.LanguageEnum.ENGLISH
import de.htwg.se.scrabble.model.scoring._

class ScoringSystemSpec extends AnyWordSpec with Matchers {
  "A ScoringSystem" when {
    "collecting points" should {
      "calculate the correct score for a word" in {
        val scrabbleField = new ScrabbleField(new Matrix(Vector.fill(15, 15)(new StandardSquareFactory().createDoubleSquare(new Stone()))).init(), new Dictionary().readLines(ENGLISH), new StandardSquareFactory, ENGLISH)

        val horn = scrabbleField.placeWord(7, 5, 'V', "HORN")
        val score = horn.scoringSystem.collectPoints(horn.matrix, 5, 7, 'H', "HORN")
        score shouldBe 7
      }
      " calculate the correct score for a word with a double word square" in {
        val scrabbleField = new ScrabbleField(new Matrix(Vector.fill(15, 15)(new StandardSquareFactory().createDoubleSquare(new Stone()))).init(), new Dictionary().readLines(ENGLISH), new StandardSquareFactory, ENGLISH)

        val horn = scrabbleField.placeWord(0, 0, 'V', "HORN")
        print(horn)
        val score = horn.scoringSystem.collectPoints(horn.matrix, 0, 0, 'H', "horn")
        score shouldBe 24
      }
    }
  }
}