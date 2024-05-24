package de.htwg.se.scrabble.aview.languages

import org.scalatest.*
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class GermanStrategySpec extends AnyWordSpec with Matchers {
  "A GermanStrategy" should {
    val strategy = new GermanStrategy()

    "request a new word" in {
      strategy.requestNewWord should be ("Bitte geben Sie ein neues Wort ein")
    }

    "return a message if a word is already added to the dictionary" in {
      strategy.wordAlreadyAddedToDictionary should be ("Das Wort wurde bereits zum Wörterbuch hinzugefügt")
    }

    "return a message if a word is added to the dictionary" in {
      strategy.wordAddedToDictionary should be ("Das Wort wurde zum Wörterbuch hinzugefügt")
    }

    "return a message for invalid coordinates" in {
      strategy.invalidcoordinates should be ("Ungültige Koordinaten")
    }

    "return a message for invalid input" in {
      strategy.invalidInput should be ("Ungültige Eingabe")
    }

    "return a message if a word is not in the dictionary" in {
      strategy.notInDictionary should be ("Das Wort ist nicht im Wörterbuch, sorry")
    }

    "return a message if a word is not in the dictionary (different context)" in {
      strategy.wordNotInDictionary should be ("Das Wort ist nicht im Wörterbuch")
    }

    "return a message to enter a word for the dictionary" in {
      strategy.enterWordforDictionary should be ("Sie können eigene Wörter in das Wörterbuch eintragen")
    }

    "return a stop message" in {
      strategy.stop should be ("stop")
    }

    "return a language setting message" in {
      strategy.languageSetting should be ("Spracheinstellung: ")
    }

    "return a message to enter a word" in {
      strategy.enterWord should be ("Bitte geben Sie ein Wort ein")
    }

    "return a message for no correct direction" in {
      strategy.NoCorrectDirection should be (" ist keine richtige Richtung")
    }

    "return a message if a word doesn't fit" in {
      strategy.wordDoesntFit should be ("Das Wort passt nicht")
    }

    "return an exit message" in {
      strategy.exit should be ("exit")
    }
  }
}