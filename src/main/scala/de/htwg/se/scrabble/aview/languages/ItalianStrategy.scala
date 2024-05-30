package de.htwg.se.scrabble.aview.languages

class ItalianStrategy extends LanguageStrategy{
  override def languageSetting: String = "Lingua impostata su "
  override def requestNewWord: String = "Inserisci una nuova parola"
  override def wordAlreadyAddedToDictionary: String = "Questa parola è già stata aggiunta al dizionario"
  override def wordAddedToDictionary: String = "Parola aggiunta al dizionario"
  override def invalidcoordinates: String = "Coordinate non valide"
  override def invalidInput: String = "Input non valido"
  override def notInDictionary: String = "Parola non presente nel dizionario"
  override def wordNotInDictionary: String = "Parola non presente nel dizionario"
  override def enterWordforDictionary: String = "Inserisci una parola per il dizionario"
  override def stop: String = "stop"
  override def enterWord: String = "Inserisci la tua parola: e Direzione (H|V) esempio: miaParola A 0 H"
  override def NoCorrectDirection: String = " non è una direzione corretta"
  override def wordDoesntFit: String = "La parola non si adatta"
  override def exit: String = "uscire"
  override def nameAlreadyTaken: String = "Il nome è già stato preso"
  override def enterNumberofPlayers: String = "Inserisci il numero di giocatori"
  override def invalidNumber: String = "Input non valido, inserisci un numero valido"
}
