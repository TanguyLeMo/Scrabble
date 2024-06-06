package de.htwg.se.scrabble.aview.languages

import de.htwg.se.scrabble.model.languages.EnglishStrategy
import org.scalatest.*
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class EnglishStrategySpec extends AnyWordSpec with Matchers {
  "An EnglishStrategy" should {
    val strategy = new EnglishStrategy()

    "request a new word" in {
      strategy.requestNewWord should be ("Enter your word: ")
    }

    "return a message if a word is already added to the dictionary" in {
      strategy.wordAlreadyAddedToDictionary should be ("Word already added to dictionary")
    }

    "return a message if a word is added to the dictionary" in {
      strategy.wordAddedToDictionary should be ("Word added to dictionary")
    }

    "return a message for invalid coordinates" in {
      strategy.invalidcoordinates should be ("Invalid coordinates")
    }

    "return a message for invalid input" in {
      strategy.invalidInput should be ("Invalid input")
    }

    "return a message if a word is not in the dictionary" in {
      strategy.notInDictionary should be (" is not in dictionary, sorry!")
    }

    "return a message if a word is not in the dictionary (different context)" in {
      strategy.wordNotInDictionary should be ("Word not in dictionary")
    }

    "return a message to enter a word for the dictionary" in {
      strategy.enterWordforDictionary should not be ("Enter your personal words, which should be available in the dictionary, apart from the default words \n type: stop to finish the input of your personal words")
    }

    "return a stop message" in {
      strategy.stop should be ("stop")
    }

    "return a language setting message" in {
      strategy.languageSetting should be ("Language setting: ")
    }

    "return a message to enter a word" in {
      strategy.enterWord should be ("Enter your word: and Direction (H|V) example: myWord A 0 H")
    }

    "return a message for no correct direction" in {
      strategy.NoCorrectDirection should be (" is not a correct direction")
    }

    "return a message if a word doesn't fit" in {
      strategy.wordDoesntFit should be ("Word doesnt fit")
    }

    "return an exit message" in {
      strategy.exit should be ("exit")
    }
    "return a message for entering the number of players" in {
      strategy.enterNumberofPlayers should be ("Enter number of Players")
    }
    "return a message if name is already taken" in {
      strategy.nameAlreadyTaken should be ("Name already taken")
    }
  }
}