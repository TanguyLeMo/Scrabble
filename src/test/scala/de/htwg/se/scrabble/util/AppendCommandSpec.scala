package de.htwg.se.scrabble.util

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class AppendCommandSpec extends AnyWordSpec with Matchers {

  "An AppendCommand" should {
    "append a character to a string when doStep is called" in {
      val command = new AppendCommand('a')
      val result = command.doStep("test")
      result should be("testa")
    }

    "remove the last character from a string when undoStep is called" in {
      val command = new AppendCommand('a')
      val result = command.undoStep("testa")
      result should be("test")
    }

    "append a character to a string when redoStep is called" in {
      val command = new AppendCommand('a')
      val result = command.redoStep("test")
      result should be("testa")
    }

    "not change the string when noStep is called" in {
      val command = new AppendCommand('a')
      val result = command.noStep("test")
      result should be("test")
    }
  }
}
