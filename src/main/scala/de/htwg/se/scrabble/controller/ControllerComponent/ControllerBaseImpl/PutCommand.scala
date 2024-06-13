package de.htwg.se.scrabble.controller.ControllerComponent.ControllerBaseImpl

import de.htwg.se.scrabble.model.gameComponent.ScrabbleFieldInterface
import de.htwg.se.scrabble.util.placeWordsAsMove
import de.htwg.se.scrabble.util.Command


class PutCommand(move: placeWordsAsMove) extends Command[ScrabbleFieldInterface]:
  override def noStep(field: ScrabbleFieldInterface): ScrabbleFieldInterface = field
  override def doStep(field: ScrabbleFieldInterface): ScrabbleFieldInterface =  field.placeWord(move.yPosition, move.xPosition, move.direction, move.word)
  override def undoStep(field: ScrabbleFieldInterface): ScrabbleFieldInterface = {field.removeWord(move.yPosition, move.xPosition, move.direction, move.word.replaceAll("[a-zA-Z]", "_")) }
  override def redoStep(field: ScrabbleFieldInterface): ScrabbleFieldInterface = field.placeWord(move.yPosition, move.xPosition, move.direction, move.word)