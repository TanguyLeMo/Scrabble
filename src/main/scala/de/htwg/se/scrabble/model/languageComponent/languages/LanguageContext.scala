package de.htwg.se.scrabble.model.languageComponent.languages

import com.google.inject.Inject
import de.htwg.se.scrabble.model.languageComponent.{LanguageContextInterface, LanguageStrategy}
import de.htwg.se.scrabble.util.LanguageEnum


class LanguageContext @Inject (languageStrat: String) extends LanguageContextInterface{
  val language: LanguageEnum = LanguageEnum.toLanguage(languageStrat)
  val languageStrategy: LanguageStrategy = language match {
    case LanguageEnum.ENGLISH => EnglishStrategy()
    case LanguageEnum.GERMAN => GermanStrategy()
    case LanguageEnum.FRENCH => FrenchStrategy()
    case LanguageEnum.ITALIAN => ItalianStrategy()
  }
  override def requestNewWord: String = languageStrategy.requestNewWord
  override def wordAlreadyAddedToDictionary: String = languageStrategy.wordAlreadyAddedToDictionary
  override def wordAddedToDictionary: String = languageStrategy.wordAddedToDictionary
  override def invalidcoordinates: String = languageStrategy.invalidcoordinates
  override def invalidInput: String = languageStrategy.invalidInput
  override def notInDictionary: String = languageStrategy.notInDictionary
  override def wordNotInDictionary: String = languageStrategy.wordNotInDictionary
  override def enterWordForDictionary: String = languageStrategy.enterWordforDictionary
  override def stop: String = languageStrategy.stop
  override def languageSetting: String = languageStrategy.languageSetting
  override def enterWord: String = languageStrategy.enterWord
  override def noCorrectDirection: String = languageStrategy.NoCorrectDirection
  override def wordDoesntFit: String = languageStrategy.wordDoesntFit
  override def exit: String = languageStrategy.exit
  override def nameAlreadyTaken: String = languageStrategy.nameAlreadyTaken
  override def enterNumberofPlayers: String = languageStrategy.enterNumberofPlayers
  override def invalidNumber: String = languageStrategy.invalidNumber
  override def enterPlayerNames: String = languageStrategy.enterPlayernames
  override def nameCantBeEmpty: String = languageStrategy.nameCantBeEmpty
  override def currentPlayer: String = languageStrategy.currentPlayer
  override def notEnoughStones: String = languageStrategy.noteEnoughStones
  override def leaderBoard: String = languageStrategy.leaderBoard
  override def place: String = languageStrategy.place
  override def horizontal: String = languageStrategy.horizontal
  override def vertical: String = languageStrategy.vertical
  override def save: String = languageStrategy.save
  override def load: String = languageStrategy.load
  override def changeLanguage: String = languageStrategy.changeLanguage
  override def french: String = languageStrategy.french
  override def english: String = languageStrategy.english
  override def german: String = languageStrategy.german
  override def italian: String = languageStrategy.italian
  override def undo : String = languageStrategy.undo
  override def redo : String = languageStrategy.redo
  override def currentLanguageRequest: String = languageStrategy.currentLanguageRequest
  override def currentLanguage: String = languageStrategy.currentLanguage
  override def next: String = languageStrategy.next
  override def ownWordForDictionary: String = languageStrategy.ownWordForDictionary
}
