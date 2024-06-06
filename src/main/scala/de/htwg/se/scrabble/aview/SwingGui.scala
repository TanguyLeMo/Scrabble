package de.htwg.se.scrabble
package aview

import controller.Controller
import controller.Controller
import de.htwg.se.scrabble.model.languages.LanguageContext
import de.htwg.se.scrabble.model.languages.LanguageEnum.{ENGLISH, FRENCH, GERMAN, ITALIAN}
import de.htwg.se.scrabble.model.{CreatePlayersListAsMove, Player, ScrabbleField, placeWordsAsMove, setGameLanguageAsMove}
import util.*

import scala.swing.*
import scala.swing.event.*
import util.{NameCantBeEmpty, Observer, ScrabbleEvent}
import util.ScrabbleEvent

class SwingGui(val controller: Controller, val languageContext: LanguageContext) extends Frame with Observer {
  controller.add(this)
  def this(controller: Controller) = this(controller, new LanguageContext("english"))

  val lw = languageWindow.top
  val nbpw = numberPlayerWindow.top
  val nmpw = namePlayerWindow.top
  val dw = dictionaryWindow.top
  val gamerounds = getInputAndDisplayGameWindow.top

  gamerounds.visible = true

  object languageWindow extends SimpleSwingApplication {
    def top = new MainFrame {
      title = "Language Settings"
      val options = Seq("english", "german", "french", "italian")
      val comboBox = new ComboBox(options)
      val next = new Button("Next")
      contents = new FlowPanel {
        contents += comboBox
        contents += next
        border = Swing.EmptyBorder(15, 10, 10, 10)
      }

      listenTo(next)
      reactions += {
        case ButtonClicked(`next`) =>
          val newTUI = comboBox.selection.item match
            case "english" =>
              controller.setLanguageDictionary(ENGLISH)
              new TUI(controller)
            case "french" =>
              controller.setLanguageDictionary(FRENCH)
              new TUI(controller)
            case "german" =>
              controller.setLanguageDictionary(GERMAN)
              new TUI(controller)
            case "italian" =>
              controller.setLanguageDictionary(ITALIAN)
              new TUI(controller)
          languageWindow.top.dispose()
      }
    }
  }


  object numberPlayerWindow extends SimpleSwingApplication {

    def top = new MainFrame {
      title = "number Players Settings"
      val text = new TextField(3)
      val next = new Button("Next")
      contents = new FlowPanel {
        contents += new Label("Enter number of Players: ")
        contents += text
        contents += next
        border = Swing.EmptyBorder(30, 10, 10, 10)
      }

      listenTo(next)
      reactions += {
        case ButtonClicked(`next`) =>
          println("Number of Players: " + text.text)
      }
    }
  }


  object namePlayerWindow extends SimpleSwingApplication {

    def top = new MainFrame {
      val numberOfTextFields = 4
      title = "Playernames Settings"
      val textFields = for (i <- 1 to numberOfTextFields) yield new TextField(16)
      val next = new Button("Next")
      contents = new FlowPanel {
        for (x <- textFields) {
          contents += new Label("Enter Player " + textFields.indexOf(x) + " Name: ")
          contents += x
        }
        contents += next
        border = Swing.EmptyBorder(40, 10, 10, 10)
      }

      listenTo(next)
      reactions += {
        case ButtonClicked(`next`) =>
          val playerNames = textFields.map(_.text).toVector
          if (playerNames.distinct.length != playerNames.length || playerNames.contains("")) {
            Dialog.showMessage(
              message = "Dies ist eine Popup-Nachricht",
              title = "Popup-Titel",
              messageType = Dialog.Message.Info
            )

          } else {
            controller.CreatePlayersList(playerNames)
            controller.notifyObservers(phaseMainGame())
            dispose()
          }
      }
    }
  }

  object dictionaryWindow extends SimpleSwingApplication {

    def top = new MainFrame {
      title = "dictionary Settings"
      val text = new TextField(15)
      val add = new Button("Add")
      val next = new Button("Next")
      contents = new FlowPanel {
        contents += new Label("Enter new word: ")
        contents += text
        contents += add
        contents += next
        border = Swing.EmptyBorder(30, 10, 10, 10)
      }

      listenTo(next, add)
      reactions += {
        case ButtonClicked(`add`) =>
          println(text.text)
          println(controller.contains(text.text))
          println(controller.field.languageEnum)
          if (controller.contains(text.text)) {
            Dialog.showMessage(
              message = "Dies ist eine Popup-Nachricht",
              title = "Popup-Titel",
              messageType = Dialog.Message.Info
            )
          } else {
            controller.add(text.text)
          }
        case ButtonClicked(`next`) =>
          dispose()
      }
    }
  }

