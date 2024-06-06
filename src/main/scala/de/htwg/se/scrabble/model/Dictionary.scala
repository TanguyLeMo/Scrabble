package de.htwg.se.scrabble.model

import de.htwg.se.scrabble.model.languages.LanguageEnum
import scala.collection.immutable.HashSet
import scala.language.postfixOps
class Dictionary(val set: HashSet[String]) {
  val file: String = "englishWordList.txt"
  def this() = this(HashSet.empty[String])
  def readLines(languageEnum: LanguageEnum): Dictionary = {
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
  def addWord(word: String): Dictionary = new Dictionary(set + word.toUpperCase)
  def removeWord(word: String): Dictionary = new Dictionary(set - word.toUpperCase())
}
