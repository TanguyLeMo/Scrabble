package de.htwg.se.scrabble.model

import scala.collection.immutable.HashSet

class Dictionary(val set: HashSet[String]) {
  val file: String = "englishWordList.txt"

  // Auxiliary constructor
  def this() = this(HashSet.empty[String])

  // Read lines from file and create a new Dictionary
  def readLines: Dictionary = {
    val source = scala.io.Source.fromResource("englishWordList.txt")
    val lines = try source.getLines.toList finally source.close()
    val set = HashSet() ++ lines
    new Dictionary(set)
  }
  def contains(word: String): Boolean = set.contains(word)
  def addWord(word: String): Dictionary = new Dictionary(set + word)
  def removeWord(word: String): Dictionary = new Dictionary(set - word)

  // Auxiliary method to call readLines if no parameters are given

}
