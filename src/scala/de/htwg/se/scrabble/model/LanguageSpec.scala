package de.htwg.se.scrabble.model

import de.htwg.se.scrabble.model.languageComponent.languages.LanguageContext
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class LanguageSpec extends AnyWordSpec with Matchers {
  "A Language Context " when {
    val FrenchlanguageContext = new LanguageContext("FRENCH")
    val EnglishlanguageContext = new LanguageContext("ENGLISH")
    val GermanlanguageContext = new LanguageContext("GERMAN")
    val ItalianlanguageContext = new LanguageContext("ITALIAN")

    "used in French" should {
      "have a requestNewWord" in {
        FrenchlanguageContext.requestNewWord should be("Entrez un nouveau mot")
      }
      "have wordAlreadyAddedToDictionary" in {
        FrenchlanguageContext.wordAlreadyAddedToDictionary should be("Le mot a déjà été ajouté au dictionnaire")
      }
      "have wordAddedToDictionary" in {
        FrenchlanguageContext.wordAddedToDictionary should be("Le mot a été ajouté au dictionnaire")
      }
      "have invalidcoordinates" in {
        FrenchlanguageContext.invalidcoordinates should be("Coordonnées invalides")
      }
      "have invalidInput" in {
        FrenchlanguageContext.invalidInput should be("Entrée invalide")
      }
      "have notInDictionary" in {
        FrenchlanguageContext.notInDictionary should be(" n'est pas dans le dictionnaire")
      }
      "have wordNotInDictionary" in {
        FrenchlanguageContext.wordNotInDictionary should be(" n'est pas dans le dictionnaire")
      }
      "have enterWordforDictionary" in {
        FrenchlanguageContext.enterWordForDictionary should be("Entrez un mot pour le dictionnaire")
      }
      "have stop" in {
        FrenchlanguageContext.stop should be("stop")
      }
      "have enterWord" in {
        FrenchlanguageContext.enterWord should be("Entrez votre mot: et Direction (H|V) exemple: monMot A 0 H")
      }
      "have NoCorrectDirection" in {
        FrenchlanguageContext.noCorrectDirection should be("Aucune direction correcte")
      }
      "have wordDoesntFit" in {
        FrenchlanguageContext.wordDoesntFit should be("Le mot ne convient pas")
      }
      "have exit" in {
        FrenchlanguageContext.exit should be("sortir")
      }
      "have nameAlreadyTaken" in {
        FrenchlanguageContext.nameAlreadyTaken should be("Le nom est déjà pris")
      }
      "have enterNumberofPlayers" in {
        FrenchlanguageContext.enterNumberofPlayers should be("Entrez le nombre de joueurs")
      }
      "have invalidNumber" in {
        FrenchlanguageContext.invalidNumber should be("Entrée invalide, veuillez entrer un nombre valide")
      }
      "have enterPlayernames" in {
        FrenchlanguageContext.enterPlayerNames should be("Entrez les noms du premier / prochain joueur ")
      }
      "have nameCantBeEmpty" in {
        FrenchlanguageContext.nameCantBeEmpty should be("Le nom ne peut pas être vide")
      }
      "have currentPlayer" in {
        FrenchlanguageContext.currentPlayer should be("Joueur actuel: ")
      }
      "have noteEnoughStones" in {
        FrenchlanguageContext.notEnoughStones should be("Pas assez de pierres")
      }
      "have leaderBoard" in {
        FrenchlanguageContext.leaderBoard should be("Classement")
      }
      "have place" in {
        FrenchlanguageContext.place should be("placer")
      }
      "have horizontal" in {
        FrenchlanguageContext.horizontal should be("horizontal")
      }
      "have vertical" in {
        FrenchlanguageContext.vertical should be("vertical")
      }
      "have save" in {
        FrenchlanguageContext.save should be("sauvegarder")
      }
      "have load" in {
        FrenchlanguageContext.load should be("charger")
      }
      "have changeLanguage" in {
        FrenchlanguageContext.changeLanguage should be("changer de langue")
      }
      "have french" in {
        FrenchlanguageContext.french should be("français")
      }
      "have english" in {
        FrenchlanguageContext.english should be("anglais")
      }
      "have german" in {
        FrenchlanguageContext.german should be("allemand")
      }
      "have italian" in {
        FrenchlanguageContext.italian should be("italien")
      }
      "have undo" in {
        FrenchlanguageContext.undo should be("annuler")
      }
      "have redo" in {
        FrenchlanguageContext.redo should be("refaire")
      }
      "have currentLanguageRequest" in {
        FrenchlanguageContext.currentLanguageRequest should be("Langue actuelle: ")
      }
      "have currentLanguage" in {
        FrenchlanguageContext.currentLanguage should be("français")
      }
      "have next" in {
        FrenchlanguageContext.next should be("continuer")
      }
    }
    "used in German" should {
      "have a requestNewWord" in {
        GermanlanguageContext.requestNewWord should be("Bitte geben Sie ein neues Wort ein")
      }
      "have wordAlreadyAddedToDictionary" in {
        GermanlanguageContext.wordAlreadyAddedToDictionary should be("Das Wort wurde bereits zum Wörterbuch hinzugefügt")
      }
      "have wordAddedToDictionary" in {
        GermanlanguageContext.wordAddedToDictionary should be("Das Wort wurde zum Wörterbuch hinzugefügt")
      }
      "have invalidcoordinates" in {
        GermanlanguageContext.invalidcoordinates should be("Ungültige Koordinaten")
      }
      "have invalidInput" in {
        GermanlanguageContext.invalidInput should be("Ungültige Eingabe")
      }
      "have notInDictionary" in {
        GermanlanguageContext.notInDictionary should be("Das Wort ist nicht im Wörterbuch, sorry")
      }
      "have wordNotInDictionary" in {
        GermanlanguageContext.wordNotInDictionary should be("Das Wort ist nicht im Wörterbuch")
      }
      "have enterWordforDictionary" in {
        GermanlanguageContext.enterWordForDictionary should be("Sie können eigene Wörter in das Wörterbuch eintragen")
      }
      "have stop" in {
        GermanlanguageContext.stop should be("stop")
      }
      "have enterWord" in {
        GermanlanguageContext.enterWord should be("Geben Sie Ihr Wort ein: und Richtung (H|V) Beispiel: meinWort A 0 H")
      }
      "have NoCorrectDirection" in {
        GermanlanguageContext.noCorrectDirection should be(" ist keine richtige Richtung")
      }
      "have wordDoesntFit" in {
        GermanlanguageContext.wordDoesntFit should be("Das Wort passt nicht")
      }
      "have exit" in {
        GermanlanguageContext.exit should be("exit")
      }
      "have nameAlreadyTaken" in {
        GermanlanguageContext.nameAlreadyTaken should be("Der Name ist bereits vergeben")
      }
      "have enterNumberofPlayers" in {
        GermanlanguageContext.enterNumberofPlayers should be("Geben Sie die Anzahl der Spieler ein")
      }
      "have invalidNumber" in {
        GermanlanguageContext.invalidNumber should be("Ungültige Eingabe, bitte geben Sie eine gültige Nummer ein")
      }
      "have enterPlayernames" in {
        GermanlanguageContext.enterPlayerNames should be("Geben Sie die Namen des ersten / nächsten Spieler ein")
      }
      "have nameCantBeEmpty" in {
        GermanlanguageContext.nameCantBeEmpty should be("Der Name darf nicht leer sein")
      }
      "have currentPlayer" in {
        GermanlanguageContext.currentPlayer should be("Aktueller Spieler: ")
      }
      "have noteEnoughStones" in {
        GermanlanguageContext.notEnoughStones should be("Nicht genügend Steine")
      }
      "have leaderBoard" in {
        GermanlanguageContext.leaderBoard should be("Bestenliste")
      }
      "have place" in {
        GermanlanguageContext.place should be("platzieren")
      }
      "have horizontal" in {
        GermanlanguageContext.horizontal should be("horizontal")
      }
      "have vertical" in {
        GermanlanguageContext.vertical should be("vertikal")
      }
      "have save" in {
        GermanlanguageContext.save should be("speichern")
      }
      "have load" in {
        GermanlanguageContext.load should be("laden")
      }
      "have changeLanguage" in {
        GermanlanguageContext.changeLanguage should be("Sprache ändern")
      }
      "have french" in {
        GermanlanguageContext.french should be("Französisch")
      }
      "have english" in {
        GermanlanguageContext.english should be("Englisch")
      }
      "have german" in {
        GermanlanguageContext.german should be("Deutsch")
      }
      "have italian" in {
        GermanlanguageContext.italian should be("Italienisch")
      }
      "have undo" in {
        GermanlanguageContext.undo should be("rückgängig")
      }
      "have redo" in {
        GermanlanguageContext.redo should be("wiederholen")
      }
      "have currentLanguageRequest" in {
        GermanlanguageContext.currentLanguageRequest should be("Aktuelle Sprache: ")
      }
      "have currentLanguage" in {
        GermanlanguageContext.currentLanguage should be("Deutsch")
      }
      "have next" in {
        GermanlanguageContext.next should be("weiter")
      }
    }
    "used in Italian" should {
      "have a requestNewWord" in {
        ItalianlanguageContext.requestNewWord should be("Inserisci una nuova parola")
      }
      "have wordAlreadyAddedToDictionary" in {
        ItalianlanguageContext.wordAlreadyAddedToDictionary should be("Questa parola è già stata aggiunta al dizionario")
      }
      "have wordAddedToDictionary" in {
        ItalianlanguageContext.wordAddedToDictionary should be("Parola aggiunta al dizionario")
      }
      "have invalidcoordinates" in {
        ItalianlanguageContext.invalidcoordinates should be("Coordinate non valide")
      }
      "have invalidInput" in {
        ItalianlanguageContext.invalidInput should be("Input non valido")
      }
      "have notInDictionary" in {
        ItalianlanguageContext.notInDictionary should be("Parola non presente nel dizionario")
      }
      "have wordNotInDictionary" in {
        ItalianlanguageContext.wordNotInDictionary should be("Parola non presente nel dizionario")
      }
      "have enterWordforDictionary" in {
        ItalianlanguageContext.enterWordForDictionary should be("Inserisci una parola per il dizionario")
      }
      "have stop" in {
        ItalianlanguageContext.stop should be("stop")
      }
      "have enterWord" in {
        ItalianlanguageContext.enterWord should be("Inserisci la tua parola: e Direzione (H|V) esempio: miaParola A 0 H")
      }
      "have NoCorrectDirection" in {
        ItalianlanguageContext.noCorrectDirection should be(" non è una direzione corretta")
      }
      "have wordDoesntFit" in {
        ItalianlanguageContext.wordDoesntFit should be("La parola non si adatta")
      }
      "have exit" in {
        ItalianlanguageContext.exit should be("uscire")
      }
      "have nameAlreadyTaken" in {
        ItalianlanguageContext.nameAlreadyTaken should be("Il nome è già stato preso")
      }
      "have enterNumberofPlayers" in {
        ItalianlanguageContext.enterNumberofPlayers should be("Inserisci il numero di giocatori")
      }
      "have invalidNumber" in {
        ItalianlanguageContext.invalidNumber should be("Input non valido, inserisci un numero valido")
      }
      "have enterPlayernames" in {
        ItalianlanguageContext.enterPlayerNames should be("Inserisci i nomi dei primi / prossimi giocatore")
      }
      "have nameCantBeEmpty" in {
        ItalianlanguageContext.nameCantBeEmpty should be("Il nome non può essere vuoto")
      }
      "have currentPlayer" in {
        ItalianlanguageContext.currentPlayer should be("Giocatore corrente: ")
      }
      "have noteEnoughStones" in {
        ItalianlanguageContext.notEnoughStones should be("Non abbastanza pietre")
      }
      "have leaderBoard" in {
        ItalianlanguageContext.leaderBoard should be("Classifica")
      }
      "have place" in {
        ItalianlanguageContext.place should be("Posizione")
      }
      "have horizontal" in {
        ItalianlanguageContext.horizontal should be("orizzontale")
      }
      "have vertical" in {
        ItalianlanguageContext.vertical should be("verticale")
      }
      "have save" in {
        ItalianlanguageContext.save should be("salvare")
      }
      "have load" in {
        ItalianlanguageContext.load should be("caricare")
      }
      "have changeLanguage" in {
        ItalianlanguageContext.changeLanguage should be("cambia lingua")
      }
      "have french" in {
        ItalianlanguageContext.french should be("francese")
      }
      "have english" in {
        ItalianlanguageContext.english should be("inglese")
      }
      "have german" in {
        ItalianlanguageContext.german should be("tedesco")
      }
      "have italian" in {
        ItalianlanguageContext.italian should be("italiano")
      }
      "have undo" in {
        ItalianlanguageContext.undo should be("annulla")
      }
      "have redo" in {
        ItalianlanguageContext.redo should be("rifare")
      }
      "have currentLanguageRequest" in {
        ItalianlanguageContext.currentLanguageRequest should be("Lingua corrente: ")
      }
      "have currentLanguage" in {
        ItalianlanguageContext.currentLanguage should be("Italiano")
      }
      "have next" in {
        ItalianlanguageContext.next should be("continua")
      }
    }

    "used in English" should {
      "have a requestNewWord" in {
        EnglishlanguageContext.requestNewWord should be("Enter your word: ")
      }
      "have wordAlreadyAddedToDictionary" in {
        EnglishlanguageContext.wordAlreadyAddedToDictionary should be("Word already added to dictionary")
      }
      "have wordAddedToDictionary" in {
        EnglishlanguageContext.wordAddedToDictionary should be("Word added to dictionary")
      }
      "have invalidcoordinates" in {
        EnglishlanguageContext.invalidcoordinates should be("Invalid coordinates")
      }
      "have invalidInput" in {
        EnglishlanguageContext.invalidInput should be("Invalid input")
      }
      "have notInDictionary" in {
        EnglishlanguageContext.notInDictionary should be(" is not in dictionary, sorry!")
      }
      "have wordNotInDictionary" in {
        EnglishlanguageContext.wordNotInDictionary should be("Word not in dictionary")
      }
      "have enterWordforDictionary" in {
        EnglishlanguageContext.enterWordForDictionary should be("Enter your personal words, which should be available in the dictionary, apart from the default words \n type:" + EnglishlanguageContext.stop + "to finish the input of your personal words")
      }
      "have stop" in {
        EnglishlanguageContext.stop should be("stop")
      }
      "have languageSetting" in {
        EnglishlanguageContext.languageSetting should be("Language setting: ")
      }
      "have enterWord" in {
        EnglishlanguageContext.enterWord should be("Enter your word: and Direction (H|V) example: myWord A 0 H")
      }
      "have NoCorrectDirection" in {
        EnglishlanguageContext.noCorrectDirection should be(" is not a correct direction")
      }
      "have wordDoesntFit" in {
        EnglishlanguageContext.wordDoesntFit should be("Word doesnt fit")
      }
      "have exit" in {
        EnglishlanguageContext.exit should be("exit")
      }
      "have enterNumberofPlayers" in {
        EnglishlanguageContext.enterNumberofPlayers should be("Enter number of Players")
      }
      "have nameAlreadyTaken" in {
        EnglishlanguageContext.nameAlreadyTaken should be("Name already taken")
      }
      "have invalidNumber" in {
        EnglishlanguageContext.invalidNumber should be("Invalid input, please enter a valid number")
      }
      "have enterPlayernames" in {
        EnglishlanguageContext.enterPlayerNames should be("Enter the names of the first / next player")
      }
      "have nameCantBeEmpty" in {
        EnglishlanguageContext.nameCantBeEmpty should be("Name cant be empty")
      }
      "have currentPlayer" in {
        EnglishlanguageContext.currentPlayer should be("Current Player: ")
      }
      "have noteEnoughStones" in {
        EnglishlanguageContext.notEnoughStones should be("Not enough stones")
      }
      "have leaderBoard" in {
        EnglishlanguageContext.leaderBoard should be("Leaderboard")
      }
      "have place" in {
        EnglishlanguageContext.place should be("Place")
      }
      "have horizontal" in {
        EnglishlanguageContext.horizontal should be("horizontal")
      }
      "have vertical" in {
        EnglishlanguageContext.vertical should be("vertical")
      }
      "have save" in {
        EnglishlanguageContext.save should be("save")
      }
      "have load" in {
        EnglishlanguageContext.load should be("load")
      }
      "have changeLanguage" in {
        EnglishlanguageContext.changeLanguage should be("change language")
      }
      "have french" in {
        EnglishlanguageContext.french should be("french")
      }
      "have english" in {
        EnglishlanguageContext.english should be("english")
      }
      "have german" in {
        EnglishlanguageContext.german should be("german")
      }
      "have italian" in {
        EnglishlanguageContext.italian should be("italian")
      }
      "have undo" in {
        EnglishlanguageContext.undo should be("undo")
      }
      "have redo" in {
        EnglishlanguageContext.redo should be("redo")
      }
      "have currentLanguageRequest" in {
        EnglishlanguageContext.currentLanguageRequest should be("Current Language: ")
      }
      "have currentLanguage" in {
        EnglishlanguageContext.currentLanguage should be("english")
      }
      "have next" in {
        EnglishlanguageContext.next should be("next")
      }
    }
  }
}