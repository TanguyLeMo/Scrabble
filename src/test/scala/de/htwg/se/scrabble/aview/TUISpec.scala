package de.htwg.se.scrabble
package aview

import org.scalatest.wordspec.AnyWordSpec
import de.htwg.se.scrabble.controller.Controller
import de.htwg.se.scrabble.model.ScrabbleField
import org.scalatest.matchers.should.Matchers

class TUISpec extends AnyWordSpec with Matchers {

  val scrabbleField = new ScrabbleField(15)

  "A TUI" when {
    "processInputLine is called" should{
      "modify the state of the Controller when valid input is given" in {
        val controller = new Controller(scrabbleField)

        val tui = new TUI(controller)

        val testfield: ScrabbleField = scrabbleField.placeWord(1, 0, 'H', "word")
        tui.processInputLine("word A 1 H")
        val finalState: ScrabbleField = controller.field
        finalState shouldEqual testfield

      }
      "not modify the state of the Controller when invalid input is given" in {
        val controller = new Controller(scrabbleField)
        val tui = new TUI(controller)

        val initialState = controller.field
        tui.processInputLine("invalid input")
        val finalState = controller.field

        finalState should equal(initialState)
      }
      "returning the String exit when the input is exit" in {
        val controller = new Controller(scrabbleField)

        val tui = new TUI(controller)

        tui.processInputLine("exit") should equal("exit")
      }
      "check the coordinates of the input and not change the state of the Scrabblefield if so" in {
        val controller = new Controller(scrabbleField)

        val tui = new TUI(controller)
        tui.processInputLine("word A 16 V")
        tui.controller.field should equal(scrabbleField)
      }
    }
    "do nothing when the coordinates are not valid" in {
      val controller = new Controller(scrabbleField)

      val tui = new TUI(controller)
      tui.processInputLine("word 3 16 V")
      tui.controller.field should equal(scrabbleField)
    }
    "do nothing when the direction i not valid" in {
      val controller = new Controller(scrabbleField)

      val tui = new TUI(controller)
      tui.processInputLine("word A 1 G")
      tui.controller.field should equal(scrabbleField)
    }

    "translateCoordinate is called" should {
      "return correct coordinates for valid input" in {
        val controller = new Controller(scrabbleField)
        val tui = new TUI(controller)

        tui.translateCoordinate("A 1") should equal((0, 1))
        tui.translateCoordinate("B 2") should equal((1, 2))
        tui.translateCoordinate("Z 15") should equal((25, 15))
      }

      "throw a NumberFormatException for invalid input" in {
        val controller = new Controller(scrabbleField)
        val tui = new TUI(controller)
        an [NumberFormatException] should be thrownBy tui.translateCoordinate("A B")
      }
    }

    "validCoordinateInput is called" should {
      "return true for valid coordinates" in {
        val controller = new Controller(scrabbleField)

        val tui = new TUI(controller)
        tui.validCoordinateInput("A", "1") shouldBe true
        tui.validCoordinateInput("Z", "15") shouldBe true
      }

      "return false for invalid coordinates" in {
        val controller = new Controller(scrabbleField)

        val tui = new TUI(controller)
        tui.validCoordinateInput("AA3", "1") shouldBe false
        tui.validCoordinateInput("A", "1A") shouldBe false
        tui.validCoordinateInput("1", "A") shouldBe false
        tui.validCoordinateInput("A", "A") shouldBe false
      }
    }
  }
}