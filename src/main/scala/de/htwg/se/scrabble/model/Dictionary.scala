package de.htwg.se.scrabble.model
import jdk.javadoc.internal.doclets.toolkit.taglets.SnippetTaglet.Language

import scala.collection.immutable.HashSet
class Dictionary(val set: HashSet[String]) {
  def this() = this(HashSet.empty[String])
  def readLines(language: String): Dictionary = {
    val source = scala.io.Source.fromResource(language + "WordList.txt")
    val lines = try source.getLines.toList finally source.close()
    val set = HashSet() ++ lines
    new Dictionary(set)
  }
  def contains(word: String): Boolean = set.contains(word)
  def addWord(word: String): Dictionary = new Dictionary(set + word)
  def removeWord(word: String): Dictionary = new Dictionary(set - word)
}
