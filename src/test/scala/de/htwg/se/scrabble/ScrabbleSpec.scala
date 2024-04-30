package de.htwg.se.scrabble

import de.htwg.se.scrabble.controller.Controller
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers.*
import de.htwg.se.scrabble.model.*


class ScrabbleSpec extends AnyWordSpec {
  "A ScrabbleField" when {
    val scrabbleField = new ScrabbleField(15)
    "created " should{
      "have following attributes with following values: " in  {
        val compare = new ScrabbleField(15).matrix
        scrabbleField.matrix shouldEqual(compare)
      }
    }
    "created with size 15" should {
      "have correct size" in {
        val field = new ScrabbleField(15)
        field.matrix.rows shouldEqual 15
      }

      "allow placing stones" in {
        val field = new ScrabbleField(15)
        val stone = new Stone('A')
        field.placeWord(3, 3, 'H', "DELULU") // Placing stone in the center for example
        field.matrix shouldEqual new Matrix(15)
      }
    }
  }

  "A Controller" when {
    "initialized with a ScrabbleField" should {
      "have access to the field" in {
        val field = new ScrabbleField(15)
        val controller = Controller(field)
        controller.field shouldEqual field
      }

      "be able to place stones on the field" in {
        val field = new ScrabbleField(15)
        val controller = Controller(field)
        controller.placeWordController(3, 3, 'H', "DELULU") // Placing stone in the center for example
        controller.field.matrix.field(3)(3) shouldEqual new Stone('D')
      }

      "reject placing stones on occupied positions" in {
        val field = new ScrabbleField(15)
        val controller = Controller(field)
        controller.placeWordController(3, 3, 'H', "DELULU") // Placing stone in the center for example
        val result = controller.wordFitsController(3, 3, 'H', "bEbUbU")
        result shouldEqual false
      }
    }
  }
}

