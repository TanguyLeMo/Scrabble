package de.htwg.se.scrabble.model.languageComponent

trait LanguageContextInterface {
  def requestNewWord: String
  def wordAlreadyAddedToDictionary: String
  def wordAddedToDictionary: String
  def invalidcoordinates: String
  def invalidInput: String
  def notInDictionary: String
  def enterWordForDictionary: String
  def stop: String
  def languageSetting: String
  def enterWord: String
  def noCorrectDirection: String
  def wordDoesntFit: String
  def exit: String
  def nameAlreadyTaken: String
  def enterNumberofPlayers: String
  def invalidNumber: String
  def enterPlayerNames: String
  def nameCantBeEmpty: String
  def currentPlayer: String
  def notEnoughStones: String
  def leaderBoard: String
  def wordNotInDictionary: String
  def place: String
  def horizontal: String
  def vertical: String
  def save : String
  def load : String
  def changeLanguage: String
  def french: String
  def english: String
  def german: String
  def italian: String
  
}
