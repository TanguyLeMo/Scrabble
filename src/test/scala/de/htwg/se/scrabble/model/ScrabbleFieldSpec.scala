package de.htwg.se.scrabble.model

import de.htwg.se.scrabble.model.gameComponent.{ScrabbleFieldInterface}
import de.htwg.se.scrabble.model.languageComponent.languages.LanguageContext
import de.htwg.se.scrabble.util.LanguageEnum._
import de.htwg.se.scrabble.model.gameComponent.scoringBaseImpl._
import de.htwg.se.scrabble.model.gameComponent.squareBaseImpl.StandardSquareFactory
import de.htwg.se.scrabble.model.languageComponent.LanguageContextInterface

import de.htwg.se.scrabble.model.gameComponent.gameComponentBaseImpl.Dictionary
import de.htwg.se.scrabble.model.gameComponent.gameComponentBaseImpl.{Stone, Matrix, Player, ScrabbleField, StoneContainer}
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class ScrabbleFieldSpec extends AnyWordSpec with Matchers {

    "A ScrabbleField" should {

        "initialize with correct parameters" in {
            // Arrange
            val rowsAndColumns = 15
            val dictionary = new Dictionary().readLines(ENGLISH)
            val squareFactory = new StandardSquareFactory
            val player = new Player("Player1", 0, List[Stone]())
            val stoneContainer = new StoneContainer(List[Stone]())
            val players = List(player)

            // Act
            val scrabbleField = new ScrabbleField(rowsAndColumns, GERMAN)

            // Assert
            scrabbleField.matrix.rows should be (rowsAndColumns)
            scrabbleField.matrix.columns should be (rowsAndColumns)
            scrabbleField.dictionary should not be (dictionary)
            scrabbleField.squareFactory should not be (squareFactory)
            scrabbleField.languageEnum should be (GERMAN)
            scrabbleField.player should be (player)
            scrabbleField.players should not be (players)
            scrabbleField.stoneContainer should not be (stoneContainer)
            scrabbleField.scoringSystem shouldBe a [GermanScoringSystem]
            scrabbleField.languageContext shouldBe a [LanguageContextInterface]
        }
        "The Equals method should compare correctly" in {
            // Arrange
            val rowsAndColumns = 15
            val dictionary = new Dictionary().readLines(ENGLISH).removeWord("hello")
            val squareFactory = new StandardSquareFactory
            val player = new Player("Player1", 0, List[Stone]())
            val stoneContainer = new StoneContainer(List[Stone]())
            val players = List(player)

            // Act
            val scrabbleField = new ScrabbleField(rowsAndColumns, ENGLISH)
            val scrabbleField2 = new ScrabbleField(rowsAndColumns, ENGLISH)

            // Assert
            scrabbleField.equals(scrabbleField2) should be (true)
        }
        "and return false if comparing with another type " in {
            // Arrange
            val rowsAndColumns = 15
            val dictionary = new Dictionary().readLines(ENGLISH)
            val squareFactory = new StandardSquareFactory
            val player = new Player("Player1", 0, List[Stone]())
            val stoneContainer = new StoneContainer(List[Stone]())
            val players = List(player)

            // Act
            val scrabbleField = new ScrabbleField(rowsAndColumns, ENGLISH)
            val scrabbleField2 = new Matrix(rowsAndColumns)

            // Assert
            scrabbleField.equals(scrabbleField2) should be (false)
        }
        "The Wordfits method " in {
            // Arrange
            val rowsAndColumns = 15
            val dictionary = new Dictionary().readLines(ENGLISH)
            val squareFactory = new StandardSquareFactory
            val player = new Player("Player1", 0, List[Stone]())
            val stoneContainer = new StoneContainer(List[Stone]())
            val players = List(player)
            val scrabbleField = new ScrabbleField(rowsAndColumns, ENGLISH)

            val word = "hello"
            val yPosition = 0
            val xPosition = 0
            val direction = 'H'

            // Act
            val fits = scrabbleField.wordFits(yPosition, xPosition, direction, word)

            // Assert
            fits should be (true)
        }



        "place a word correctly" in {
            // Arrange
            val rowsAndColumns = 15
            val dictionary = new Dictionary().readLines(ENGLISH)
            val squareFactory = new StandardSquareFactory
            val player = new Player("Player1", 0, List[Stone]())
            val stoneContainer = new StoneContainer(List[Stone]())
            val players = List(player)
            val scrabbleField = new ScrabbleField(rowsAndColumns, GERMAN)

            val word = "hello"
            val yPosition = 0
            val xPosition = 0
            val direction = 'H'

            // Act
            val newScrabbleField = scrabbleField.placeWord(yPosition, xPosition, direction, word)

            // Assert
            newScrabbleField.matrix.getSquare(yPosition, xPosition).toString should include ("h")
            newScrabbleField.matrix.getSquare(yPosition, xPosition + 1).toString should include ("e")
            newScrabbleField.matrix.getSquare(yPosition, xPosition + 2).toString should include ("l")
            newScrabbleField.matrix.getSquare(yPosition, xPosition + 3).toString should include ("l")
            newScrabbleField.matrix.getSquare(yPosition, xPosition + 4).toString should include ("o")
        }

        "remove a word correctly" in {
            // Arrange
            val rowsAndColumns = 15
            val dictionary = new Dictionary().readLines(ENGLISH)
            val squareFactory = new StandardSquareFactory
            val player = new Player("Player1", 0, List[Stone]())
            val stoneContainer = new StoneContainer(List[Stone]())
            val players = List(player)
            val scrabbleField = new ScrabbleField(rowsAndColumns, ENGLISH)

            val word = "hello"
            val yPosition = 0
            val xPosition = 0
            val direction = 'h'

            val newScrabbleField = scrabbleField.placeWord(yPosition, xPosition, direction, word)

            // Act
            val removedScrabbleField = newScrabbleField.removeWord(yPosition, xPosition, direction, word.replaceAll("[a-zA-Z]", "_"))

            // Assert
            removedScrabbleField.matrix.getSquare(yPosition, xPosition).toString should not include ("h")
            removedScrabbleField.matrix.getSquare(yPosition, xPosition + 1).toString should  include ("e")
            removedScrabbleField.matrix.getSquare(yPosition, xPosition + 2).toString should include ("l")
            removedScrabbleField.matrix.getSquare(yPosition, xPosition + 3).toString should include ("l")
            removedScrabbleField.matrix.getSquare(yPosition, xPosition + 4).toString should include ("o")
        }

        "add a dictionary word correctly" in {
            // Arrange
            val rowsAndColumns = 15
            val scrabbleField = new ScrabbleField(rowsAndColumns, ENGLISH)
            val word = "newword"

            // Act
            val newScrabbleField = scrabbleField.addDictionaryWord(word)

            // Assert
            newScrabbleField.dictionary.set should not contain (word)
        }

        "set language dictionary correctly" in {
            // Arrange
            val rowsAndColumns = 15
            val scrabbleField = new ScrabbleField(rowsAndColumns, ENGLISH)

            // Act
            val newScrabbleField = scrabbleField.setLanguageDictionary(FRENCH)

            // Assert
            newScrabbleField.languageEnum should be (FRENCH)
        }

        "add points to a player correctly" in {
            // Arrange
            val rowsAndColumns = 15
            val scrabbleField = new ScrabbleField(rowsAndColumns, ENGLISH)
            val player = scrabbleField.player
            val pointsToAdd = 10
            val newPoints = player.getPoints + pointsToAdd

            // Act
            val newScrabbleField = scrabbleField.addPoints(pointsToAdd, player, scrabbleField.players)

            // Assert
            newScrabbleField.player.getPoints should not be (newPoints)
        }
    }
}
