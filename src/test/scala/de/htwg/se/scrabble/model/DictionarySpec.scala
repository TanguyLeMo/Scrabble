package de.htwg.se.scrabble.model

import org.scalatest.*
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import scala.collection.immutable.HashSet


class DictionarySpec extends AnyWordSpec with Matchers{
  "A Dictionary" when {
    "new" should {
      val dictionary = Dictionary(HashSet.empty[String])
      "have a empty dictionary" in {
        dictionary.set.size shouldEqual 0
      }
    }
    "when readLines" should {
      val dictionary = Dictionary(HashSet.empty[String]).readLines
      "have a dictionary with 267751 words" in {
        dictionary.set.size should be (267751)
      }
    }
    "when check if a word is in the dictionary" should {
      val dictionary = Dictionary(HashSet.empty[String]).readLines
      "return true if the word is in the dictionary" in {
        dictionary.contains("hello") should be (true)
      }
      "return false if the word is not in the dictionary" in {
        dictionary.contains("helloworld") should be (false)
      }
    }
    "when removing a word from the dictionary" should {
      val dictionary = Dictionary(HashSet.empty[String]).readLines
      "remove the word from the dictionary" in {
        val compare = dictionary.removeWord("hello")
        compare.contains("hello") should be (false)
      }
    }
    "when adding a word to the dictionary" should {
      val dictionary = Dictionary(HashSet.empty[String]).readLines
      "add the word to the dictionary" in {
        val compare = dictionary.addWord("rindfleischetikettierungsüberwachungsaufgabenübertragungsgesetz")
        compare.contains("rindfleischetikettierungsüberwachungsaufgabenübertragungsgesetz") should be (true)
      }
    }
    "and a word is added to the dictionary wich is already in the dictionary" should {
      val dictionary = Dictionary(HashSet.empty[String]).readLines
      "not change the the Dictionary" in {
        val compare = dictionary.addWord("hello").addWord("hello")

        compare.set.size should be (Dictionary().readLines.set.size)
      }
    }
  }

}
