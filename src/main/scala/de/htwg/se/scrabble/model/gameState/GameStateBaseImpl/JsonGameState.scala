package de.htwg.se.scrabble.model.gameState
package GameStateBaseImpl

import de.htwg.se.scrabble.model.gameComponent.{MatrixInterface, PlayerInterface, ScrabbleFieldInterface}
import de.htwg.se.scrabble.model.gameComponent.gameComponentBaseImpl.{Dictionary, Matrix, Player, ScrabbleField, Stone, StoneContainer}
import de.htwg.se.scrabble.model.gameComponent.squareBaseImpl.StandardSquareFactory
import de.htwg.se.scrabble.model.gameState.GameStateInterface
import de.htwg.se.scrabble.util.LanguageEnum
import play.api.libs.json.{JsValue, Json, __}

import java.io.{File, PrintWriter}
import scala.util.Try

class JsonGameState extends GameStateInterface {
  def createDirectory(path: String): Unit = {
    val directory = new File(path)
    if (!directory.exists()) {
      directory.mkdir()
    }
  }

  override def save(scrabbleField: ScrabbleFieldInterface): Boolean = {
    createDirectory("JSON")
    saveField(scrabbleField)
    true
  }

  override def load: ScrabbleFieldInterface = {
    val json = loadJsonFromFile("JSON/ScrabbleField.json")
    getFieldFromJson(json)
  }
  def loadJsonFromFile(fileName: String): JsValue = {
    val file = new File(fileName)
    val inputStream = new java.io.FileInputStream(file)
    val content = Json.parse(inputStream)
    content
  }

  def getFieldFromJson(json: JsValue): ScrabbleFieldInterface = {
    val fieldJson = (json \ "ScrabbleField").as[List[JsValue]]

    val matrixJson = fieldJson.head
    val matrix = matrixFromJson(matrixJson)

    val playersJson = (fieldJson(1) \ "players").get
    val players = loadPlayersFromJson(playersJson.as[List[JsValue]])

    val currentPlayerJson = (fieldJson(2) \ "currentPlayer").get
    val currentPlayer = loadCurrentPlayerFromJson(currentPlayerJson)

    val stoneContainerJson = (fieldJson(3) \ "stoneContainer").get
    val stoneContainer = loadStoneContainerFromJson(stoneContainerJson)

    val languageJson = (fieldJson(4) \ "language").get
    val language = loadLanguageFromJson(languageJson)

    new ScrabbleField(
      matrix,
      new Dictionary().readLines(language),
      new StandardSquareFactory,
      language,
      currentPlayer,
      players,
      new StoneContainer(stoneContainer)
    )
  }

  def loadPlayersFromJson(players: List[JsValue]): List[PlayerInterface] = {
    val tmp = players.map(player => {
      val name = (player \ "name").as[String]
      val points = (player \ "points").as[Int]
      val stonesString = (player \ "stones").as[String]
      val stones = stonesString.toList
      val stonesAsObjects: List[Stone] = stones.map(Stone(_))
      new Player(name, points, stonesAsObjects)
    })

    tmp
  }
  def loadCurrentPlayerFromJson(currentPlayer: JsValue): PlayerInterface = {
    val name = (currentPlayer \ "name").as[String]
    val points = (currentPlayer \ "points").as[Int]
    val stones: List[Char] = (currentPlayer \ "stones").as[String].toList
    val stonesAsObjects: List[Stone] = stones.map(Stone(_))
    new Player(name, points, stonesAsObjects)
  }
  def loadStoneContainerFromJson(stoneContainer: JsValue): List[Stone] = {
    val stones: List[Char] = stoneContainer.as[String].toList
    stones.map(Stone(_))
  }
  def loadLanguageFromJson(language: JsValue): LanguageEnum = {
    val languageString = language.as[String]
    languageString match {
      case "ENGLISH" => LanguageEnum.ENGLISH
      case "FRENCH" => LanguageEnum.FRENCH
      case "GERMAN" => LanguageEnum.GERMAN
      case "ITALIAN" => LanguageEnum.ITALIAN
      case _ => throw new IllegalArgumentException(s"Unsupported language: $languageString")
    }
  }

  def updateMatrix(matrix: Matrix, letters: Vector[String], index: Int): MatrixInterface = {
    if(index >= letters.length) return matrix
    print(matrix.getSquare(0, 1))
    val updatedMatrix = matrix.horizontalPlacement(index, 0, letters(index), 0, matrix)
    updateMatrix(updatedMatrix, letters, index + 1)
  }

  def matrixFromJson(json: JsValue): MatrixInterface = {
    val rowsAndColumns = (json \ "rowsAndColumns").as[Int]
    val fieldString = (json \ "field").as[String]
    val field = fieldString.split("\n").toVector
    val matrix: Matrix = new Matrix(rowsAndColumns).init()
    updateMatrix(matrix, field, 0)
  }

  def saveField(scrabbleField: ScrabbleFieldInterface): Unit = {

    val matrixRealString = for{row <- 0 until scrabbleField.matrix.rows
                               column <- 0 until scrabbleField.matrix.columns} yield scrabbleField.matrix.getSquare(row, column).letter.symbol
    val matriString: String = matrixRealString.grouped(scrabbleField.matrix.columns).map(_.mkString("")).mkString("\n")

    val matrixJson: JsValue = Json.obj(
      "rowsAndColumns" -> scrabbleField.matrix.rowsAndColumn,
      "field" -> matriString
    )
    val playersJson: JsValue = Json.obj(
      "players" -> Json.toJson(scrabbleField.players.map(player => Json.obj(
        "name" -> player.name,
        "points" -> player.points,
        "stones" -> Json.toJson(player.playerTiles.map(_.symbol).toString())
      ))))
      val currentPlayerJson: JsValue = Json.obj(
        "currentPlayer" -> Json.obj(
          "name" -> scrabbleField.player.name,
          "points" -> scrabbleField.player.points,
          "stones" -> Json.toJson(scrabbleField.player.playerTiles.map(_.symbol).toString())
        )
      )
      val stoneContainerJson: JsValue = Json.obj(
        "stoneContainer" -> Json.toJson(scrabbleField.stoneContainer.stones.map(_.symbol).toString())
      )
      val languageJson: JsValue = Json.obj(
        "language" -> scrabbleField.languageEnum.toString
      )
      val scrabbleFieldJson: JsValue = Json.obj(
        "ScrabbleField" -> Json.toJson(Seq(matrixJson, playersJson, currentPlayerJson, stoneContainerJson, languageJson))
      )
      saveJsonToFile(scrabbleFieldJson, "JSON/ScrabbleField.json")

  }

  def saveJsonToFile(json: JsValue, fileName: String): Unit = {
    val file = new File(fileName)
    val writer = new PrintWriter(file)
    writer.write(Json.prettyPrint(json))
    writer.close()
  }


}