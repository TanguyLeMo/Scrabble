package de.htwg.se.scrabble
package aview

import org.scalatest.wordspec.AnyWordSpec
import de.htwg.se.scrabble.controller.Controller
import de.htwg.se.scrabble.model.{Player, ScrabbleField}
import org.scalatest.matchers.should.Matchers
import de.htwg.se.scrabble.aview.languages.*

import java.io.ByteArrayInputStream
class TUISpec extends AnyWordSpec with Matchers {


  val english: LanguageEnum = LanguageEnum.ENGLISH
  val scrabbleField = new ScrabbleField(15, english)

  "A TUI" when {
    "creating a new TUI by using the auxiliary method" should {
      "be the same field as" in {
        val input = "english\nenglish\n"
        val in = new ByteArrayInputStream(input.getBytes)
        Console.withIn(in) {
          val tui = new TUI(new Controller(new ScrabbleField(15, english)), new LanguageContext("english"))
          val nonAuxiliaryTui = new TUI(new Controller(new ScrabbleField(15, english)))
          tui shouldEqual nonAuxiliaryTui
        }
      }
    }
  }

    "dictionaryAddWords is called" should {
      "add a word to the dictionary" in {
        val input = "english\nstop\n"
        val in = new ByteArrayInputStream(input.getBytes)
        Console.withIn(in) {
          val controller = new Controller(scrabbleField)
          val tui = new TUI(controller)
          tui.controller.add("trigonometricFunction")
          tui.controller.field.dictionary.set should contain("trigonometricFunction".toUpperCase())
        }
      }


      "add weird words to the dictionary" in {
        val input = "wubalubadubdub\nstop\n"
        val in = new ByteArrayInputStream(input.getBytes)
        Console.withIn(in) {
        val controller = new Controller(scrabbleField)
        val tui = new TUI(controller)
        tui.dictionaryAddWords
        tui.controller.field.dictionary.set should contain("wubalubadubdub".toUpperCase())
        }
      }

      "not add a word to the dictionary if it is already in there" in {
        val input = "word\nword\nstop\n"
        val in = new ByteArrayInputStream(input.getBytes)
        Console.withIn(in) {
        val controller = new Controller(scrabbleField)
        val tui = new TUI(controller)
        tui.dictionaryAddWords
        tui.controller.field.dictionary.set should contain("word".toUpperCase())
        }
      }

      "print that the word is added to the dictionary" in {
        val input = "english\nenglish\nstop\n"
        val in = new ByteArrayInputStream(input.getBytes)
        Console.withIn(in) {
        val controller = new Controller(scrabbleField)
        val tui = new TUI(controller)
        tui.dictionaryAddWords should not be null
        }
      }


    "processInputLine is called" should{
      "modify the state of the Controller when valid input is given" in {

        val input = "1\nhui\nWORD B 1 H\nstop\n"
        val in = new ByteArrayInputStream(input.getBytes)
        var hui: TUI = null
        Console.withIn(in) {
        val controller = new Controller(scrabbleField)
        hui = new TUI(controller)
        val tui = hui.inputNamesAndCreateList(hui.numberOfPlayers())
        val testfield: ScrabbleField = scrabbleField.placeWord(1, 0, 'V', "WORD")
        }
        hui.controller.field shouldEqual scrabbleField
      }
    }

      "not modify the state of the Controller when invalid input is given" in {
        val input = "1\nhui\nmeme\nstop\n"
        val in = new ByteArrayInputStream(input.getBytes)
        var hui: TUI = null
        Console.withIn(in) {
          val controller = new Controller(scrabbleField)
          hui = new TUI(controller)
          val tui = hui.inputNamesAndCreateList(hui.numberOfPlayers())
          val testfield: ScrabbleField = scrabbleField.placeWord(1, 0, 'V', "WORD")
        }
        hui.controller.field shouldEqual scrabbleField
      }
      "returning the TUI instance exit when the input is exit" in {
        val input = "1\nhui\nexit\n"
        val in = new ByteArrayInputStream(input.getBytes)
        var hui: TUI = null
        Console.withIn(in) {
          val controller = new Controller(scrabbleField)
          hui = new TUI(controller)
          val tui = hui.inputNamesAndCreateList(hui.numberOfPlayers())
          hui = hui.processInputLine(new Player("hui", 0))
        }
        hui should not equal("exit")
        }
    }


