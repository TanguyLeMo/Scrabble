package de.htwg.se.scrabble.aview.languages

class GermanStrategy extends LanguageStrategy{
  override def languageSetting: String = "Spracheinstellung: "
  override def requestNewWord: String = "Bitte geben Sie ein neues Wort ein"
  override def wordAlreadyAddedToDictionary: String = "Das Wort wurde bereits zum Wörterbuch hinzugefügt"
  override def wordAddedToDictionary: String = "Das Wort wurde zum Wörterbuch hinzugefügt"
  override def invalidcoordinates: String = "Ungültige Koordinaten"
  override def invalidInput: String = "Ungültige Eingabe"
  override def notInDictionary: String = "Das Wort ist nicht im Wörterbuch, sorry"
  override def wordNotInDictionary: String = "Das Wort ist nicht im Wörterbuch"
  override def enterWordforDictionary: String = "Sie können eigene Wörter in das Wörterbuch eintragen"
  override def stop: String = "stop"
  override def enterWord: String = "Geben Sie Ihr Wort ein: und Richtung (H|V) Beispiel: meinWort A 0 H"
  override def NoCorrectDirection: String = " ist keine richtige Richtung"
  override def wordDoesntFit: String = "Das Wort passt nicht"
  override def exit: String = "exit"
  override def nameAlreadyTaken: String = "Der Name ist bereits vergeben"
  override def enterNumberofPlayers: String = "Geben Sie die Anzahl der Spieler ein"
  override def invalidNumber: String = "Ungültige Eingabe, bitte geben Sie eine gültige Nummer ein"
  override def enterPlayernames: String = "Geben Sie die Namen des ersten / nächsten Spieler ein"
  override def nameCantBeEmpty: String = "Der Name darf nicht leer sein"
  override def currentPlayer: String = "Aktueller Spieler: "
}
