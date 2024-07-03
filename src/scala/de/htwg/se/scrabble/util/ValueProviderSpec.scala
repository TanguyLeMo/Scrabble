package de.htwg.se.scrabble.util

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class ValueProviderSpec extends AnyWordSpec with Matchers {

  "An IntegerProvider" should {
    "provide an Integer value" in {
      val intProvider = new IntegerProvider
      val value = intProvider.get()
      value shouldBe a[Integer]
      value should be(0) // assuming 0 is the expected value
    }
  }

  "A CharacterProvider" should {
    "provide a Character value" in {
      val charProvider = new CharacterProvider
      val value = charProvider.get()
      value shouldBe a[Character]
      value should be('_') // assuming '_' is the expected value
    }
  }
}
