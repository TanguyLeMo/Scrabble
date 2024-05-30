package de.htwg.se.scrabble.aview.languages

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
}
