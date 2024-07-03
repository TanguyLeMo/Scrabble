package de.htwg.se.scrabble.util

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class LanguageEnumSpec extends AnyWordSpec with Matchers {

  "LanguageEnum" should {
    "return ENGLISH for unknown languages" in {
      LanguageEnum.toLanguage("unknown") should be (LanguageEnum.ENGLISH)
    }

    "return GERMAN for 'german' or 'deutsch'" in {
      LanguageEnum.toLanguage("german") should be (LanguageEnum.GERMAN)
      LanguageEnum.toLanguage("deutsch") should be (LanguageEnum.GERMAN)
    }

    "return FRENCH for 'french', 'francais', or 'français'" in {
      LanguageEnum.toLanguage("french") should be (LanguageEnum.FRENCH)
      LanguageEnum.toLanguage("francais") should be (LanguageEnum.FRENCH)
      LanguageEnum.toLanguage("français") should be (LanguageEnum.FRENCH)
    }

    "return ITALIAN for 'italian' or 'italiano'" in {
      LanguageEnum.toLanguage("italian") should be (LanguageEnum.ITALIAN)
      LanguageEnum.toLanguage("italiano") should be (LanguageEnum.ITALIAN)
    }
  }
}
