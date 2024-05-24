package de.htwg.se.scrabble.aview.languages

class LanguageContext(languageStrat: String){
  val language: LanguageEnum = LanguageEnum.toLanguage(languageStrat)
  val languageStrategy: LanguageStrategy = language match {
    case LanguageEnum.ENGLISH => new EnglishStrategy()
    case LanguageEnum.GERMAN => null
    case LanguageEnum.FRENCH => null
    case LanguageEnum.ITALIAN => null
  }
  def requestNewWord: String = languageStrategy.requestNewWord
  def wordAlreadyAddedToDictionary: String = languageStrategy.wordAlreadyAddedToDictionary
  def wordAddedToDictionary: String = languageStrategy.wordAddedToDictionary
  def invalidcoordinates: String = languageStrategy.invalidcoordinates
  def invalidInput: String = languageStrategy.invalidInput
  def notInDictionary: String = languageStrategy.notInDictionary
  def wordNotInDictionary: String = languageStrategy.wordNotInDictionary
  def EnterYourWord: String = languageStrategy.EnterYourWord
  def ExitWord: String = languageStrategy.ExitWord
  def languageSetting: String = languageStrategy.languageSetting
}