    "do nothing when the coordinates are not valid" in {
      val input = "1\nhui\nword A 16 V\nexit"
      val in = new ByteArrayInputStream(input.getBytes)
      var hui: TUI = null
      val scrabbleField = new ScrabbleField(15, english)
      Console.withIn(in) {
        val controller = new Controller(scrabbleField)
        hui = new TUI(controller)
        val tui = hui.inputNamesAndCreateList(hui.numberOfPlayers())
        hui = hui.processInputLine(new Player("hui", 0))
      }
      hui.controller.field shouldEqual scrabbleField
    }



    "do nothing when the direction i not valid" in {
      val input = "1\nhui\nword A 16 I\nexit"
      val in = new ByteArrayInputStream(input.getBytes)
      var hui: TUI = null
      val scrabbleField = new ScrabbleField(15, english)
      Console.withIn(in) {
        val controller = new Controller(scrabbleField)
        hui = new TUI(controller)
        val tui = hui.inputNamesAndCreateList(hui.numberOfPlayers())
        hui = hui.processInputLine(new Player("hui", 0))
      }
      hui.controller.field shouldEqual scrabbleField
    }

    "do nothing when the word is not in the dictionary" in {
      val input = "1\nhui\nRAMBAZAMBA A 16 V\nexit"
      val in = new ByteArrayInputStream(input.getBytes)
      var hui: TUI = null
      val scrabbleField = new ScrabbleField(15, english)
      Console.withIn(in) {
        val controller = new Controller(scrabbleField)
        hui = new TUI(controller)
        val tui = hui.inputNamesAndCreateList(hui.numberOfPlayers())
        hui = hui.processInputLine(new Player("hui", 0))
      }
      hui.controller.field shouldEqual scrabbleField
    }

    "translateCoordinate is called" should {
      "return correct coordinates for valid input" in {
        val input = "english\n"
        val in = new ByteArrayInputStream(input.getBytes)
        Console.withIn(in) {
        val controller = new Controller(scrabbleField)
        val tui = new TUI(controller)

        tui.translateCoordinate("A 1") should equal((0, 1))
        tui.translateCoordinate("B 2") should equal((1, 2))
        tui.translateCoordinate("Z 15") should equal((25, 15))
        }
      }

      "throw a NumberFormatException for invalid input" in {
        val input = "english\n"
        val in = new ByteArrayInputStream(input.getBytes)
        Console.withIn(in) {
        val controller = new Controller(scrabbleField)
        val tui = new TUI(controller)
        an [NumberFormatException] should be thrownBy tui.translateCoordinate("A B")
        }
      }
    }

    "validCoordinateInput is called" should {
      "return true for valid coordinates" in {
        val input = "english\n"
        val in = new ByteArrayInputStream(input.getBytes)
        Console.withIn(in) {
        val controller = new Controller(scrabbleField)
        val tui = new TUI(controller)
        tui.validCoordinateInput("A", "1") shouldBe true
        tui.validCoordinateInput("Z", "15") shouldBe true
        }
      }

      "return false for invalid coordinates" in {
        val input = "english\n"
        val in = new ByteArrayInputStream(input.getBytes)
        Console.withIn(in) {
        val controller = new Controller(scrabbleField)
        val tui = new TUI(controller)
        tui.validCoordinateInput("AA3", "1") shouldBe false
        tui.validCoordinateInput("A", "1A") shouldBe false
        tui.validCoordinateInput("1", "A") shouldBe false
        tui.validCoordinateInput("A", "A") shouldBe false
        }
      }

      "The equals method should return true if the controller fields are equal" in {
        val input = "english\nenglish\n"
        val in = new ByteArrayInputStream(input.getBytes)
        Console.withIn(in) {
        val controller = new Controller(scrabbleField)
        val tui = new TUI(controller)
        val tui2 = new TUI(controller)
        tui.equals(tui2) shouldBe true
        }
      }
      "and just return false if the types are different" in {
        val input = "english\n"
        val in = new ByteArrayInputStream(input.getBytes)
        Console.withIn(in) {
        val controller = new Controller(scrabbleField)
        val tui = new TUI(controller)
        tui.equals("test") shouldBe false
        }
      }
    }
  
}