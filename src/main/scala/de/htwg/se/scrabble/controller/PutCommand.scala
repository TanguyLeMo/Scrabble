package de.htwg.se.scrabble
package controller
import model.{ Move, ScrabbleField, Stone}
import util.Command
import util.UndoManager

class PutCommand(move: Move) extends Command[ScrabbleField]:
  override def noStep(field: ScrabbleField): ScrabbleField = field
  override def doStep(field: ScrabbleField): ScrabbleField =  { println("hello") ; field.placeWord(move.yPosition, move.xPosition, move.direction, move.word) }
  override def undoStep(field: ScrabbleField): ScrabbleField = {field.removeWord(move.yPosition, move.xPosition, move.direction, move.word.replaceAll("[a-zA-Z]", "_")) }
  override def redoStep(field: ScrabbleField): ScrabbleField = field.placeWord(move.yPosition, move.xPosition, move.direction, move.word)