package de.htwg.se.scrabble.aview.languages

class LanguageContext(languageStrat: String){
  val language: LanguageEnum = LanguageEnum.toLanguage(languageStrat)
  val languageStrategy: LanguageStrategy = language match {
    case LanguageEnum.ENGLISH => new EnglishStrategy()
    case LanguageEnum.GERMAN => new GermanStrategy()
    case LanguageEnum.FRENCH => new FrenchStrategy()
    case LanguageEnum.ITALIAN => new ItalianStrategy()
  }
  def requestNewWord: String = languageStrategy.requestNewWord
  def wordAlreadyAddedToDictionary: String = languageStrategy.wordAlreadyAddedToDictionary
  def wordAddedToDictionary: String = languageStrategy.wordAddedToDictionary
  def invalidcoordinates: String = languageStrategy.invalidcoordinates
  def invalidInput: String = languageStrategy.invalidInput
  def notInDictionary: String = languageStrategy.notInDictionary
  def wordNotInDictionary: String = languageStrategy.wordNotInDictionary
  def enterWordforDictionary: String = languageStrategy.enterWordforDictionary
  def stop: String = languageStrategy.stop
  def languageSetting: String = languageStrategy.languageSetting
  def enterWord: String = languageStrategy.enterWord
  def noCorrectDirection: String = languageStrategy.NoCorrectDirection
  def wordDoesntFit: String = languageStrategy.wordDoesntFit
  def exit: String = languageStrategy.exit
  def nameAlreadyTaken: String = languageStrategy.nameAlreadyTaken
  def enterNumberofPlayers: String = languageStrategy.enterNumberofPlayers
}
