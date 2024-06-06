package de.htwg.se.scrabble
package aview

import controller.Controller
import controller.Controller
import de.htwg.se.scrabble.aview.languages.*
import de.htwg.se.scrabble.aview.languages.LanguageEnum.{ENGLISH, FRENCH, GERMAN, ITALIAN}
import de.htwg.se.scrabble.model.{CreatePlayersListAsMove, Player, ScrabbleField, placeWordsAsMove, setGameLanguageAsMove}
import util._

import scala.swing.*
import scala.swing.event.*
import util.{ScrabbleEvent, NameCantBeEmpty, Observer}
import util.ScrabbleEvent

class SwingGui(val controller: Controller, val languageContext : LanguageContext) extends Frame with Observer{
  controller.add(this)
  def this(controller: Controller) = this(controller, new LanguageContext("english"))

  object languageWindow extends SimpleSwingApplication  {
    println("heee")
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
              new TUI(controller, new LanguageContext(comboBox.selection.item))
            case "french" =>
              controller.setLanguageDictionary(FRENCH)
              new TUI(controller, new LanguageContext(comboBox.selection.item))
            case "german" =>
              controller.setLanguageDictionary(GERMAN)
              new TUI(controller, new LanguageContext(comboBox.selection.item))
            case "italian" =>
              controller.setLanguageDictionary(ITALIAN)
              new TUI(controller, new LanguageContext(comboBox.selection.item))
          languageWindow.top.dispose()
      }
    }
  }


  object numberPlayerWindow extends SimpleSwingApplication {
    println("hiiii")

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
    println("huuuuuu")

    def top = new MainFrame {
      title = "number Players Settings"
      val text = new TextField(16)
      val next = new Button("Next")
      contents = new FlowPanel {
        contents += new Label("Playername: ")
        contents += text
        contents += next
        border = Swing.EmptyBorder(40, 10, 10, 10)
      }

      listenTo(next)
      reactions += {
        case ButtonClicked(`next`) =>
          println("Number of Players: " + text.text)
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
      val xAxisLabels = ('A' to 'O').map(_.toString)
      val yAxisLabels = (0 until rows).map(_.toString)

      // FlowPanel for X-axis labels
      val xAxisPanel = new FlowPanel {
        for (label <- xAxisLabels) {
          contents += new Label(label) {
            horizontalAlignment = Alignment.Center
            border = Swing.LineBorder(java.awt.Color.BLACK)
          }
        }
      }

      // FlowPanel for Y-axis labels
      val yAxisPanel = new FlowPanel {
        for (label <- yAxisLabels) {
          contents += new Label(label) {
            horizontalAlignment = Alignment.Center
            border = Swing.LineBorder(java.awt.Color.BLACK)
          }
        }
      }

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
            contents += new Label(controller.field.matrix.field(row)(col).letter.toString) {
              horizontalAlignment = Alignment.Center
              border = Swing.LineBorder(java.awt.Color.GREEN)
            }
          }
        }
      }

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
        border = Swing.EmptyBorder(10, 10, 10, 10)
      }
    }
  }


  override def update(event: ScrabbleEvent): String = {
    event match
      case event: RoundsScrabbleEvent =>
        getInputAndDisplayGameWindow.top.visible = true
      case event: DictionaryScrabbleEvent =>
        println(controller.field.languageSettings)
      case event: RequestEnterLanguage =>
        val window = languageWindow.top
        if(window.visible == false)
          window.visible = true
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
      case event: WordDoesntFit => println(languageContext.wordDoesntFit)
      case event: EnterNumberOfPlayers =>
        languageWindow.top.dispose()
        val window = numberPlayerWindow.top
        window.visible = true

      case event: EnterPlayerName => 
        val window: Frame = namePlayerWindow.top
        window.visible = true
      case event: NameAlreadyTaken => println(languageContext.nameAlreadyTaken)
      case event: NameCantBeEmpty => println(languageContext.nameCantBeEmpty)
      case event: EnterWord => println(languageContext.enterWord)
      case event: InvalidInput => println(languageContext.invalidInput)
      case event: InvalidNumber => println(languageContext.invalidNumber)
      case event: RequestNewWord => println(languageContext.requestNewWord)
      case event: WordAlreadyAddedToDictionary => println(languageContext.wordAlreadyAddedToDictionary)
      case event: WordAddedToDictionary => println(languageContext.wordAddedToDictionary)
      case event: EnterWordForDictionary => println(languageContext.enterWordforDictionary)
      case event: LanguageSetting => println(languageContext.languageSetting)
      case event: WordNotInDictionary => println(languageContext.wordNotInDictionary)
      case event: DisplayLeaderBoard => println(languageContext.leaderBoard)
        val players = controller.field.players
        val sortedPlayers = controller.sortListAfterPoints(players)
        sortedPlayers.foreach(player => println(sortedPlayers.indexOf(player) + 1 + ". " + player))
      case _ => ""
    controller.toString
  }
}

