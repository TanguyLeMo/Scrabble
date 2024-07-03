
package de.htwg.se.scrabble.controller
import de.htwg.se.scrabble.controller.ControllerComponent.ControllerBaseImpl.Controller
import de.htwg.se.scrabble.controller.ControllerComponent.ControllerInterface
import de.htwg.se.scrabble.model.gameComponent.ScrabbleFieldInterface
import de.htwg.se.scrabble.model.gameComponent.gameComponentBaseImpl.{ScrabbleField, Stone}
import de.htwg.se.scrabble.model.gameState.GameStateInterface
import de.htwg.se.scrabble.util.placeWordsAsMove
import org.scalatest.matchers.must.Matchers
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers.*

import scala.List.*
import javax.annotation.meta.When
import scala.List
import scala.language.postfixOps
import org.scalatestplus.mockito.MockitoSugar
import org.scalatestplus.mockito.MockitoSugar.mock
import de.htwg.se.scrabble.util.*
import org.mockito.Mockito.{verify, when}

class ControllerSpec extends AnyWordSpec with Matchers:
  "A Controller" when {
    val scrabbleField : ScrabbleFieldInterface = ScrabbleField(15)
    "A Controller" should {

      "doAndPublish with a move correctly" in {

        val mockField = mock[ScrabbleFieldInterface]
        val mockNewField = mock[ScrabbleFieldInterface]
        val mockFileIO = mock[GameStateInterface]
        val mockUndoManager = mock[UndoManager[ScrabbleFieldInterface]]
        val controller = new Controller(mockField) {
          override val fileIO: GameStateInterface = mockFileIO
          override val undoManager: UndoManager[ScrabbleFieldInterface] = mockUndoManager
        }
        val move = new placeWordsAsMove( 0, 0, 'H',"word")
        when(mockUndoManager.doStep(mockField, mockNewField)).thenReturn(mockNewField)

        controller.doAndPublish(_ => mockNewField, move)

        verify(mockUndoManager).doStep(mockField, mockNewField)
        controller.field should be(mockNewField)
      }

      "doAndPublish without a move correctly" in {
        val mockField = mock[ScrabbleFieldInterface]
        val mockNewField = mock[ScrabbleFieldInterface]
        val mockFileIO = mock[GameStateInterface]
        val controller = new Controller(mockField) {
          override val fileIO: GameStateInterface = mockFileIO
        }

        controller.doAndPublish(mockNewField)

        controller.field should be(mockNewField)
      }

      "save the game state correctly" in {
        val mockField = mock[ScrabbleFieldInterface]
        val mockFileIO = mock[GameStateInterface]
        val controller = new Controller(mockField) {
          override val fileIO: GameStateInterface = mockFileIO
        }
        when(mockFileIO.save(mockField)).thenReturn(true)

        controller.save should be(true)
        verify(mockFileIO).save(mockField)
      }

      "change language correctly" in {
        val mockField = mock[ScrabbleFieldInterface]
        val mockNewField = mock[ScrabbleFieldInterface]
        val mockFileIO = mock[GameStateInterface]
        val controller = new Controller(mockField) {
          override val fileIO: GameStateInterface = mockFileIO
        }
        when(mockField.setLanguageDictionary(LanguageEnum.FRENCH)).thenReturn(mockNewField)

        controller.changeLanguage(LanguageEnum.FRENCH) should be(mockNewField)
        controller.field should be(mockNewField)
        verify(mockField).setLanguageDictionary(LanguageEnum.FRENCH)
      }
      "DrawRandomStone" in {
        val controller: Controller = new Controller(scrabbleField)
        controller.drawRandomStone(controller.field.stoneContainer) should not be null
      }

      val controller: Controller = new Controller(scrabbleField)
      "have a empty field" in {
        controller.field shouldEqual scrabbleField
      }

      "removeStoneFromContainer" in {
        controller.removeStonefromContainer(new Stone('A'), controller.field.stoneContainer) should not be null
      }
      "wordfits" in {
        controller.wordFits(7, 7, 'V', "A") should be(true)
      }
      "have a empty dictionary" in {
        controller.field.dictionary should not equal(Set())
      }
      "undo" in {
        controller.field.matrix.placeWord(7, 7, 'V', "A")
        controller.undo
        controller.field.matrix.field(7)(7).letter.symbol should be('_')
      }
      "redo" in{
        controller.field = controller.field.placeWord(7, 7, 'V', "A")
        controller.undo
        controller.redo
        controller.field.matrix.field(7)(7).letter.symbol should be('A')
      }
      "toString" in {
        controller.toString should be(controller.field.toString)
      }
      "save" in {
        controller.save
      }
      "load" in {
        controller.load
      }
      "contains" in {
        controller.contains("hello") should be(true)
      }
      "add" in {
        controller.add("hello")
        controller.contains("hello") should be(true)
      }
      "createPlayerlist" in {
        controller.CreatePlayersList(Vector("Player1", "Player2"))
        controller.field.players.length should be(2)
      }
      "sortlistAfterPoints" in {
        controller.CreatePlayersList(Vector("Player1", "Player2"))
        controller.sortListAfterPoints(controller.field.players) shouldEqual controller.field.player.sortListAfterPoints(controller.field.players)
      }
      "lettersAlreadyThere" in {
        controller.lettersAlreadyThere(7, 7, 'V', "A") should not be(List())
      }
      "setLanguageDictionary" in {
        controller.setLanguageDictionary(de.htwg.se.scrabble.util.LanguageEnum.ENGLISH)
        controller.field.languageEnum should be(de.htwg.se.scrabble.util.LanguageEnum.ENGLISH)
      }
      "collectPoints" in {
        controller.placeWord(7, 7, 'V', "A")
        controller.collectPoints(controller.field.matrix, 7, 7, 'V', "A") should be(2)
      }
      "hasStones" in {
        controller.hasStones(List(), "A", controller.field.players(0)) should be(false)
      }
      "languageContext" in {
        controller.languageContext should not be(null)
      }
      "addPoints" in {
        controller.CreatePlayersList(Vector("Player1", "Player2"))
        controller.AddPoints(2, controller.field.players(0), controller.field.players) should be(controller.field.players)
      }
      "nextTurn" in {
        controller.CreatePlayersList(Vector("Player1", "Player2"))
        controller.nextTurn(controller.field.players, controller.field.players(0)) should be(controller.field.players(1))
      }
      "addStones" in {
        controller.CreatePlayersList(Vector("Player1", "Player2"))
        controller.addStones(controller.field.players(0), List()) should be(controller.field.players)
      }
      "removeStones" in {
        controller.CreatePlayersList(Vector("Player1", "Player2"))
        controller.removeStones(controller.field.players(0), controller.field.players, List()) should be(controller.field.players)
      }
      "OnlyRequiredStones" in {
        controller.OnlyRequiredStones(List(), "A") should not be List()
      }
      "gamestartPlayStones" in {
        controller.gamestartPlayStones(de.htwg.se.scrabble.util.LanguageEnum.ENGLISH) should not be List()
      }
      "requestLanguage" in {
        controller.requestLanguage should not be null
      }
      "noSuchLanguage" in {
        controller.noSuchLanguage should not be null
      }
      "displayLeaderBoard" in {
        controller.displayLeaderBoard should not be null
      }
      "requestnewWord" in {
        controller.requestnewWord should not be null
      }
      "wordAlreadyAddedToDictionarycontroller" in {
        controller.wordAlreadyAddedToDictionarycontroller should not be null
      }
      "wordAddedToDictionarycontroller" in {
        controller.wordAddedToDictionarycontroller should not be null
      }
      "invalidcoordinatescontroller" in {
        controller.invalidcoordinatescontroller should not be null
      }
      "invalidInputcontroller" in {
        controller.invalidInputcontroller should not be null
      }
      "notInDictionarycontroller" in {
        controller.notInDictionarycontroller should not be null
      }
      "wordNotInDictionarycontroller" in {
        controller.wordNotInDictionarycontroller should not be null
      }
      "enterWordforDictionarycontroller" in {
        controller.enterWordforDictionarycontroller should not be null
      }
      "stopcontroller" in {
        controller.stopcontroller should not be null
      }
      "languageSettingcontroller" in {
        controller.languageSettingcontroller should not be null
      }
      "enterWordcontroller" in {
        controller.enterWordcontroller should not be null
      }
      "NoCorrectDirectioncontroller" in {
        controller.NoCorrectDirectioncontroller should not be null
      }
      "wordDoesntFitcontroller" in {
        controller.wordDoesntFitcontroller should not be null
      }
      "exitcontroller" in {
        controller.exitcontroller should not be null
      }
      "nameAlreadyTakencontroller" in {
        controller.nameAlreadyTakencontroller should not be null
      }
      "enterNumberofPlayerscontroller" in {
        controller.enterNumberofPlayerscontroller should not be null
      }
      "invalidNumbercontroller" in {
        controller.invalidNumbercontroller should not be null
      }
      "currentPlayercontroller" in {
        controller.currentPlayercontroller should not be null
      }
      "enterPlayerNamecontroller" in {
        controller.enterPlayerNamecontroller should not be null
      }
      "nameCantBeEmptycontroller" in {
        controller.nameCantBeEmptycontroller should not be null
      }
      "noteEnoughStonescontroller" in {
        controller.noteEnoughStonescontroller should not be null
      }
      "leaderBoardcontroller" in {
        controller.leaderBoardcontroller should not be null
      }
      "translateCoordinate" in {
        controller.translateCoordinate("A 0") should be(0, 0)
      }
      "changeLanguage" in {
        controller.changeLanguage(de.htwg.se.scrabble.util.LanguageEnum.ENGLISH) should not be null
      }
      "leaderboardController" in {
        controller.leaderBoardcontroller should not be null
      }
    }

  }