  import scala.swing._
  import scala.swing.event._

  object getInputAndDisplayGameWindow extends SimpleSwingApplication {
    def top = new MainFrame {
      title = "Scrabble"
      val text = new TextField(16)
      val rows = controller.field.matrix.rows
      val columns = controller.field.matrix.columns
      val xAxisLabels= ('A' to 'O').map(_.toString)
      val yAxisLabels = (0 until rows).map(_.toString)

      // FlowPanel for X-axis labels
      val xAxisPanel = new ComboBox[String](xAxisLabels)

      // FlowPanel for Y-axis labels
      val yAxisPanel = new ComboBox[String](yAxisLabels)
      // ComboBox for choosing between horizontal and vertical placement
      val orientationComboBox = new ComboBox(Seq("Horizontal", "Vertical"))

      // Button for placing the word
      val placeButton = new Button("Place")

      // GridPanel with labels
      val gridWithLabelsPanel = new GridPanel(rows + 1, columns + 1) {
        // Add an empty label for the top-left corner
        contents += new Label("")

        // Add the X axis labels
        for (col <- 0 until 15) {
          contents += new Label(xAxisLabels(col)) {
            horizontalAlignment = Alignment.Center
            border = Swing.LineBorder(java.awt.Color.BLACK)
          }
        }

        // Add the grid rows with Y axis labels and grid content
        for (row <- 0 until 15) {
          // Add the Y axis label
          contents += new Label(yAxisLabels(row)) {
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
      val players = controller.field.players
      val sortedPlayers = controller.sortListAfterPoints(players)
      val resultString = sortedPlayers.zipWithIndex.map { case (player, index) => s"${index + 1}. $player\n" }.mkString("\n")

      val currentPlayer = new Label("Current Player: " + controller.field.player.getName)
      val scoreboard = new Label("Scoreboard: " + controller.field.player)
      val leaderboard = new Label("Leaderboard: " + resultString)
      // Main content
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
          if(controller.contains(word)){
            controller.notifyObservers(new WordAlreadyAddedToDictionary)
          }

          if(!controller.wordFits(x, y, direction, word)){
            controller.notifyObservers(new WordDoesntFit)
          }
          controller.placeWord(x, y, direction, word)
      }
    }
  }


  override def update(event: ScrabbleEvent): String = {
    event match
      case event: RoundsScrabbleEvent =>
        getInputAndDisplayGameWindow.top.visible = true
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
      case event: InvalidCoordinates => println(languageContext.invalidcoordinates)
      case event: NotInDictionary => println(languageContext.notInDictionary)
      case event: NoCorrectDirection => println(languageContext.noCorrectDirection)
      case event: WordDoesntFit => showErrorDialog(languageContext.wordDoesntFit)
      case event: EnterNumberOfPlayers =>
      case event: EnterPlayerName =>
      case event: NameAlreadyTaken => println(languageContext.nameAlreadyTaken)
      case event: NameCantBeEmpty => println(languageContext.nameCantBeEmpty)
      case event: EnterWord => println(languageContext.enterWord)
      case event: InvalidInput => println(languageContext.invalidInput)
      case event: InvalidNumber => println(languageContext.invalidNumber)
      case event: RequestNewWord => println(languageContext.requestNewWord)
      case event: WordAlreadyAddedToDictionary => showErrorDialog(languageContext.wordNotInDictionary)
      case event: WordAddedToDictionary => println(languageContext.wordAddedToDictionary)
      case event: EnterWordForDictionary => println(languageContext.enterWordforDictionary)
      case event: LanguageSetting => println(languageContext.languageSetting)
      case event: WordNotInDictionary => println(languageContext.wordNotInDictionary)
      case event: DisplayLeaderBoard => println(languageContext.leaderBoard)
        val players = controller.field.players
        val sortedPlayers = controller.sortListAfterPoints(players)
        sortedPlayers.foreach(player => println(sortedPlayers.indexOf(player) + 1 + ". " + player))
      case event: phaseChooseLanguage =>
        lw.visible = true
      case event: phasePlayerAndNames =>
        lw.visible = false
        nbpw.contents.head.revalidate()
        nbpw.repaint()
        nbpw.visible = true
      case event: phaseaddWordsToDictionary =>
        nbpw.visible = false
        dw.visible = true
      case event: phaseMainGame =>
        dw.visible = false
        getInputAndDisplayGameWindow.top.visible = true
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
}

