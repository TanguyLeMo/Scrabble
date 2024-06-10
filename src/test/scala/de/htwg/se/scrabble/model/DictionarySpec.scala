package de.htwg.se.scrabble.model

import de.htwg.se.scrabble.model.languageComponent.LanguageEnum
import org.scalatest.*
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

import scala.collection.immutable.HashSet


class DictionarySpec extends AnyWordSpec with Matchers{
  "A Dictionary" when {
    val english = LanguageEnum.ENGLISH

    "new" should {
      val dictionary = Dictionary(HashSet.empty[String])
      "have a empty dictionary" in {
        dictionary.set.size shouldEqual 0
      }
    }
    "when readLines" should {
      val dictionary = Dictionary(HashSet.empty[String]).readLines(english)
      "have a dictionary with 267751 words" in {
        dictionary.set.size should be (267752)
      }
    }
    "when check if a word is in the dictionary" should {
      val dictionary = Dictionary(HashSet.empty[String]).readLines(english)
      "return true if the word is in the dictionary" in {
        dictionary.contains("hello") should be (true)
      }
      "return false if the word is not in the dictionary" in {
        dictionary.contains("helloworld") should be (false)
      }
    }
    "when removing a word from the dictionary" should {
      val dictionary = Dictionary(HashSet.empty[String]).readLines(english)
      "remove the word from the dictionary" in {
        val compare = dictionary.removeWord("hello")
        compare.contains("hello") should be (false)
      }
    }
    "when adding a word to the dictionary" should {
      val dictionary = Dictionary(HashSet.empty[String]).readLines(english)
      "add the word to the dictionary" in {
        val compare = dictionary.addWord("rindfleischetikettierungsüberwachungsaufgabenübertragungsgesetz")
        compare.contains("rindfleischetikettierungsüberwachungsaufgabenübertragungsgesetz") should be (true)
      }
    }
    "and a word is added to the dictionary wich is already in the dictionary" should {
      val dictionary = Dictionary(HashSet.empty[String]).readLines(english)
      "not change the the Dictionary" in {
        val compare = dictionary.addWord("hello").addWord("hello")
        compare.set.size should be (Dictionary().readLines(english).set.size)
      }
    }
    "should work with the german dictionary" in {
      val german = LanguageEnum.GERMAN
      val dictionary = Dictionary(HashSet.empty[String]).readLines(german)
      dictionary.contains("hallo".toUpperCase) should be (true)
    }
    "should work with the french dictionary" in {
      val french = LanguageEnum.FRENCH
      val dictionary = Dictionary(HashSet.empty[String]).readLines(french)
      dictionary.contains("bonjour".toUpperCase) should be (true)
    }
    "should work with the italian dictionary" in {
      val italian = LanguageEnum.ITALIAN
      val dictionary = Dictionary(HashSet.empty[String]).readLines(italian)
      dictionary.contains("ciao") should be (true)
    }
    "when parsing null to readLines" should {
      val dictionary = Dictionary(HashSet.empty[String]).readLines(null)
      "use the english dictionary" in {
        dictionary.contains("hello") should be (true)
      }
    }
  }

}
