package de.htwg.se.scrabble.model
import de.htwg.se.scrabble.aview.languages.LanguageEnum

import scala.collection.immutable.HashSet
import scala.language.postfixOps
class Dictionary(val set: HashSet[String]) {
  val file: String = "englishWordList.txt"
  def this() = this(HashSet.empty[String])
  def readLines(languageEnum: LanguageEnum): Dictionary = {
    val source = scala.io.Source.fromResource(languageEnum match { 
      case LanguageEnum.ENGLISH => "englishWordList.txt"
      case LanguageEnum.GERMAN => "GermanWordList.txt"
      case LanguageEnum.FRENCH => "FrenchWordList.txt"
      case LanguageEnum.ITALIAN => "ItalianWordList.txt"
      case null => "englishWordList.txt"
    })

    val lines = try source.getLines.toList finally source.close()
    val set = HashSet() ++ lines
    new Dictionary(set)
  }
  def contains(word: String): Boolean = set.contains(word)
  def addWord(word: String): Dictionary = new Dictionary(set + word)
  def removeWord(word: String): Dictionary = new Dictionary(set - word)
}
