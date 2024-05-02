package de.htwg.se.scrabble
package aview

import org.scalatest.wordspec.AnyWordSpec
import de.htwg.se.scrabble.controller.Controller
import de.htwg.se.scrabble.model.ScrabbleField
import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito
import org.mockito.Mockito.*
import org.scalatest.matchers.must.Matchers.*
import org.scalatest.matchers.should.*
import org.scalatest.matchers.should.Matchers.should

class TUISpec extends AnyWordSpec{
  "A TUI" when {
    val scrabbleField = new ScrabbleField(15)
    val controller = new Controller(scrabbleField)
    val tui = new TUI(controller)

    val tuiMock = mock(classOf[TUI])

    when(tuiMock.getInputAndPrintLoop(any[String])).thenAnswer(invocation => {
      val input: String = invocation.getArgument(0)
      val inputVector = input.split(" ")

      tuiMock.validCoordinateInput(inputVector(1), inputVector(2))
      tuiMock.translateCoordinate(inputVector(1) + " " + inputVector(2))
    })

      when(tuiMock.translateCoordinate(any[String])).thenAnswer(invocation => {
        val input: String = invocation.getArgument(0)
        val inputVector = input.split(" ")

        if (inputVector(0).matches("[A-Z]") && inputVector(1).matches("[0-9]+")) {
          val x = inputVector(0).charAt(0) - 'A'
          val y = inputVector(1).toInt
          (x, y)
        } else {
          (-1, -1)
        }
      })

      when(tuiMock.validCoordinateInput(any[String], any[String])).thenAnswer(invocation => {
        val x: String = invocation.getArgument(0)
        val y: String = invocation.getArgument(1)

        if (x.matches("[A-Z]") && y.matches("[0-9]+")) {
          true
        } else {
          false
        }
      })

    "is being run it with expected values it " should {


      "Call mentioned functions a correct amount of times" in {
        val input = "myWord 0 A H"
        val inputVector = input.split(" ")

        tuiMock.getInputAndPrintLoop(input)

        verify(tuiMock, atMostOnce()).validCoordinateInput(inputVector(1), inputVector(2))
        verify(tuiMock, atMostOnce()).translateCoordinate(inputVector(1) + " " + inputVector(2))
      }
    }
    val input = "exit"
     "Stop running when input is invalid" in {
      val input = "myWord A 0 H"
      val inputVector = input.split(" ")

      tuiMock.getInputAndPrintLoop(input)

      verify(tuiMock, times(1)).validCoordinateInput(inputVector(1), inputVector(2))
      verify(tuiMock, times(1)).translateCoordinate(inputVector(1) + " " + inputVector(2))

    }
    "run the application appropriately and the meantime check the mock for correctness" in {
      assert(tuiMock.update().equals(new TUI(new Controller(new ScrabbleField(0))).update() ))
    }


    "translateCoordinate is called" should {
      "return correct coordinates for valid input" in {
        assert(tui.translateCoordinate("A 1") == (0, 1))
        assert(tui.translateCoordinate("B 2") == (1, 2))
        assert(tui.translateCoordinate("Z 15") == (25, 15))
      }
    }

    "validCoordinateInput is called" should {
      "return true for valid coordinates" in {
        assert(tui.validCoordinateInput("A", "1"))
        assert(tui.validCoordinateInput("Z", "15"))
      }

      "return false for invalid coordinates" in {
        assert(tui.validCoordinateInput("AA", "1"))
        assert(tui.validCoordinateInput("A", "16"))
        assert(!tui.validCoordinateInput("1", "A"))
        assert(!tui.validCoordinateInput("A", "A"))
      }
    }
  }
}