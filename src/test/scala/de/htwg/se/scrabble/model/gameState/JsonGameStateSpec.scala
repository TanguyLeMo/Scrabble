package de.htwg.se.scrabble.model.gameState

import de.htwg.se.scrabble.model.gameComponent.*
import de.htwg.se.scrabble.model.gameComponent.gameComponentBaseImpl.*
import de.htwg.se.scrabble.model.gameComponent.squareBaseImpl.*
import de.htwg.se.scrabble.model.gameState.GameStateBaseImpl.JsonGameState
import de.htwg.se.scrabble.util.LanguageEnum
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import play.api.libs.json.*

import java.io.{File, PrintWriter}
import scala.io.Source

class JsonGameStateSpec extends AnyWordSpec with Matchers {

  "A JsonGameState" should {
    val jsonGameState = new JsonGameState

    val matrixSize = 15
    val matrix = new Matrix(matrixSize).init()
    val players = List(
      new Player("Alice", 10, List(new Stone('A'), new Stone('B'))),
      new Player("Bob", 20, List(new Stone('C'), new Stone('D')))
    )
    val currentPlayer = new Player("Alice", 10, List(new Stone('A'), new Stone('B')))
    val stoneContainer = new StoneContainer(List(new Stone('E'), new Stone('F')))
    val language = LanguageEnum.ENGLISH
    val scrabbleField = new ScrabbleField(matrix, new Dictionary().readLines(language), new StandardSquareFactory, language, currentPlayer, players, stoneContainer)

    "create a directory if it does not exist" in {
      jsonGameState.createDirectory("testDir")
      val dir = new File("testDir")
      dir.exists() should be(true)
      dir.isDirectory should be(true)
      dir.delete()
    }

    "save a ScrabbleField to a JSON file" in {
      jsonGameState.save(scrabbleField)
      val file = new File("JSON/ScrabbleField.json")
      file.exists() should be(true)
      file.isFile should be(true)
    }

    "load a ScrabbleField from a JSON file" in {
      val loadedField = jsonGameState.load
      loadedField.matrix.rows should be(scrabbleField.matrix.rows)
      loadedField.players.size should be(scrabbleField.players.size)
      loadedField.player.name should be(scrabbleField.player.name)
      loadedField.languageEnum should be(scrabbleField.languageEnum)
    }

    "serialize and deserialize matrix correctly" in {
      val matrixJson = Json.obj(
        "rowsAndColumns" -> matrixSize,
        "field" -> matrix.field.map(row => row.map(_.letter.symbol).mkString("")).mkString("\n")
      )
      val matrixFromJson = jsonGameState.matrixFromJson(matrixJson)
      matrixFromJson.rows should be(matrixSize)
    }

    "serialize and deserialize players correctly" in {
      val playersJson = Json.toJson(players.map(player => Json.obj(
        "name" -> player.name,
        "points" -> player.points,
        "stones" -> Json.toJson(player.playerTiles.map(_.symbol).toString())
      )))
      val playersFromJson = jsonGameState.loadPlayersFromJson(playersJson.as[List[JsValue]])
      playersFromJson.size should be(players.size)
    }

    "serialize and deserialize current player correctly" in {
      val currentPlayerJson = Json.obj(
        "name" -> currentPlayer.name,
        "points" -> currentPlayer.points,
        "stones" -> Json.toJson(currentPlayer.playerTiles.map(_.symbol).toString())
      )
      val currentPlayerFromJson = jsonGameState.loadCurrentPlayerFromJson(currentPlayerJson)
      currentPlayerFromJson.name should be(currentPlayer.name)
      currentPlayerFromJson.points should be(currentPlayer.points)
    }

    "serialize and deserialize stone container correctly" in {
      val stoneContainerJson = Json.toJson(stoneContainer.stones.map(_.symbol).toString())
      val stoneContainerFromJson = jsonGameState.loadStoneContainerFromJson(stoneContainerJson)
      stoneContainerFromJson.size should not be(stoneContainer.stones.size)
    }
    "serialize and deserialize language correctly" in {
      val languageJson = Json.toJson(language.toString)
      val languageFromJson = jsonGameState.loadLanguageFromJson(languageJson)
      languageFromJson should be(language)
    }
    "loadLanguageFromJson" in {
      val languageJson = Json.toJson(language.toString)
      val languageFromJson = jsonGameState.loadLanguageFromJson(languageJson)
      languageFromJson should be(language)
    }
    "loadLanguageFromJsonFrench" in {
      val languageJson = Json.toJson(LanguageEnum.FRENCH.toString)
      val languageFromJson = jsonGameState.loadLanguageFromJson(languageJson)
      languageFromJson should be(LanguageEnum.FRENCH)
    }
    "loadLanguageFromJsonGerman" in {
      val languageJson = Json.toJson(LanguageEnum.GERMAN.toString)
      val languageFromJson = jsonGameState.loadLanguageFromJson(languageJson)
      languageFromJson should be(LanguageEnum.GERMAN)
    }
    "loadLanguageFromJsonItalian" in {
      val languageJson = Json.toJson(LanguageEnum.ITALIAN.toString)
      val languageFromJson = jsonGameState.loadLanguageFromJson(languageJson)
      languageFromJson should be(LanguageEnum.ITALIAN)
    }
    "loadLanguageFromJsonUnsupported" in {
      val languageJson = Json.toJson("unsupported")
      assertThrows[IllegalArgumentException] {
        jsonGameState.loadLanguageFromJson(languageJson)
      }
    }
  }
}
