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
  def EnterYourWord:String
  def ExitWord: String 
}
