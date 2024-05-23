package de.htwg.se.scrabble.model

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._

class StoneSpec extends AnyWordSpec {

  "A Stone" when {
    "created with a letter" should {
      "have the specified symbol" in {
        val stone = new Stone('A')
        stone.symbol shouldBe 'A'
      }

      "have a correct string representation" in {
        val stone = new Stone('B')
        stone.toString shouldBe "B"
      }

      "be equal to another Stone with the same symbol" in {
        val stone1 = new Stone('C')
        val stone2 = new Stone('C')
        stone1 shouldEqual stone2
      }

      "not be equal to another Stone with a different symbol" in {
        val stone1 = new Stone('D')
        val stone2 = new Stone('E')
        stone1 shouldNot equal(stone2)
      }
      "return false when comparing with another Type, without throwing an exception " in {
        val scrabbleField = new ScrabbleField(1)
        val stone = Stone()
        assert(! stone.equals(scrabbleField))
      }
    }

    "created without specifying a letter" should {
      "have the default symbol '_'" in {
        val stone = new Stone()
        stone.symbol shouldBe '_'
      }
    }
  }
}
