package de.htwg.se.scrabble
package aview

import org.scalatest.wordspec.AnyWordSpec
import de.htwg.se.scrabble.controller.Controller
import de.htwg.se.scrabble.model.ScrabbleField
import org.scalatest.matchers.should.Matchers

class TUISpec extends AnyWordSpec with Matchers {

  val scrabbleField = new ScrabbleField(15)

  "A TUI" when {
    "creating a new TUI by using the auxiliary method" should {
      "be the same field as" in {
        val tui = new TUI()
        val nonAuxiliaryTui = new TUI(new Controller(new ScrabbleField(15)))
        tui shouldEqual nonAuxiliaryTui
      }
    }
    "start is called" should {
      "print the welcome message and not do anything else" in {
        val controller = new Controller(scrabbleField)
        val tui = new TUI(controller)
        tui.start shouldEqual tui
      }
    }
    "dictionaryAddWords is called" should {
      "add a word to the dictionary" in {
        val controller = new Controller(scrabbleField)
        val tui = new TUI(controller)
        tui.dictionaryAddWords("trigonometricFunction")
        tui.controller.field.dictionary.set should contain("trigonometricFunction")
      }
      "not add a word to the dictionary if it is already in there" in {
        val controller = new Controller(scrabbleField)
        val tui = new TUI(controller)
        tui.dictionaryAddWords("word")
        tui.dictionaryAddWords("word") shouldEqual new TUI().dictionaryAddWords("word")
      }
      "return stop if the input is stop" in {
        val controller = new Controller(scrabbleField)
        val tui = new TUI(controller)
        tui.dictionaryAddWords("stop") shouldEqual "stop"
      }
    }


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

    "do nothing when the word is not in the dictionary" in {
      val controller = new Controller(scrabbleField)

      val tui = new TUI(controller)
      tui.processInputLine("notAWord A 1 H")
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

      "The equals method should return true if the controller fields are equal" in {
        val controller = new Controller(scrabbleField)
        val tui = new TUI(controller)
        val tui2 = new TUI(controller)
        tui.equals(tui2) shouldBe true
      }
      "and just return false if the types are different" in {
        val controller = new Controller(scrabbleField)
        val tui = new TUI(controller)
        tui.equals("test") shouldBe false
      }
    }
  }
}