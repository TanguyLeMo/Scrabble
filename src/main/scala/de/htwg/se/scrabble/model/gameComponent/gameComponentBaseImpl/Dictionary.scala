package de.htwg.se.scrabble.model.gameComponent.gameComponentBaseImpl

import com.google.inject.Inject
import de.htwg.se.scrabble.model.gameComponent.DictionaryInterface
import de.htwg.se.scrabble.util.LanguageEnum

import scala.collection.immutable.HashSet
import scala.language.postfixOps
class Dictionary @Inject (val set: HashSet[String]) extends DictionaryInterface{
  val file: String = "englishWordList.txt"
  def this() = this(HashSet.empty[String])
  
  
  def readLines (languageEnum: LanguageEnum): DictionaryInterface = {
    val source = scala.io.Source.fromResource(languageEnum match {
      case LanguageEnum.ENGLISH => "englishWordList.txt"
      case LanguageEnum.GERMAN => "germanWordList.txt"
      case LanguageEnum.FRENCH => "frenchWordList.txt"
      case LanguageEnum.ITALIAN => "italianWordList.txt"
      case null => "englishWordList.txt"
    })
    val lines = try source.getLines.toList finally source.close()
    val upperCase = lines.map(_.toUpperCase)
    val set = HashSet() ++ upperCase
    new Dictionary(set)
  }
  def contains(word: String): Boolean = set.contains(word.toUpperCase())

  def addWord(word: String): DictionaryInterface = new Dictionary(set + word.toUpperCase)
  def removeWord(word: String): DictionaryInterface = new Dictionary(set - word.toUpperCase())
}
