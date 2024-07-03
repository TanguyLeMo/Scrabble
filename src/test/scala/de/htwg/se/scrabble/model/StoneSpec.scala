package de.htwg.se.scrabble.model

import de.htwg.se.scrabble.model.gameComponent.gameComponentBaseImpl.Stone
import org.scalatest.matchers.must.Matchers
import org.scalatest.wordspec.AnyWordSpec

class StoneSpec extends AnyWordSpec with Matchers{
  "A Stone" when {
    "new" should {
      val stone = new Stone('A')
      "have a symbol" in {
        stone.symbol must be('A')
      }
      "have a String representation" in {
        stone.toString must be("A")
      }
      "be equal to another Stone with the same symbol" in {
        stone must equal(new Stone('A'))
      }
      "not be equal to another Stone with a different symbol" in {
        stone must not equal new Stone('B')
      }
      "not be equal to another type" in {
        stone must not equal "Stone"
      }
    }
  }
}
