package de.htwg.se.scrabble.aview.languages

class EnglishStrategy extends LanguageStrategy{
  override def requestNewWord: String = "Enter your word: "
  override def wordAlreadyAddedToDictionary: String = "Word already added to dictionary"
  override def wordAddedToDictionary: String = "Word added to dictionary"
  override def invalidcoordinates: String = "Invalid coordinates"
  override def invalidInput: String = "Invalid input"
  override def notInDictionary: String = " is not in dictionary, sorry!"
  override def wordNotInDictionary: String = "Word not in dictionary"
  override def EnterYourWord: String = "Enter your personal words, which should be available in the dictionary, apart from the default words"
  override def ExitWord: String = "stop"

}
