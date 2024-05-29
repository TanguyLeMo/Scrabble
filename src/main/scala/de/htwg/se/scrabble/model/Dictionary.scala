package de.htwg.se.scrabble.model
import jdk.javadoc.internal.doclets.toolkit.taglets.SnippetTaglet.Language

import scala.collection.immutable.HashSet
class Dictionary(val set: HashSet[String],val dictionaryLanguage: String) {
  def this() = this(HashSet.empty[String], "english")
  def readLines: Dictionary = {
    val source = scala.io.Source.fromResource(dictionaryLanguage + "WorldList.txt")
    val lines = try source.getLines.toList finally source.close()
    val set = HashSet() ++ lines
    new Dictionary(set,dictionaryLanguage)
  }
  def contains(word: String): Boolean = set.contains(word)
  def addWord(word: String, languageOfDictionary: String): Dictionary = new Dictionary(set + word, languageOfDictionary)
  def removeWord(word: String, languageOfDictionary: String): Dictionary = new Dictionary(set - word, languageOfDictionary)
}
