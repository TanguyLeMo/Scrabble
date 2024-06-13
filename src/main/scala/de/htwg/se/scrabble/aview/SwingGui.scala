package de.htwg.se.scrabble
package aview

import de.htwg.se.scrabble.controller.ControllerComponent.ControllerBaseImpl.Controller
import de.htwg.se.scrabble.controller.ControllerComponent.ControllerInterface
import de.htwg.se.scrabble.model.languageComponent.LanguageContextInterface
import de.htwg.se.scrabble.model.languageComponent.languages.LanguageContext
import de.htwg.se.scrabble.util.LanguageEnum.{ENGLISH, FRENCH, GERMAN, ITALIAN}
import de.htwg.se.scrabble.util.placeWordsAsMove
import de.htwg.se.scrabble.model.gameComponent.ScrabbleFieldInterface
import util.*

import scala.swing.*
import scala.swing.event.*
import util.{NameCantBeEmpty, Observer, ScrabbleEvent}
import util.ScrabbleEvent

class SwingGui(val controller: ControllerInterface) extends Frame with Observer {
  controller.add(this)
  val gameWindow = GameWindow(controller)
  override def update(event: ScrabbleEvent): String = {
    event match
      case event: RoundsScrabbleEvent =>
        gameWindow.update()
      case event: DictionaryScrabbleEvent =>

      case event: RequestEnterLanguage =>

      case event: NoSuchLanguageScrabbleEvent =>
        println(" Entered Language not a available language")
        println(" Es handelt sich um keine verfügbare Sprache")
        println(" il s'agit pas une langue disponible")
        println(" non è una lingua disponibile")
      case event: GameEndScrabbleEvent => println("Game Over")
      case event: CurrentPlayer => println("Current Player: " + controller.field.player.getName)
      case event: Exit => println("Goodbye!")
      case event: InvalidCoordinates => println(controller.languageContext.invalidcoordinates)
      case event: NotInDictionary => println(controller.languageContext.notInDictionary)
      case event: NoCorrectDirection => println(controller.languageContext.noCorrectDirection)
      case event: WordDoesntFit => showErrorDialog(controller.languageContext.wordDoesntFit)
      case event: EnterNumberOfPlayers =>
      case event: EnterPlayerName =>
      case event: NameAlreadyTaken => println(controller.languageContext.nameAlreadyTaken)
      case event: NameCantBeEmpty => println(controller.languageContext.nameCantBeEmpty)
      case event: EnterWord =>
      case event: InvalidInput => println(controller.languageContext.invalidInput)
      case event: InvalidNumber => println(controller.languageContext.invalidNumber)
      case event: RequestNewWord => println(controller.languageContext.requestNewWord)
      case event: WordAlreadyAddedToDictionary => showErrorDialog(controller.languageContext.wordNotInDictionary)
      case event: WordAddedToDictionary => println(controller.languageContext.wordAddedToDictionary)
      case event: EnterWordForDictionary => println(controller.languageContext.enterWordForDictionary)
      case event: LanguageSetting => println(controller.languageContext.languageSetting)
      case event: WordNotInDictionary => println(controller.languageContext.wordNotInDictionary)
      case event: DisplayLeaderBoard => println(controller.languageContext.leaderBoard)
        val players = controller.field.players
        val sortedPlayers = controller.sortListAfterPoints(players)
        sortedPlayers.foreach(player => println(sortedPlayers.indexOf(player) + 1 + ". " + player))
      case event: phaseChooseLanguage =>
      case event: phasePlayerAndNames =>
      case event: phaseaddWordsToDictionary =>
      case event: phaseMainGame =>
        gameWindow.top.visible = true
      case _ => ""
    controller.toString
  }
  def showErrorDialog(message: String): Unit = {
    Dialog.showMessage(
      title = "Error",
      message = message,
      messageType = Dialog.Message.Error
    )
  }
  
