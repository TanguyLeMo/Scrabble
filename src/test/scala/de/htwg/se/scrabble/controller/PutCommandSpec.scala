package de.htwg.se.scrabble.controller

import de.htwg.se.scrabble.controller.ControllerComponent.ControllerBaseImpl.PutCommand
import de.htwg.se.scrabble.model.gameComponent.ScrabbleFieldInterface
import de.htwg.se.scrabble.util.{Command, placeWordsAsMove}
import org.mockito.Mockito._
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import org.scalatestplus.mockito.MockitoSugar.mock

class PutCommandSpec extends AnyWordSpec with Matchers {

  "A PutCommand" should {
    "doStep correctly" in {
      // Arrange
      val move = placeWordsAsMove(0, 0,'H', "hello")
      val command = new PutCommand(move)
      val field = mock[ScrabbleFieldInterface]

      // Act
      when(field.placeWord(move.yPosition, move.xPosition, move.direction, move.word)).thenReturn(field)
      val result = command.doStep(field)

      // Assert
      result should be (field)
      verify(field).placeWord(move.yPosition, move.xPosition, move.direction, move.word)
    }

    "undoStep correctly" in {
      // Arrange
      val move = placeWordsAsMove(0, 0, 'H', "hello")
      val command = new PutCommand(move)
      val field = mock[ScrabbleFieldInterface]
      val removedWord = "_____"

      // Act
      when(field.removeWord(move.yPosition, move.xPosition, move.direction, removedWord)).thenReturn(field)
      val result = command.undoStep(field)

      // Assert
      result should be (field)
      verify(field).removeWord(move.yPosition, move.xPosition, move.direction, removedWord)
    }

    "redoStep correctly" in {
      // Arrange
      val move = placeWordsAsMove(0, 0, '_', "hello")
      val command = new PutCommand(move)
      val field = mock[ScrabbleFieldInterface]

      // Act
      when(field.placeWord(move.yPosition, move.xPosition, move.direction, move.word)).thenReturn(field)
      val result = command.redoStep(field)

      // Assert
      result should be (field)
      verify(field).placeWord(move.yPosition, move.xPosition, move.direction, move.word)
    }

    "noStep correctly" in {
      // Arrange
      val move = placeWordsAsMove(0, 0, '_', "hello")
      val command = new PutCommand(move)
      val field = mock[ScrabbleFieldInterface]

      // Act
      val result = command.noStep(field)

      // Assert
      result should be (field)
    }
  }
}
