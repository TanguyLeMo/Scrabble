package de.htwg.se.scrabble.model.gameComponent
import de.htwg.se.scrabble.util.LanguageEnum


trait DictionaryInterface {
  def readLines(languageEnum: LanguageEnum): Dictionary
  def contains(word: String): Boolean
  def addWord(word: String): Dictionary
  def removeWord(word: String): Dictionary
}
