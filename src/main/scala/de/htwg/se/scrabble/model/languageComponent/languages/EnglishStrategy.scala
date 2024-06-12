package de.htwg.se.scrabble.model.languageComponent.languages

import de.htwg.se.scrabble.model.languageComponent.LanguageStrategy

class EnglishStrategy extends LanguageStrategy{
  override def requestNewWord: String = "Enter your word: "
  override def wordAlreadyAddedToDictionary: String = "Word already added to dictionary"
  override def wordAddedToDictionary: String = "Word added to dictionary"
  override def invalidcoordinates: String = "Invalid coordinates"
  override def invalidInput: String = "Invalid input"
  override def notInDictionary: String = " is not in dictionary, sorry!"
  override def wordNotInDictionary: String = "Word not in dictionary"
  override def enterWordforDictionary: String = "Enter your personal words, which should be available in the dictionary, apart from the default words \n type:" + stop + "to finish the input of your personal words"
  override def stop: String = "stop"
  override def languageSetting: String = "Language setting: "
  override def enterWord: String = "Enter your word: and Direction (H|V) example: myWord A 0 H"
  override def NoCorrectDirection: String = " is not a correct direction"
  override def wordDoesntFit: String = "Word doesnt fit"
  override def exit: String = "exit"
  override def enterNumberofPlayers: String = "Enter number of Players"
  override def nameAlreadyTaken: String = "Name already taken"
  override def invalidNumber: String = "Invalid input, please enter a valid number"
  override def enterPlayernames: String = "Enter the names of the first / next player"
  override def nameCantBeEmpty: String = "Name cant be empty"
  override def currentPlayer: String = "Current Player: "
  override def noteEnoughStones: String = "Not enough stones"
  override def leaderBoard: String = "Leaderboard"
}
