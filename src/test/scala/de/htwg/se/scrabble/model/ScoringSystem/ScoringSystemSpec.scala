package de.htwg.se.scrabble.model.ScoringSystem

import de.htwg.se.scrabble.model.square.StandardSquareFactory
import de.htwg.se.scrabble.model.{Dictionary, Matrix, ScrabbleField, Stone, scoring}
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import de.htwg.se.scrabble.aview.languages.*
import de.htwg.se.scrabble.aview.languages.LanguageEnum.{ENGLISH, FRENCH, GERMAN}
import de.htwg.se.scrabble.model.scoring.*

class ScoringSystemSpec extends AnyWordSpec with Matchers {
  val scrabbleField = new ScrabbleField(15, ENGLISH)
  "A ScoringSystem" when {
    "collecting points" should {
      "calculate the correct score for a word" in {

        val horn = scrabbleField.placeWord(7, 5, 'V', "HORN")
        val score = horn.scoringSystem.collectPoints(horn.matrix, 5, 7, 'V', "HORN")
        score shouldBe 7
      }
      " calculate the correct score for a word with a double word square" in {
        val horn = scrabbleField.placeWord(0, 0, 'V', "HORN")
        print(horn)
        val score = horn.scoringSystem.collectPoints(horn.matrix, 0, 0, 'V', "horn")
        score shouldBe 24
      }

      " When placed HORN on the board" should {
        "calculate the correct score" in {
          val horn = scrabbleField.placeWord(0, 0, 'V', "DBKJQ")
          val score = horn.scoringSystem.collectPoints(horn.matrix, 0, 0, 'V', "DBKJQ_")
          score shouldBe 108
        }
      }
      "When using a french scoring system" should {
        "calculate the correct score" in {
          val korn = new ScrabbleField(15, FRENCH)
          val horn = korn.placeWord(0, 0, 'V', "ADBFJK")
          val score = horn.scoringSystem.collectPoints(horn.matrix, 0, 0, 'V', "ADBFJK")
          score shouldBe 96
        }
        "return 0 when the word is empty" in {
          val horn = scrabbleField.placeWord(0, 0, 'H', " ")
          val score = horn.scoringSystem.collectPoints(horn.matrix, 0, 0, 'H', " ")
          score shouldBe 0
        }
      }
      "When using a german scoring system" should {
        "calculate the correct score" in {
          val korn = new ScrabbleField(15, GERMAN)
          val horn = korn.placeWord(0, 0, 'H', "gehen")
          val dorn = horn.placeWord(0, 0, 'V', "gehen")
          val score = dorn.scoringSystem.collectPoints(dorn.matrix, 0, 0, 'V', "gehen")
          print(dorn)
          score shouldBe 6
        }
        "return 0 when the word is empty" in {
          val horn = scrabbleField.placeWord(0, 0, 'H', " ")
          val score = horn.scoringSystem.collectPoints(horn.matrix, 0, 0, 'H', " ")
          score shouldBe 0
        }
      }

      "When using a german scoring system with the word gehen" should {
        "calculate the correct score" in {
          val korn = new ScrabbleField(15, GERMAN)
          val horn = korn.placeWord(0, 0, 'H', "EHMCÄÖY")
          val score = horn.scoringSystem.collectPoints(horn.matrix, 0, 0, 'H', "EHMCÄÖY")
          score shouldBe 114

        } // TODO: Check BUgs
        "return 0 when the word is empty" in {
          val horn = scrabbleField.placeWord(0, 0, 'H', " ")
          val score = horn.scoringSystem.collectPoints(horn.matrix, 0, 0, 'H', " ")
          score shouldBe 0
          }
        "return 0 for French" in {

          val horn = scrabbleField.placeWord(0, 0, 'H', " ")
          val score = horn.scoringSystem.collectPoints(horn.matrix, 0, 0, 'V', " ")
          score shouldBe 0
        }
        "return 0 for English" in {
          val horn = scrabbleField.placeWord(0, 0, 'V', " ")
          val score = horn.scoringSystem.collectPoints(horn.matrix, 0, 0, 'H', " ")
          score shouldBe 0
        }

      }
      "When using an Italian scoring system" should {
        "calculate the correct score" in {
          val korn = new ScrabbleField(15, LanguageEnum.ITALIAN)
          val horn = korn.placeWord(0, 0, 'H', "OCLBGQ.")
          val score = horn.scoringSystem.collectPoints(horn.matrix, 0, 0, 'H', "OCLBGQ.")
          score shouldBe 102
        }
        "When there is no stone placed on a square the isEmpty method should return true" in {
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