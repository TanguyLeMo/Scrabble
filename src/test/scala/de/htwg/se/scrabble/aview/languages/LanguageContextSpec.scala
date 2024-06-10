package de.htwg.se.scrabble.aview.languages

import de.htwg.se.scrabble.model.languageComponent.LanguageContext
import org.scalatest.*
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class LanguageContextSpec extends AnyWordSpec with Matchers {
  "A LanguageContext" should {
    val context = new LanguageContext("ENGLISH")

    "request a new word" in {
      context.requestNewWord should be ("Enter your word: ")
    }

    "return a message if a word is already added to the dictionary" in {
      context.wordAlreadyAddedToDictionary should be ("Word already added to dictionary")
    }

    "return a message if a word is added to the dictionary" in {
      context.wordAddedToDictionary should be ("Word added to dictionary")
    }

    "return a message for invalid coordinates" in {
      context.invalidcoordinates should be ("Invalid coordinates")
    }

    "return a message for invalid input" in {
      context.invalidInput should be ("Invalid input")
    }

    "return a message if a word is not in the dictionair" in {
      context.notInDictionary should be (" is not in dictionary, sorry!")
    }

    "return a message if a word is not in the dictionary" in {
      context.wordNotInDictionary should be ("Word not in dictionary")
    }

    "return a message to enter a word for the dictionaries" in {
      context.enterWordforDictionary should not be ("Enter your personal words, which should be available in the dictionary, apart from the default words \n type: stop to finish the input of your personal words")
    }

    "return a stop message" in {
      context.stop should be ("stop")
    }

    "return a language setting message" in {
      context.languageSetting should be ("Language setting: ")
    }

    "return a message to enter a word" in {
      context.enterWord should be ("Enter your word: and Direction (H|V) example: myWord A 0 H")
    }

    "return a message for no correct direction" in {
      context.noCorrectDirection should be (" is not a correct direction")
    }

    "return a message if a word doesn't fit" in {
      context.wordDoesntFit should be ("Word doesnt fit")
    }
    "return an exit message" in {
      context.exit should be ("exit")
    }
    "create a german language context" in {
      val germanContext = new LanguageContext("GERMAN")
      germanContext.wordDoesntFit should be ("Das Wort passt nicht")
    }
    "create a french language context" in {
      val frenchContext = new LanguageContext("FRENCH")
      frenchContext.wordDoesntFit should be ("Le mot ne convient pas")
    }
    "create an italian language context" in {
      val italianContext = new LanguageContext("ITALIAN")
      italianContext.wordDoesntFit should be ("La parola non si adatta")
    }
  }
  "nameAlreadyTaken" should {
    "return a message if the name is already taken" in {
      val context = new LanguageContext("ENGLISH")
      context.nameAlreadyTaken should be ("Name already taken")
    }
  }
  "enterNumberofPlayers" should {
    "return a message to enter the number of players" in {
      val context = new LanguageContext("ENGLISH")
      context.enterNumberofPlayers should be ("Enter number of Players")
    }
  }
}