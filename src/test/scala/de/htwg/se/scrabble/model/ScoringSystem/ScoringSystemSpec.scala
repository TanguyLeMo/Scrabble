package de.htwg.se.scrabble.model.ScoringSystem

import de.htwg.se.scrabble.model.square.StandardSquareFactory
import de.htwg.se.scrabble.model.{Dictionary, Matrix, ScrabbleField, Stone, scoring}
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import de.htwg.se.scrabble.aview.languages.*
import de.htwg.se.scrabble.aview.languages.LanguageEnum.{ENGLISH, FRENCH, GERMAN}
import de.htwg.se.scrabble.model.scoring.*

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

      " When placed HORN on the board" should {
        "calculate the correct score" in {
          val scrabbleField = new ScrabbleField(new Matrix(Vector.fill(15, 15)(new StandardSquareFactory().createDoubleSquare(new Stone()))).init(), new Dictionary().readLines(ENGLISH), new StandardSquareFactory, ENGLISH)

          val horn = scrabbleField.placeWord(0, 0, 'H', "DBKJQ")
          val score = horn.scoringSystem.collectPoints(horn.matrix, 0, 0, 'V', "DBKJQ_")
          score shouldBe 108
        }
      }
      "When using a french scoring system" should {
        "calculate the correct score" in {
          val scrabbleField = new ScrabbleField(new Matrix(Vector.fill(15, 15)(new StandardSquareFactory().createDoubleSquare(new Stone()))).init(), new Dictionary().readLines(FRENCH), new StandardSquareFactory, FRENCH)

          val horn = scrabbleField.placeWord(0, 0, 'H', "ADBFJK")
          val score = horn.scoringSystem.collectPoints(horn.matrix, 0, 0, 'V', "ADBFJK")
          score shouldBe 96
        }
      }
      "When using a german scoring system" should {
        "calculate the correct score" in {
          val scrabbleField = new ScrabbleField(new Matrix(Vector.fill(15, 15)(new StandardSquareFactory().createDoubleSquare(new Stone()))).init(), new Dictionary().readLines(GERMAN), new StandardSquareFactory, GERMAN)

          val horn = scrabbleField.placeWord(0, 0, 'H', "EHMCÄÖY")
          val score = horn.scoringSystem.collectPoints(horn.matrix, 0, 0, 'V', "EHMCÄÖY")
          score shouldBe 114
        }
        "return 0 when the word is empty" in {
          val scrabbleField = new ScrabbleField(new Matrix(Vector.fill(15, 15)(new StandardSquareFactory().createDoubleSquare(new Stone()))).init(), new Dictionary().readLines(GERMAN), new StandardSquareFactory, GERMAN)
          val horn = scrabbleField.placeWord(0, 0, 'H', " ")
          val score = horn.scoringSystem.collectPoints(horn.matrix, 0, 0, 'V', " ")
          score shouldBe 0
          }
        "return 0 for French" in {
          val scrabbleField = new ScrabbleField(
            new Matrix(Vector.fill(15, 15)(new StandardSquareFactory().createDoubleSquare(new Stone()))).init(),
            new Dictionary().readLines(LanguageEnum.FRENCH),
            new StandardSquareFactory,
            LanguageEnum.FRENCH
          )
          val horn = scrabbleField.placeWord(0, 0, 'H', " ")
          val score = horn.scoringSystem.collectPoints(horn.matrix, 0, 0, 'V', " ")
          score shouldBe 0
        }
        "return 0 for English" in {
          val scrabbleField = new ScrabbleField(
            new Matrix(Vector.fill(15, 15)(new StandardSquareFactory().createDoubleSquare(new Stone()))).init(),
            new Dictionary().readLines(LanguageEnum.ENGLISH),
            new StandardSquareFactory,
            LanguageEnum.ENGLISH
          )
          val horn = scrabbleField.placeWord(0, 0, 'H', " ")
          val score = horn.scoringSystem.collectPoints(horn.matrix, 0, 0, 'V', " ")
          score shouldBe 0
        }

      }
      "When using an Italian scoring system" should {
        "calculate the correct score" in {
          val scrabbleField = new ScrabbleField(new Matrix(Vector.fill(15, 15)(new StandardSquareFactory().createDoubleSquare(new Stone()))).init(), new Dictionary().readLines(LanguageEnum.ITALIAN), new StandardSquareFactory, LanguageEnum.ITALIAN)
          val horn = scrabbleField.placeWord(0, 0, 'H', "OCLBGQ.")
          val score = horn.scoringSystem.collectPoints(horn.matrix, 0, 0, 'V', "OCLBGQ.")
          score shouldBe 102
        }
        "When there is no stone placed on a square the isEmpty method should return true" in {
          val scrabbleField = new ScrabbleField(new Matrix(Vector.fill(15, 15)(new StandardSquareFactory().createDoubleSquare(new Stone()))).init(), new Dictionary().readLines(LanguageEnum.ITALIAN), new StandardSquareFactory, LanguageEnum.ITALIAN)
          for(i <- 0 to 14)
            for(j <- 0 to 14)
              scrabbleField.matrix.field(i)(j).isEmpty shouldBe true
        }
      }
    }

    "There are 4 different Factories which is managed by the ScoringSystemFactories" should {
      "return a EnglishScoringSystem" in {
        val scoringSystemFactory = new EnglishScoringFactory()
        val scoringSystem = scoringSystemFactory.createScoringSystem()
        scoringSystem shouldBe a[EnglishScoringSystem]
      }
      "return a FrenchScoringSystem" in {
        val scoringSystemFactory = new FrenchScoringFactory
        val scoringSystem = scoringSystemFactory.createScoringSystem()
        scoringSystem shouldBe a[FrenchScoringSystem]
      }
      "return a GermanScoringSystem" in {
        val scoringSystemFactory = new GermanScoringFactory()
        val scoringSystem = scoringSystemFactory.createScoringSystem()
        scoringSystem shouldBe a[GermanScoringSystem]
      }
      "return a ItalianScoringSystem" in {
        val scoringSystemFactory = new ItalianScoringFactory()
        val scoringSystem = scoringSystemFactory.createScoringSystem()
        scoringSystem shouldBe a[ItalianScoringSystem]
      }

    }
  }
}