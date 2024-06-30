package de.htwg.se.scrabble.model.languageComponent

trait LanguageStrategy {
  def languageSetting: String
  def requestNewWord: String
  def wordAlreadyAddedToDictionary: String
  def wordAddedToDictionary: String
  def invalidcoordinates: String  
  def invalidInput: String
  def notInDictionary: String
  def wordNotInDictionary: String
  def enterWordforDictionary:String
  def stop: String 
  def enterWord: String
  def NoCorrectDirection: String
  def wordDoesntFit: String
  def exit: String
  def nameAlreadyTaken : String
  def enterNumberofPlayers : String
  def invalidNumber: String
  def enterPlayernames: String
  def nameCantBeEmpty: String
  def currentPlayer: String
  def noteEnoughStones: String
  def leaderBoard: String
  def place: String
  def horizontal:String
  def vertical: String
  def save: String
  def load: String
  def changeLanguage: String
  def french: String
  def german: String
  def english: String
  def italian: String  
  def undo : String
  def redo : String
  def currentLanguageRequest: String
  def currentLanguage: String
  def next: String
}
