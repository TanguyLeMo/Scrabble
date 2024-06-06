package de.htwg.se.scrabble.aview.languages

import de.htwg.se.scrabble.model.languages.ItalianStrategy
import org.scalatest.*
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class ItalianStrategySpec extends AnyWordSpec with Matchers {
  "An ItalianStrategy" should {
    val strategy = new ItalianStrategy()

    "request a new word" in {
      strategy.requestNewWord should be ("Inserisci una nuova parola")
    }

    "return a message if a word is already added to the dictionary" in {
      strategy.wordAlreadyAddedToDictionary should be ("Questa parola è già stata aggiunta al dizionario")
    }

    "return a message if a word is added to the dictionary" in {
      strategy.wordAddedToDictionary should be ("Parola aggiunta al dizionario")
    }

    "return a message for invalid coordinates" in {
      strategy.invalidcoordinates should be ("Coordinate non valide")
    }

    "return a message for invalid input" in {
      strategy.invalidInput should be ("Input non valido")
    }

    "return a message if a word is not in the dictionary" in {
      strategy.notInDictionary should be ("Parola non presente nel dizionario")
    }

    "return a message if a word is not in the dictionary (different context)" in {
      strategy.wordNotInDictionary should be ("Parola non presente nel dizionario")
    }

    "return a message to enter a word for the dictionary" in {
      strategy.enterWordforDictionary should be ("Inserisci una parola per il dizionario")
    }

    "return a stop message" in {
      strategy.stop should be ("stop")
    }

    "return a language setting message" in {
      strategy.languageSetting should be ("Lingua impostata su ")
    }

    "return a message to enter a word" in {
      strategy.enterWord should be ("Inserisci la tua parola: e Direzione (H|V) esempio: miaParola A 0 H")
    }

    "return a message for no correct direction" in {
      strategy.NoCorrectDirection should be (" non è una direzione corretta")
    }

    "return a message if a word doesn't fit" in {
      strategy.wordDoesntFit should be ("La parola non si adatta")
    }

    "return an exit message" in {
      strategy.exit should be ("uscire")
    }
    "return a message if name is aready taken" in {
      strategy.nameAlreadyTaken should be ("Il nome è già stato preso")
    }
    "return a message for entering the number of players" in {
      strategy.enterNumberofPlayers should be ("Inserisci il numero di giocatori")
    }
    "return a message if number is invalid" in {
      strategy.invalidNumber should be ("Input non valido, inserisci un numero valido")
    }

  }
}