package de.htwg.se.scrabble.aview.languages

import de.htwg.se.scrabble.model.languages.FrenchStrategy
import org.scalatest.*
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class FrenchStrategySpec extends AnyWordSpec with Matchers {
  "A FrenchStrategy" should {
    val strategy = new FrenchStrategy()

    "request a new word" in {
      strategy.requestNewWord should be("Entrez un nouveau mot")
    }

    "return a message if a word is already added to the dictionary" in {
      strategy.wordAlreadyAddedToDictionary should be("Le mot a déjà été ajouté au dictionnaire")
    }

    "return a message if a word is added to the dictionary" in {
      strategy.wordAddedToDictionary should be("Le mot a été ajouté au dictionnaire")
    }

    "return a message for invalid coordinates" in {
      strategy.invalidcoordinates should be("Coordonnées invalides")
    }

    "return a message for invalid input" in {
      strategy.invalidInput should be("Entrée invalide")
    }

    "return a message if a word is not in the dictionary" in {
      strategy.notInDictionary should be(" n'est pas dans le dictionnaire")
    }

    "return a message if a word is not in the dictionary (different context)" in {
      strategy.wordNotInDictionary should be(" n'est pas dans le dictionnaire")
    }

    "return a message to enter a word for the dictionary" in {
      strategy.enterWordforDictionary should be("Entrez un mot pour le dictionnaire")
    }

    "return a stop message" in {
      strategy.stop should be("stop")
    }

    "return a language setting message" in {
      strategy.languageSetting should be("Langue: ")
    }

    "return a message to enter a word" in {
      strategy.enterWord should be("Entrez votre mot: et Direction (H|V) exemple: monMot A 0 H")
    }

    "return a message for no correct direction" in {
      strategy.NoCorrectDirection should be("Aucune direction correcte")
    }

    "return a message if a word doesn't fit" in {
      strategy.wordDoesntFit should be("Le mot ne convient pas")
    }

    "return an exit message" in {
      strategy.exit should be("sortir")
    }
    "return a message if name is already taken" in {
      strategy.nameAlreadyTaken should be("Le nom est déjà pris")
    }
    "return a message for entering the number of players" in {
      strategy.enterNumberofPlayers should be("Entrez le nombre de joueurs")
    }
    "return a message if number is invalid" in {
      strategy.invalidNumber should be("Entrée invalide, veuillez entrer un nombre valide")
    }
  }
}