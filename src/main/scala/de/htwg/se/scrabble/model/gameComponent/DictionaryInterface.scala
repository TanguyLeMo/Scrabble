package de.htwg.se.scrabble.model.gameComponent
import de.htwg.se.scrabble.model.gameComponent.gameComponentBaseImpl.Dictionary
import de.htwg.se.scrabble.util.LanguageEnum

import java.util
import scala.collection.immutable.HashSet


trait DictionaryInterface {
  val set: HashSet[String]
  def readLines(languageEnum: LanguageEnum): DictionaryInterface
  def contains(word: String): Boolean
  def addWord(word: String): DictionaryInterface
  def removeWord(word: String): DictionaryInterface
}
