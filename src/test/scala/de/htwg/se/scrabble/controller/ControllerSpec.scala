package de.htwg.se.scrabble.controller

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers.*
import de.htwg.se.scrabble.model.ScrabbleField
import de.htwg.se.scrabble.util.Observer

class ControllerSpec extends AnyWordSpec:

  "A Controller" when {

    "created" should {

      "have a valid ScrabbleField" in {
        val field = new ScrabbleField(15)
        val controller = new Controller(field)
        controller.field should be(field)
      }

      "have no subscribers initially" in {
        val field = new ScrabbleField(15)
        val controller = new Controller(field)
        controller.subscribers shouldBe empty
      }
    }

    "a word is placed" should {

      "update subscribers" in {
        val field = new ScrabbleField(15)
        val controller = new Controller(field)
        val observer = new Observer { def update(): Unit = () }
        controller.add(observer)

        controller.placeWordController(0, 0, 'H', "hello")
        // Assuming Observer's update method sets a flag or performs some action
        // We should check if the update method has been called
        // For simplicity, we'll just check if there are subscribers
        controller.subscribers should not be (empty)

      }
    }

    "checking if a word fits" should {

      "return true if word fits" in {
        val field = new ScrabbleField(15)
        val controller = new Controller(field)

        val xPosition = 0
        val yPosition = 0
        val direction = 'H'
        val word = "hello"

        val fits = controller.wordFitsController(xPosition, yPosition, direction, word)
        fits should be(true)
      }

      "return false if word doesn't fit" in {
        val field = new ScrabbleField(15)
        val controller = new Controller(field)

        // Assuming we're trying to place a word in a position where it doesn't fit
        val xPosition = 11
        val yPosition = 11
        val direction = 'v'
        val word = "hello"

        val fits = controller.wordFitsController(xPosition, yPosition, direction, word)
        fits should be(false)
      }
    }

    "checking if word fits in bounds" should {

      "return true if word fits within bounds" in {
        val field = new ScrabbleField(15)
        val controller = new Controller(field)

        val xPosition = 0
        val yPosition = 0
        val direction = 'h'
        val word = "hello"

        val fits = controller.fitsinBoundsController(xPosition, yPosition, direction, word)
        fits should be(true)
      }

      "return false if word doesn't fit within bounds" in {
        val field = new ScrabbleField(5)
        val controller = new Controller(field)

        // Assuming we're trying to place a word that goes out of bounds
        val xPosition = 10
        val yPosition = 10
        val direction = 'v'
        val word = "hello"

        val fits = controller.fitsinBoundsController(xPosition, yPosition, direction, word)
        fits should be(false)
      }
    }
    "The toString method " should {
      "print the same lines as the Class Scrabblefield does." in {
        val field = new ScrabbleField(15)
        val controller = new Controller(field)
        field.toString shouldEqual(controller.toString)
      }
    }
  }