  case class GameWindow(val controller: ControllerInterface) extends SimpleSwingApplication {
    val text = new TextField(16)
    val xAxisPanel = new ComboBox[String](('A' to 'O').map(_.toString))
    val yAxisPanel = new ComboBox[String]((0 until controller.field.matrix.rows).map(_.toString))
    val orientationComboBox = new ComboBox(Seq("Horizontal", "Vertical"))
    val placeButton = new Button("Place")
    val currentPlayer = new Label("Current Player: " + controller.field.player.getName)
    val scoreboard = new Label("Scoreboard: " + controller.field.player)
    val leaderboard = new Label("Leaderboard: " + controller.sortListAfterPoints(controller.field.players).zipWithIndex.map { case (player, index) => s"${index + 1}. $player\n" }.mkString("\n"))
    val gridWithLabelsPanel = new GridPanel(controller.field.matrix.rows + 1, controller.field.matrix.columns + 1) {
      // Add an empty label for the top-left corner
      contents += new Label("")

      // Add the X axis labels
      for (col <- 0 until 15) {
        contents += new Label(('A' to 'O').map(_.toString)(col)) {
          horizontalAlignment = Alignment.Center
          border = Swing.LineBorder(java.awt.Color.BLACK)
        }
      }

      // Add the grid rows with Y axis labels and grid content
      for (row <- 0 until 15) {
        // Add the Y axis label
        contents += new Label((0 until controller.field.matrix.rows).map(_.toString)(row)) {
          horizontalAlignment = Alignment.Center
          border = Swing.LineBorder(java.awt.Color.BLACK)
        }

        // Add the corresponding row of the scrabble grid
        for (col <- 0 until 15) {
          contents += new Label(controller.field.matrix.field(col)(row).letter.toString) {
            horizontalAlignment = Alignment.Center
            border = Swing.LineBorder(java.awt.Color.getColor(controller.field.matrix.field(col)(row).color))
          }
        }
      }
    }

    def top = new MainFrame {
      title = "Scrabble"
      contents = new BoxPanel(Orientation.Vertical) {
        contents += new FlowPanel {
          contents += text
          contents += xAxisPanel
          contents += yAxisPanel
          contents += orientationComboBox
          contents += placeButton
        }
        contents += gridWithLabelsPanel
        contents += currentPlayer
        contents += scoreboard
        border = Swing.EmptyBorder(10, 10, 10, 10)
      }
      listenTo(placeButton)
      reactions += {
        case ButtonClicked(`placeButton`) =>
          val coordinates = controller.translateCoordinate(xAxisPanel.selection.item.toString + " " + yAxisPanel.selection.item.toString)
          val y = coordinates._1
          val x = coordinates._2
          val direction = orientationComboBox.selection.item.head
          val word = text.text
          if(!controller.contains(word)){
            controller.notifyObservers(new WordAlreadyAddedToDictionary)
          }else if(!controller.wordFits(x, y, direction, word)){
            controller.notifyObservers(new WordDoesntFit)
          } else{
            controller.placeWord(x, y, direction, word)
          }
      }
    }

    def update(): Unit = {
      currentPlayer.text = "Current Player: " + controller.field.player.getName
      scoreboard.text = "Scoreboard: " + controller.field.player
      leaderboard.text = "Leaderboard: " + controller.sortListAfterPoints(controller.field.players).zipWithIndex.map { case (player, index) => s"${index + 1}. $player\n" }.mkString("\n")
      for (row <- 0 until 15) {
        for (col <- 0 until 15) {
          gridWithLabelsPanel.contents((row + 1) * (controller.field.matrix.columns + 1) + col + 1) = new Label(controller.field.matrix.field(col)(row).letter.toString) {
            horizontalAlignment = Alignment.Center
            border = Swing.LineBorder(java.awt.Color.getColor(controller.field.matrix.field(col)(row).color))
          }
        }
      }
    }
  }
}

