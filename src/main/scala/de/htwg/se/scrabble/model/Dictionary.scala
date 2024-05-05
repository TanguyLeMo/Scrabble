package de.htwg.se.scrabble.model
import scala.collection.immutable.HashSet
class Dictionary(val set: HashSet[String]) {
  val file: String = "englishWordList.txt"
  def this() = this(HashSet.empty[String])
  def readLines: Dictionary = {
    val source = scala.io.Source.fromResource("englishWordList.txt")
    val lines = try source.getLines.toList finally source.close()
    val set = HashSet() ++ lines
    new Dictionary(set)
  }
  def contains(word: String): Boolean = set.contains(word)
  def addWord(word: String): Dictionary = new Dictionary(set + word)
  def removeWord(word: String): Dictionary = new Dictionary(set - word)
}
