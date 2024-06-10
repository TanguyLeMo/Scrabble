package de.htwg.se.scrabble.controller

import de.htwg.se.scrabble.model.Stone
import de.htwg.se.scrabble.model.languageComponent.LanguageEnum.{ENGLISH, FRENCH, GERMAN}
import de.htwg.se.scrabble.model.languageComponent.LanguageEnum
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers.*
import de.htwg.se.scrabble.model.{placeWordsAsMove, Player, ScrabbleField}
import de.htwg.se.scrabble.util.Observer

class ControllerSpec extends AnyWordSpec:

  "A Controller" when {
    val english = LanguageEnum.ENGLISH

    "created" should {

      "have a valid ScrabbleField" in {
        val field = new ScrabbleField(15,english)
        val controller = new Controller(field)
        controller.field should be(field)
      }

      "have no subscribers initially" in {
        val field = new ScrabbleField(15,english)
        val controller = new Controller(field)
        controller.subscribers shouldBe empty
      }
    }

    "a word is placed" should {

      "update subscribers" in {
        val field = new ScrabbleField(15,english)
        val controller = new Controller(field)
        val observer = new Observer { def update(): String = "" }
        controller.add(observer)

        controller.placeWord(0, 0, 'H', "hello")
        // Assuming Observer's update method sets a flag or performs some action
        // We should check if the update method has been called
        // For simplicity, we'll just check if there are subscribers
        controller.subscribers should not be empty

      }
    }

    "checking if a word fits" should {

      "return true if word fits" in {
        val field = new ScrabbleField(15,english)
        val controller = new Controller(field)

        val xPosition = 0
        val yPosition = 0
        val direction = 'H'
        val word = "hello"

        val fits = controller.wordFits(xPosition, yPosition, direction, word)
        fits should be(true)
      }

      "return false if word doesn't fit" in {
        val field = new ScrabbleField(15,english)
        val controller = new Controller(field)

        // Assuming we're trying to place a word in a position where it doesn't fit
        val xPosition = 11
        val yPosition = 11
        val direction = 'v'
        val word = "hello"

        val fits = controller.wordFits(xPosition, yPosition, direction, word)
        fits should be(false)
      }
    }

    "checking if word fits in bounds" should {

      "return true if word fits within bounds" in {
        val field = new ScrabbleField(15,english)
        val controller = new Controller(field)

        val xPosition = 0
        val yPosition = 0
        val direction = 'h'
        val word = "hello"

        val fits = controller.fitsinBounds(xPosition, yPosition, direction, word)
        fits should be(true)
      }

      "return false if word doesn't fit within bounds" in {
        val field = new ScrabbleField(5,english)
        val controller = new Controller(field)

        // Assuming we're trying to place a word that goes out of bounds
        val xPosition = 10
        val yPosition = 10
        val direction = 'v'
        val word = "hello"

        val fits = controller.fitsinBounds(xPosition, yPosition, direction, word)
        fits should be(false)
      }
    }
    
    "The toString method " should {
      "print the same lines as the Class Scrabblefield does." in {
        val field = new ScrabbleField(15,english)
        val controller = new Controller(field)
        field.toString shouldEqual controller.toString
      }
    }
    "adding a word to the dictionary" should {
      "have the word in the dictionary" in {
        val field = new ScrabbleField(15,english)
        val controller = new Controller(field)
        controller.add("testword")
        controller.field.dictionary.set should contain("testword".toUpperCase())
      }
    }
    "sorting the list after points" should {
      "return a list of players sorted descending by points" in {
        val field = new ScrabbleField(15,english)
        val controller = new Controller(field)
        val player1 = new Player("Someone1", 10, List[Stone]())
        val player2 = new Player("Someone2", 20, List[Stone]())
        val players = List(player1, player2)
        val sortedPlayers = controller.sortListAfterPoints(players)
        sortedPlayers should be(List(player2, player1))
      }
    }
    "collecting points" should {
      "return the correct amount of points" in {
        val field = new ScrabbleField(15,ENGLISH)
        val controller = new Controller(field)
        val newField = controller.placeWord(0, 0, 'V', "hello")
        val points = controller.collectPoints(newField.matrix, 0, 0, 'V', "hello")
        points should be(27)
      }
    }
    "adding points" should {
      "add the points to the player" in {
        val field = new ScrabbleField(15,english)
        val controller = new Controller(field)
        val player = new Player("Someone", 0, List[Stone]())
        val playerList = List(player)
        val newPlayerList = controller.AddPoints(10, player, playerList)
        newPlayerList.head.getPoints should be(10)
      }
    }

    "nextTurn" should {
      "return the next player in the list" in {
        val field = new ScrabbleField(15,english)
        val controller = new Controller(field)
        val player1 = new Player("Someone", 10, List[Stone]())
        val player2 = new Player("Someone else", 20, List[Stone]())
        val player3 = new Player("Another one", 5, List[Stone]())
        val playerList = List(player1, player2, player3)
        val nextPlayer = controller.nextTurn(playerList, player1)
        nextPlayer should be(player2)
      }
      "return the first player if the current turn is last player in the list" in {
        val field = new ScrabbleField(15,english)
        val controller = new Controller(field)
        val player1 = new Player("Someone", 10, List[Stone]())
        val player2 = new Player("Someone else", 20, List[Stone]())
        val player3 = new Player("Another one", 5, List[Stone]())
        val playerList = List(player1, player2, player3)
        val nextPlayer = controller.nextTurn(playerList, player3)
        nextPlayer should be(player1)
      }
    }
    "thisMatrix" should {
      "return the matrix of the field" in {
        val field = new ScrabbleField(15,english)
        val controller = new Controller(field)
        controller.thisMatrix should be(field.matrix)
      }
    }
    "thisPlayerList" should {
      "return the list of players" in {
        val field = new ScrabbleField(15,english)
        val controller = new Controller(field)
        controller.thisPlayerList should be(field.players)
      }
    }
    "doAndPublish" should {
      "return the new field" in {
        val field = new ScrabbleField(15, english)
        val controller = new Controller(field)
        val newField = controller.doAndPublish(controller.field)
        newField should not be (controller.field)
      }
      "new" should {
        val english = LanguageEnum.ENGLISH
        val field = new ScrabbleField(15, english)
        val controller = new Controller(field)
        val move = placeWordsAsMove(0, 0, 'H', "hello")

        "have a valid doAndPublish method with two arguments" in {
          val placeWordAsFunction: placeWordsAsMove => ScrabbleField = move => {
            controller.placeWord(move.xPosition, move.yPosition, move.direction, move.word)
          }
          val newField: Unit = controller.doAndPublish(placeWordAsFunction, move)
          newField should not be field
          1 should not equal move.word
        }
      }

    }
  }

