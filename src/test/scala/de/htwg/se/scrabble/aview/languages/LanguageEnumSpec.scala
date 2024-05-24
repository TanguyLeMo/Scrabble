package de.htwg.se.scrabble.aview.languages

import org.scalatest.*
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class LanguageEnumSpec extends AnyWordSpec with Matchers {
  "A LanguageEnum" should {
    "contain ENGLISH" in {
      LanguageEnum.ENGLISH should not be null
    }
    "contain GERMAN" in {
      LanguageEnum.GERMAN should not be null
    }
    "contain FRENCH" in {
      LanguageEnum.FRENCH should not be null
    }
    "contain ITALIAN" in {
      LanguageEnum.ITALIAN should not be null
    }
    "return the correct LanguageEnum for a given string" in {
      LanguageEnum.toLanguage("ENGLISH") should be (LanguageEnum.ENGLISH)
      LanguageEnum.toLanguage("GERMAN") should be (LanguageEnum.GERMAN)
      LanguageEnum.toLanguage("FRENCH") should be (LanguageEnum.FRENCH)
      LanguageEnum.toLanguage("ITALIAN") should be (LanguageEnum.ITALIAN)
    }
    "return english for an unknown input" in {
      LanguageEnum.toLanguage("UNKNOWN") should be (LanguageEnum.ENGLISH)
    }
  }
}