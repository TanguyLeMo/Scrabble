package de.htwg.se.scrabble.model.gameState.GameStateBaseImpl

import de.htwg.se.scrabble.model.gameComponent.gameComponentBaseImpl.{Dictionary, Matrix, Player, ScrabbleField, Stone, StoneContainer}
import de.htwg.se.scrabble.model.gameComponent.{MatrixInterface, PlayerInterface, ScrabbleFieldInterface, ScrabbleSquare, SquareFactory, StoneContainerInterface}
import de.htwg.se.scrabble.model.gameState.GameStateInterface
import de.htwg.se.scrabble.util.LanguageEnum
import de.htwg.se.scrabble.model.gameComponent.gameComponentBaseImpl.Dictionary
import de.htwg.se.scrabble.model.gameComponent.squareBaseImpl.StandardSquareFactory

import java.io.{File, PrintWriter}
import scala.util.Try
import scala.xml.Elem

class XmlGameState extends GameStateInterface {

  override def save(scrabbleField: ScrabbleFieldInterface): Boolean = {
    createDirectory("XML")
    val pw = new PrintWriter(new File("XML/ScrabbleField.xml"))
    val prettyPrinter = new scala.xml.PrettyPrinter(120, 4)
    val xml = prettyPrinter.format(scrabbleFieldToXml(scrabbleField))
    val res = Try(pw.write(xml))
    pw.close()
    res.isSuccess
  }

  override def load: ScrabbleFieldInterface = {
    val xml = scala.xml.XML.loadFile("XML/ScrabbleField.xml")
    loadXmlToScrabbleField(xml)
  }

  def loadXmlToScrabbleField(xml: Elem): ScrabbleFieldInterface = {
    val matrix = loadXmlToMatrix(xml)
    val players = loadXmlToPlayers(xml)
    val currentPlayer = loadXmlToCurrentPlayer(xml)
    val stoneContainer = loadXmlToStoneContainer(xml)
    val language: LanguageEnum = loadXmlToLanguage(xml)
    new ScrabbleField(matrix, new Dictionary().readLines(language), new StandardSquareFactory, language, currentPlayer, players, stoneContainer)
  }

  def loadXmlToMatrix(xml: Elem): MatrixInterface = {
    val rowsAndColumns = (xml \ "matrix" \ "rowsAndColumns").text.trim.toInt
    val newMatrix = new Matrix(rowsAndColumns).init()
    val letters: Vector[String] = (xml \ "matrix" \ "field" \ "square").map(_.text.trim).grouped(rowsAndColumns).map(_.mkString).toVector
    println("geladene Matrix:")
    for( l <- letters){
      println(l)
    }
    updateMatrix(newMatrix, letters, 0)
  }


  def updateMatrix(matrix: Matrix, letters: Vector[String], index: Int): MatrixInterface = {
    if(index >= letters.length) return matrix
    val updatedMatrix = matrix.horizontalPlacement(index, 0, letters(index), 0, matrix)
    updateMatrix(updatedMatrix, letters, index + 1)
  }

  def loadXmlToPlayers(xml: Elem): List[PlayerInterface] = {
    val players = xml \ "players" \ "player"
    players.map(player => {
      val name = (player \ "name").text.trim
      val points = (player \ "points").text.trim.toInt
      val stones: List[Char] = (player \ "stones" \ "stone").map(_.text.trim.head).toList
      val stonesAsObjects: List[Stone] = stones.map(Stone(_))
      new Player(name, points, stonesAsObjects)
    }).toList
  }

  def loadXmlToCurrentPlayer(xml: Elem): Player = {
    val currentPlayer = (xml \ "currentPlayer" \ "player").head
    val name = (currentPlayer \ "name").text.trim
    val points = (currentPlayer \ "points").text.trim.toInt
    val stones: List[Char] = (currentPlayer \ "stones" \ "stone").map(_.text.trim.head).toList
    val stonesAsObjects: List[Stone] = stones.map(Stone(_))
    new Player(name, points, stonesAsObjects)
  }

  def loadXmlToStoneContainer(xml: Elem): StoneContainerInterface = {
    val stones = xml \ "stoneContainer" \ "stone"
    val stonesAsObjects: List[Stone] = stones.map(stone => Stone(stone.text.trim.head)).toList
    new StoneContainer(stonesAsObjects)
  }

  def loadXmlToLanguage(xml: Elem): LanguageEnum = {
    val language = (xml \ "language").text.trim.toUpperCase()
    language match {
      case "ENGLISH" => LanguageEnum.ENGLISH
      case "FRENCH" => LanguageEnum.FRENCH
      case "GERMAN" => LanguageEnum.GERMAN
      case "ITALIAN" => LanguageEnum.ITALIAN
      case _ => throw new IllegalArgumentException(s"Unsupported language: $language")
    }
  }

  private def createDirectory(path: String): Unit = {
    val directory = new File(path)
    if (!directory.exists()) {
      directory.mkdir()
    }
  }

  def scrabbleFieldToXml(scrabbleField: ScrabbleFieldInterface): Elem = {
    <ScrabbleField>
      {matrixAsXml(scrabbleField)}
      {playersToXml(scrabbleField)}
      {currentPlayerToXml(scrabbleField)}
      {stoneContainerToXml(scrabbleField)}
      <language>{scrabbleField.languageEnum.toString}</language>
    </ScrabbleField>
  }

  def matrixAsXml(scrabbleField: ScrabbleFieldInterface): Elem = {
    <matrix>
      <rowsAndColumns>{scrabbleField.matrix.rowsAndColumn}</rowsAndColumns>
      <field>
        {for {
        row <- 0 until scrabbleField.matrix.rows
        column <- 0 until scrabbleField.matrix.columns
      } yield <square>{scrabbleField.matrix.getSquare(row, column).letter}</square>}
      </field>
    </matrix>
  }

  def playersToXml(scrabbleField: ScrabbleFieldInterface): Elem = {
    <players>
      {for (player <- scrabbleField.players) yield playerToXml(player)}
    </players>
  }

  def playerToXml(Player: PlayerInterface): Elem = {
    <player>
      <name>{Player.name}</name>
      <points>{Player.points}</points>
      <stones>
        {for {
        stone <- Player.playerTiles
      } yield <stone>{stone.symbol}</stone>}
      </stones>
    </player>
  }

  def currentPlayerToXml(scrabbleField: ScrabbleFieldInterface): Elem = {
    <currentPlayer>
      {playerToXml(scrabbleField.player)}
    </currentPlayer>
  }

  def stoneContainerToXml(scrabbleField: ScrabbleFieldInterface): Elem = {
    <stoneContainer>
      {for {
      stone <- scrabbleField.stoneContainer.stones
    } yield <stone>{stone.toString}</stone>}
    </stoneContainer>
  }
}
