package de.htwg.se.scrabble.model

import de.htwg.se.scrabble.model.languageComponent.languages.LanguageContext
import org.scalatest.matchers.must.Matchers
import org.scalatest.wordspec.AnyWordSpec

class LanguageSpec extends AnyWordSpec with Matchers {
  "A Language Context " when {
    val FrenchlanguageContext = new LanguageContext("FRENCH")
    val EnglishlanguageContext = new LanguageContext("ENGLISH")
    val GermanlanguageContext = new LanguageContext("GERMAN")
    val ItalianlanguageContext = new LanguageContext("ITALIAN")

    "used in French" should {
      "have a requestNewWord" in {
        FrenchlanguageContext.requestNewWord must be("Entrez un nouveau mot")
      }
      "have wordAlreadyAddedToDictionary" in {
        FrenchlanguageContext.wordAlreadyAddedToDictionary must be("Le mot a déjà été ajouté au dictionnaire")
      }
      "have wordAddedToDictionary" in {
        FrenchlanguageContext.wordAddedToDictionary must be("Le mot a été ajouté au dictionnaire")
      }
      "have invalidcoordinates" in {
        FrenchlanguageContext.invalidcoordinates must be("Coordonnées invalides")
      }
      "have invalidInput" in {
        FrenchlanguageContext.invalidInput must be("Entrée invalide")
      }
      "have notInDictionary" in {
        FrenchlanguageContext.notInDictionary must be(" n'est pas dans le dictionnaire")
      }
      "have wordNotInDictionary" in {
        FrenchlanguageContext.wordNotInDictionary must be(" n'est pas dans le dictionnaire")
      }
      "have enterWordforDictionary" in {
        FrenchlanguageContext.enterWordForDictionary must be("Entrez un mot pour le dictionnaire")
      }
      "have stop" in {
        FrenchlanguageContext.stop must be("stop")
      }
      "have enterWord" in {
        FrenchlanguageContext.enterWord must be("Entrez votre mot: et Direction (H|V) exemple: monMot A 0 H")
      }
      "have NoCorrectDirection" in {
        FrenchlanguageContext.noCorrectDirection must be("Aucune direction correcte")
      }
      "have wordDoesntFit" in {
        FrenchlanguageContext.wordDoesntFit must be("Le mot ne convient pas")
      }
      "have exit" in {
        FrenchlanguageContext.exit must be("sortir")
      }
      "have nameAlreadyTaken" in {
        FrenchlanguageContext.nameAlreadyTaken must be("Le nom est déjà pris")
      }
      "have enterNumberofPlayers" in {
        FrenchlanguageContext.enterNumberofPlayers must be("Entrez le nombre de joueurs")
      }
      "have invalidNumber" in {
        FrenchlanguageContext.invalidNumber must be("Entrée invalide, veuillez entrer un nombre valide")
      }
      "have enterPlayernames" in {
        FrenchlanguageContext.enterPlayerNames must be("Entrez les noms du premier / prochain joueur ")
      }
      "have nameCantBeEmpty" in {
        FrenchlanguageContext.nameCantBeEmpty must be("Le nom ne peut pas être vide")
      }
      "have currentPlayer" in {
        FrenchlanguageContext.currentPlayer must be("Joueur actuel: ")
      }
      "have noteEnoughStones" in {
        FrenchlanguageContext.notEnoughStones must be("Pas assez de pierres")
      }
      "have leaderBoard" in {
        FrenchlanguageContext.leaderBoard must be("Classement")
      }
      "have place" in {
        FrenchlanguageContext.place must be("placer")
      }
      "have horizontal" in {
        FrenchlanguageContext.horizontal must be("horizontal")
      }
      "have vertical" in {
        FrenchlanguageContext.vertical must be("vertical")
      }
      "have save" in {
        FrenchlanguageContext.save must be("sauvegarder")
      }
      "have load" in {
        FrenchlanguageContext.load must be("charger")
      }
      "have changeLanguage" in {
        FrenchlanguageContext.changeLanguage must be("changer de langue")
      }
      "have french" in {
        FrenchlanguageContext.french must be("français")
      }
      "have english" in {
        FrenchlanguageContext.english must be("anglais")
      }
      "have german" in {
        FrenchlanguageContext.german must be("allemand")
      }
      "have italian" in {
        FrenchlanguageContext.italian must be("italien")
      }
      "have undo" in {
        FrenchlanguageContext.undo must be("annuler")
      }
      "have redo" in {
        FrenchlanguageContext.redo must be("refaire")
      }
      "have currentLanguageRequest" in {
        FrenchlanguageContext.currentLanguageRequest must be("Langue actuelle: ")
      }
      "have currentLanguage" in {
        FrenchlanguageContext.currentLanguage must be("français")
      }
      "have next" in {
        FrenchlanguageContext.next must be("continuer")
      }
    }
    "used in German" should {
      "have a requestNewWord" in {
        GermanlanguageContext.requestNewWord must be("Bitte geben Sie ein neues Wort ein")
      }
      "have wordAlreadyAddedToDictionary" in {
        GermanlanguageContext.wordAlreadyAddedToDictionary must be("Das Wort wurde bereits zum Wörterbuch hinzugefügt")
      }
      "have wordAddedToDictionary" in {
        GermanlanguageContext.wordAddedToDictionary must be("Das Wort wurde zum Wörterbuch hinzugefügt")
      }
      "have invalidcoordinates" in {
        GermanlanguageContext.invalidcoordinates must be("Ungültige Koordinaten")
      }
      "have invalidInput" in {
        GermanlanguageContext.invalidInput must be("Ungültige Eingabe")
      }
      "have notInDictionary" in {
        GermanlanguageContext.notInDictionary must be("Das Wort ist nicht im Wörterbuch, sorry")
      }
      "have wordNotInDictionary" in {
        GermanlanguageContext.wordNotInDictionary must be("Das Wort ist nicht im Wörterbuch")
      }
      "have enterWordforDictionary" in {
        GermanlanguageContext.enterWordForDictionary must be("Sie können eigene Wörter in das Wörterbuch eintragen")
      }
      "have stop" in {
        GermanlanguageContext.stop must be("stop")
      }
      "have enterWord" in {
        GermanlanguageContext.enterWord must be("Geben Sie Ihr Wort ein: und Richtung (H|V) Beispiel: meinWort A 0 H")
      }
      "have NoCorrectDirection" in {
        GermanlanguageContext.noCorrectDirection must be(" ist keine richtige Richtung")
      }
      "have wordDoesntFit" in {
        GermanlanguageContext.wordDoesntFit must be("Das Wort passt nicht")
      }
      "have exit" in {
        GermanlanguageContext.exit must be("exit")
      }
      "have nameAlreadyTaken" in {
        GermanlanguageContext.nameAlreadyTaken must be("Der Name ist bereits vergeben")
      }
      "have enterNumberofPlayers" in {
        GermanlanguageContext.enterNumberofPlayers must be("Geben Sie die Anzahl der Spieler ein")
      }
      "have invalidNumber" in {
        GermanlanguageContext.invalidNumber must be("Ungültige Eingabe, bitte geben Sie eine gültige Nummer ein")
      }
      "have enterPlayernames" in {
        GermanlanguageContext.enterPlayerNames must be("Geben Sie die Namen des ersten / nächsten Spieler ein")
      }
      "have nameCantBeEmpty" in {
        GermanlanguageContext.nameCantBeEmpty must be("Der Name darf nicht leer sein")
      }
      "have currentPlayer" in {
        GermanlanguageContext.currentPlayer must be("Aktueller Spieler: ")
      }
      "have noteEnoughStones" in {
        GermanlanguageContext.notEnoughStones must be("Nicht genügend Steine")
      }
      "have leaderBoard" in {
        GermanlanguageContext.leaderBoard must be("Bestenliste")
      }
      "have place" in {
        GermanlanguageContext.place must be("platzieren")
      }
      "have horizontal" in {
        GermanlanguageContext.horizontal must be("horizontal")
      }
      "have vertical" in {
        GermanlanguageContext.vertical must be("vertikal")
      }
      "have save" in {
        GermanlanguageContext.save must be("speichern")
      }
      "have load" in {
        GermanlanguageContext.load must be("laden")
      }
      "have changeLanguage" in {
        GermanlanguageContext.changeLanguage must be("Sprache ändern")
      }
      "have french" in {
        GermanlanguageContext.french must be("Französisch")
      }
      "have english" in {
        GermanlanguageContext.english must be("Englisch")
      }
      "have german" in {
        GermanlanguageContext.german must be("Deutsch")
      }
      "have italian" in {
        GermanlanguageContext.italian must be("Italienisch")
      }
      "have undo" in {
        GermanlanguageContext.undo must be("rückgängig")
      }
      "have redo" in {
        GermanlanguageContext.redo must be("wiederholen")
      }
      "have currentLanguageRequest" in {
        GermanlanguageContext.currentLanguageRequest must be("Aktuelle Sprache: ")
      }
      "have currentLanguage" in {
        GermanlanguageContext.currentLanguage must be("Deutsch")
      }
      "have next" in {
        GermanlanguageContext.next must be("weiter")
      }
    }
    "used in Italian" should {
      "have a requestNewWord" in {
        ItalianlanguageContext.requestNewWord must be("Inserisci una nuova parola")
      }
      "have wordAlreadyAddedToDictionary" in {
        ItalianlanguageContext.wordAlreadyAddedToDictionary must be("Questa parola è già stata aggiunta al dizionario")
      }
      "have wordAddedToDictionary" in {
        ItalianlanguageContext.wordAddedToDictionary must be("Parola aggiunta al dizionario")
      }
      "have invalidcoordinates" in {
        ItalianlanguageContext.invalidcoordinates must be("Coordinate non valide")
      }
      "have invalidInput" in {
        ItalianlanguageContext.invalidInput must be("Input non valido")
      }
      "have notInDictionary" in {
        ItalianlanguageContext.notInDictionary must be("Parola non presente nel dizionario")
      }
      "have wordNotInDictionary" in {
        ItalianlanguageContext.wordNotInDictionary must be("Parola non presente nel dizionario")
      }
      "have enterWordforDictionary" in {
        ItalianlanguageContext.enterWordForDictionary must be("Inserisci una parola per il dizionario")
      }
      "have stop" in {
        ItalianlanguageContext.stop must be("stop")
      }
      "have enterWord" in {
        ItalianlanguageContext.enterWord must be("Inserisci la tua parola: e Direzione (H|V) esempio: miaParola A 0 H")
      }
      "have NoCorrectDirection" in {
        ItalianlanguageContext.noCorrectDirection must be(" non è una direzione corretta")
      }
      "have wordDoesntFit" in {
        ItalianlanguageContext.wordDoesntFit must be("La parola non si adatta")
      }
      "have exit" in {
        ItalianlanguageContext.exit must be("uscire")
      }
      "have nameAlreadyTaken" in {
        ItalianlanguageContext.nameAlreadyTaken must be("Il nome è già stato preso")
      }
      "have enterNumberofPlayers" in {
        ItalianlanguageContext.enterNumberofPlayers must be("Inserisci il numero di giocatori")
      }
      "have invalidNumber" in {
        ItalianlanguageContext.invalidNumber must be("Input non valido, inserisci un numero valido")
      }
      "have enterPlayernames" in {
        ItalianlanguageContext.enterPlayerNames must be("Inserisci i nomi dei primi / prossimi giocatore")
      }
      "have nameCantBeEmpty" in {
        ItalianlanguageContext.nameCantBeEmpty must be("Il nome non può essere vuoto")
      }
      "have currentPlayer" in {
        ItalianlanguageContext.currentPlayer must be("Giocatore corrente: ")
      }
      "have noteEnoughStones" in {
        ItalianlanguageContext.notEnoughStones must be("Non abbastanza pietre")
      }
      "have leaderBoard" in {
        ItalianlanguageContext.leaderBoard must be("Classifica")
      }
      "have place" in {
        ItalianlanguageContext.place must be("Posizione")
      }
      "have horizontal" in {
        ItalianlanguageContext.horizontal must be("orizzontale")
      }
      "have vertical" in {
        ItalianlanguageContext.vertical must be("verticale")
      }
      "have save" in {
        ItalianlanguageContext.save must be("salvare")
      }
      "have load" in {
        ItalianlanguageContext.load must be("caricare")
      }
      "have changeLanguage" in {
        ItalianlanguageContext.changeLanguage must be("cambia lingua")
      }
      "have french" in {
        ItalianlanguageContext.french must be("francese")
      }
      "have english" in {
        ItalianlanguageContext.english must be("inglese")
      }
      "have german" in {
        ItalianlanguageContext.german must be("tedesco")
      }
      "have italian" in {
        ItalianlanguageContext.italian must be("italiano")
      }
      "have undo" in {
        ItalianlanguageContext.undo must be("annulla")
      }
      "have redo" in {
        ItalianlanguageContext.redo must be("rifare")
      }
      "have currentLanguageRequest" in {
        ItalianlanguageContext.currentLanguageRequest must be("Lingua corrente: ")
      }
      "have currentLanguage" in {
        ItalianlanguageContext.currentLanguage must be("Italiano")
      }
      "have next" in {
        ItalianlanguageContext.next must be("continua")
      }
    }

    "used in English" should {
      "have a requestNewWord" in {
        EnglishlanguageContext.requestNewWord must be("Enter your word: ")
      }
      "have wordAlreadyAddedToDictionary" in {
        EnglishlanguageContext.wordAlreadyAddedToDictionary must be("Word already added to dictionary")
      }
      "have wordAddedToDictionary" in {
        EnglishlanguageContext.wordAddedToDictionary must be("Word added to dictionary")
      }
      "have invalidcoordinates" in {
        EnglishlanguageContext.invalidcoordinates must be("Invalid coordinates")
      }
      "have invalidInput" in {
        EnglishlanguageContext.invalidInput must be("Invalid input")
      }
      "have notInDictionary" in {
        EnglishlanguageContext.notInDictionary must be(" is not in dictionary, sorry!")
      }
      "have wordNotInDictionary" in {
        EnglishlanguageContext.wordNotInDictionary must be("Word not in dictionary")
      }
      "have enterWordforDictionary" in {
        EnglishlanguageContext.enterWordForDictionary must be("Enter your personal words, which should be available in the dictionary, apart from the default words \n type:" + EnglishlanguageContext.stop + "to finish the input of your personal words")
      }
      "have stop" in {
        EnglishlanguageContext.stop must be("stop")
      }
      "have languageSetting" in {
        EnglishlanguageContext.languageSetting must be("Language setting: ")
      }
      "have enterWord" in {
        EnglishlanguageContext.enterWord must be("Enter your word: and Direction (H|V) example: myWord A 0 H")
      }
      "have NoCorrectDirection" in {
        EnglishlanguageContext.noCorrectDirection must be(" is not a correct direction")
      }
      "have wordDoesntFit" in {
        EnglishlanguageContext.wordDoesntFit must be("Word doesnt fit")
      }
      "have exit" in {
        EnglishlanguageContext.exit must be("exit")
      }
      "have enterNumberofPlayers" in {
        EnglishlanguageContext.enterNumberofPlayers must be("Enter number of Players")
      }
      "have nameAlreadyTaken" in {
        EnglishlanguageContext.nameAlreadyTaken must be("Name already taken")
      }
      "have invalidNumber" in {
        EnglishlanguageContext.invalidNumber must be("Invalid input, please enter a valid number")
      }
      "have enterPlayernames" in {
        EnglishlanguageContext.enterPlayerNames must be("Enter the names of the first / next player")
      }
      "have nameCantBeEmpty" in {
        EnglishlanguageContext.nameCantBeEmpty must be("Name cant be empty")
      }
      "have currentPlayer" in {
        EnglishlanguageContext.currentPlayer must be("Current Player: ")
      }
      "have noteEnoughStones" in {
        EnglishlanguageContext.notEnoughStones must be("Not enough stones")
      }
      "have leaderBoard" in {
        EnglishlanguageContext.leaderBoard must be("Leaderboard")
      }
      "have place" in {
        EnglishlanguageContext.place must be("Place")
      }
      "have horizontal" in {
        EnglishlanguageContext.horizontal must be("horizontal")
      }
      "have vertical" in {
        EnglishlanguageContext.vertical must be("vertical")
      }
      "have save" in {
        EnglishlanguageContext.save must be("save")
      }
      "have load" in {
        EnglishlanguageContext.load must be("load")
      }
      "have changeLanguage" in {
        EnglishlanguageContext.changeLanguage must be("change language")
      }
      "have french" in {
        EnglishlanguageContext.french must be("french")
      }
      "have english" in {
        EnglishlanguageContext.english must be("english")
      }
      "have german" in {
        EnglishlanguageContext.german must be("german")
      }
      "have italian" in {
        EnglishlanguageContext.italian must be("italian")
      }
      "have undo" in {
        EnglishlanguageContext.undo must be("undo")
      }
      "have redo" in {
        EnglishlanguageContext.redo must be("redo")
      }
      "have currentLanguageRequest" in {
        EnglishlanguageContext.currentLanguageRequest must be("Current Language: ")
      }
      "have currentLanguage" in {
        EnglishlanguageContext.currentLanguage must be("english")
      }
      "have next" in {
        EnglishlanguageContext.next must be("next")
      }
    }
  }
}