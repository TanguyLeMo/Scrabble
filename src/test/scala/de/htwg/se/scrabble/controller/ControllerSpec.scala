package de.htwg.se.scrabble
package controller

import controller.Controller
import model.ScrabbleField
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._

class ControllerSpec extends AnyWordSpec {
  "A Controller" when {
    val scrabbleField = new ScrabbleField(15)
    val controller = new Controller(scrabbleField)

    "placeWord method is called" should {
      "place the word on the ScrabbleField" in {
      // TO BE DONE
      }
    }

    "wordFits method is called" should {
      "check if the word fits in the specified position and direction" in {
        controller.wordFits(3, 3, 'H', "HELLO")
        // Assert expected behavior here
      }
    }

    "fitsinBounds method is called" should {
      "check if the word fits within the bounds of the ScrabbleField" in {
        controller.fitsinBounds(3, 3, 'H', "HELLO")
        // Assert expected behavior here
      }
    }
  }
}
