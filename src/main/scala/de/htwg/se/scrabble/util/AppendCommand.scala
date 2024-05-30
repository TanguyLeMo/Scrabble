package de.htwg.se.scrabble.util

class AppendCommand(character: Char) extends Command[String] {
  override def doStep(t: String): String = t + character
  override def undoStep(t: String): String = t.dropRight(1)
  override def redoStep(t: String): String = t + character
  override def noStep(t: String): String = t
}