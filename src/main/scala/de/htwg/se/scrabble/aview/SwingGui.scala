package de.htwg.se.scrabble
package aview

import de.htwg.se.scrabble.model.gameComponent.gameComponentBaseImpl.ScrabbleField
import de.htwg.se.scrabble.model.gameComponent.gameComponentBaseImpl.Player
import de.htwg.se.scrabble.controller.ControllerComponent.ControllerBaseImpl.Controller
import de.htwg.se.scrabble.controller.ControllerComponent.ControllerInterface
import de.htwg.se.scrabble.model.languageComponent.LanguageContextInterface
import de.htwg.se.scrabble.model.languageComponent.languages.LanguageContext
import de.htwg.se.scrabble.util.LanguageEnum.{ENGLISH, FRENCH, GERMAN, ITALIAN}
import de.htwg.se.scrabble.util.placeWordsAsMove
import de.htwg.se.scrabble.model.gameComponent.{PlayerInterface, ScrabbleFieldInterface}
import util.*

import scala.swing.*
import scala.swing.event.*
import util.{NameCantBeEmpty, Observer, ScrabbleEvent}
import util.ScrabbleEvent

import java.awt
import javax.swing.{BorderFactory, ImageIcon, JMenuBar}
import scala.swing.Action.NoAction.icon
import scala.swing.event.Key.Modifiers

class SwingGui(controller: ControllerInterface) extends Frame with Observer {
  controller.add(this)
  val languageWindow = LanguageWindow(controller)
  val gameWindow = GameWindow(controller)

  override def update(event: ScrabbleEvent): String = {
    event match
      case event: RoundsScrabbleEvent =>
        gameWindow.update()
      case event: DictionaryScrabbleEvent =>

      case event: RequestEnterLanguage =>

      case event: NoSuchLanguageScrabbleEvent =>
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
      case event: NotEnoughStones => showErrorDialog(controller.languageContext.notEnoughStones)
      case event: WordAddedToDictionary => println(controller.languageContext.wordAddedToDictionary)
      case event: EnterWordForDictionary =>
      case event: LanguageSetting => println(controller.languageContext.languageSetting)
      case event: WordNotInDictionary => println(controller.languageContext.wordNotInDictionary)
      case event: DisplayLeaderBoard => println(controller.languageContext.leaderBoard)
        val players = controller.field.players
        val sortedPlayers = controller.sortListAfterPoints(players)
        sortedPlayers.foreach(player => println(sortedPlayers.indexOf(player) + 1 + ". " + player))
      case event: phaseChooseLanguage =>
        val languageWindow = LanguageWindow(controller)
        //languageWindow.top.visible = true
      case event: phasePlayerAndNames =>
      case event: phaseaddWordsToDictionary =>
      case event: phaseMainGame =>
        gameWindow.update()
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


  case class LanguageWindow(val controller: ControllerInterface) extends SimpleSwingApplication {
    def top = new MainFrame {
      title = "Language Settings"

      val options = Seq("english", "german", "french", "italian")
      val comboBox = new ComboBox(options)
      val next = new Button("Next")
      contents = new FlowPanel {
        contents += comboBox
        contents += next
        border = Swing.EmptyBorder(15, 10, 10, 10)
        background = java.awt.Color(160, 182, 171)
        opaque = true
      }

      listenTo(next)
      reactions += {
        case ButtonClicked(`next`) =>
          val newTUI = comboBox.selection.item match
            case "english" =>
              controller.setLanguageDictionary(ENGLISH)
            case "french" =>
              controller.setLanguageDictionary(FRENCH)
            case "german" =>
              controller.setLanguageDictionary(GERMAN)
            case "italian" =>
              controller.setLanguageDictionary(ITALIAN)
          dispose()
          gameWindow.top.visible = true
      }
    }
  }


  case class GameWindow(val controller: ControllerInterface) extends SimpleSwingApplication {
    val text = new TextField(16)
    text.background = java.awt.Color(249,218,165)
    val xAxisPanel = new ComboBox[String](('A' to 'O').map(_.toString))
    xAxisPanel.background = java.awt.Color(202,209,220)
    xAxisPanel.opaque = true
    val yAxisPanel = new ComboBox[String]((0 until controller.field.matrix.rows).map(_.toString))
    yAxisPanel.background = java.awt.Color(202,209,220)
    var orientationComboBox = new ComboBox(Seq(controller.field.languageContext.horizontal, controller.field.languageContext.vertical))
    orientationComboBox.background = java.awt.Color(202,209,220)
    orientationComboBox.opaque = true
    val placeButton = new Button(controller.field.languageContext.place)
    placeButton.background = java.awt.Color(202,209,220)
    placeButton.opaque = true
    val currentPlayer = new Label(controller.field.languageContext.currentPlayer + controller.field.player.getName)
    currentPlayer.background = java.awt.Color(255,246,214)
    currentPlayer.opaque = true
    val leaderboard = new TextArea(controller.field.languageContext.leaderBoard + "\n" + controller.sortListAfterPoints(controller.field.players).zipWithIndex.map { case (player, index) => s"${index + 1}. $player" }.mkString("\n"))
    leaderboard.editable = false
    leaderboard.background = java.awt.Color(200,216,208)
    leaderboard.opaque = true
    val playerstones = new Label(controller.field.player.playerTiles.mkString(" | "))
    playerstones.border = Swing.LineBorder(java.awt.Color.BLACK)
    playerstones.background = java.awt.Color(249,218,165)
    playerstones.opaque = true




    background = java.awt.Color.RED
    val gridWithLabelsPanel = new GridPanel(controller.field.matrix.rows + 1, controller.field.matrix.columns + 1) {
      // Add an empty label for the top-left corner


      contents += new Label("") {
        background = java.awt.Color(200,216,208)
        opaque = true
      }
      background = java.awt.Color(200,216,208)
      // Add the X axis labels
      for (col <- 0 until 15) {
        contents += new Label(('A' to 'O').map(_.toString)(col)) {
          horizontalAlignment = Alignment.Center
          border = BorderFactory.createEmptyBorder()
          background = java.awt.Color(200,216,208)
          opaque = true
        }
      }

      // Add the grid rows with Y axis labels and grid content
      for (row <- 0 until 15) {
        // Add the Y axis label
        contents += new Label((0 until controller.field.matrix.rows).map(_.toString)(row)) {
          horizontalAlignment = Alignment.Center
          border = Swing.EmptyBorder
          background = java.awt.Color(200,216,208)
          opaque = true
        }

        // Add the corresponding row of the scrabble grid
        for (col <- 0 until 15) {
          val cellColor = controller.field.matrix.field(col)(row).color
          val label = new Label(controller.field.matrix.field(col)(row).letter.toString) {
            horizontalAlignment = Alignment.Center
            border = Swing.EmptyBorder
            background = consoleColorToSwingColor(cellColor)
            opaque = true
          }
          label.opaque = true
          contents += label
        }
      }
    }
    def setMenuBar: scala.swing.MenuBar = {
      new MenuBar {
        contents += new Menu(controller.field.languageContext.save) {
          contents += new MenuItem(Action(controller.field.languageContext.save) {
            controller.save
            background = java.awt.Color(200, 216, 208)
          })
          contents += new MenuItem(Action(controller.field.languageContext.load) {
            controller.load
            background = java.awt.Color(200, 216, 208)
          })
          background = java.awt.Color(200, 216, 208)
        }
        contents += new Menu("language") {
          contents += new MenuItem(Action(controller.field.languageContext.english) {
            controller.setLanguageDictionary(ENGLISH)
            background = java.awt.Color(200, 216, 208)
            update()

          })
          contents += new MenuItem(Action(controller.field.languageContext.german) {
            controller.setLanguageDictionary(GERMAN)
            background = java.awt.Color(200, 216, 208)
            update()

          })
          contents += new MenuItem(Action(controller.field.languageContext.french) {
            controller.setLanguageDictionary(FRENCH)
            background = java.awt.Color(200, 216, 208)
            update()

          })
          contents += new MenuItem(Action(controller.field.languageContext.italian) {
            controller.setLanguageDictionary(ITALIAN)
            background = java.awt.Color(200, 216, 208)
            update()
          })
          contents += new MenuItem(controller.field.languageContext.currentLanguageRequest + controller.field.languageContext.currentLanguage)

          background = java.awt.Color(200, 216, 208)
        }

        contents += new Menu(controller.field.languageContext.undo) {
          contents += new MenuItem(Action(controller.field.languageContext.undo) {
            controller.doAndPublish(controller.undo)
            update()
            background = java.awt.Color(200, 216, 208)
          })
          contents += new MenuItem(Action(controller.field.languageContext.redo) {
            controller.doAndPublish(controller.redo)
            update()
            background = java.awt.Color(200, 216, 208)
          })
          background = java.awt.Color(200, 216, 208)
        }
        background = java.awt.Color(200, 216, 208)
      }
    }


    def top: MainFrame = new MainFrame {
      title = "Scrabble"
      iconImage = new ImageIcon(getClass.getResource("/scrabbleloggo.png")).getImage
      background = java.awt.Color.RED
      contents = new BoxPanel(Orientation.Vertical) {
        background = java.awt.Color(200,216,208)
        opaque = true
        contents += new FlowPanel {
          contents += text
          contents += xAxisPanel
          contents += yAxisPanel
          contents += orientationComboBox
          contents += placeButton
          background = java.awt.Color(200,216,208)
          opaque = true
        }
        contents += gridWithLabelsPanel

        menuBar = setMenuBar
        contents += new FlowPanel {
          contents += currentPlayer
          contents += playerstones
          background = java.awt.Color(200,216,208)
          opaque = true
        }
        contents += leaderboard
        background = java.awt.Color.BLUE
        opaque = true
        border = Swing.EmptyBorder(10, 10, 10, 10)
        background = java.awt.Color(200,216,208)
        opaque = true
      }
      background = java.awt.Color(200,216,208)
      listenTo(placeButton)

      reactions += {
        case ButtonClicked(`placeButton`) =>
          val coordinates = controller.translateCoordinate(xAxisPanel.selection.item.toString + " " + yAxisPanel.selection.item.toString)
          val y = coordinates._1
          val x = coordinates._2
          val direction = orientationComboBox.selection.item.head
          val word = text.text.toUpperCase
          if(!controller.contains(word)){
            controller.notifyObservers(new WordAlreadyAddedToDictionary)
          }else if(!controller.wordFits(x, y, direction, word)){
            controller.notifyObservers(new WordDoesntFit)
          } else{
            val lettersAlreadyThere = controller.lettersAlreadyThere(x, y, direction, word)
            val onlyRequiredStones = controller.OnlyRequiredStones(lettersAlreadyThere, word)
            if (controller.hasStones(lettersAlreadyThere, word, controller.field.player)) {
              controller.removeStones(controller.field.player, controller.field.players, onlyRequiredStones)
              drawStonesAfterRound(controller.field.player, onlyRequiredStones.length, controller.field.players)
              controller.placeWord(x, y, direction.toUpper, word)
              controller.nextTurn(controller.thisPlayerList, controller.field.player)
            } else {
              controller.notifyObservers(new NotEnoughStones)
            }
          }
        case KeyPressed(_, Key.Z, Key.Modifier.Control, _) => controller.doAndPublish(controller.undo); update() ; println("undo")
        case KeyPressed(_, Key.Y, Key.Modifier.Control, _) => controller.doAndPublish(controller.redo); update() ; println("redo")
      }


      background = java.awt.Color.YELLOW
    }


    def update(): Unit = {
      currentPlayer.text = controller.field.languageContext.currentPlayer + controller.field.player.getName
      currentPlayer.background = java.awt.Color(255,246,214)
      leaderboard.text = controller.field.languageContext.leaderBoard + "\n" + controller.sortListAfterPoints(controller.field.players).zipWithIndex.map { case (player, index) => s"${index + 1}. $player"}.mkString("\n")
      leaderboard.background = java.awt.Color(255,246,214)
      playerstones.text = controller.field.player.playerTiles.mkString(" | ")
      for (row <- 0 until 15) {
        for (col <- 0 until 15) {
          gridWithLabelsPanel.contents((row + 1) * (controller.field.matrix.columns + 1) + col + 1) = new Label(controller.field.matrix.field(col)(row).letter.toString) {
            horizontalAlignment = Alignment.Center
            border = Swing.LineBorder(java.awt.Color(160,182,171))
            background = if(controller.field.matrix.field(col)(row).letter.symbol == '_'){
              consoleColorToSwingColor(controller.field.matrix.field(col)(row).color)
            } else {
              java.awt.Color(249,218,165)
            }
            opaque = true
          }
        }
      }
      placeButton.text = controller.field.languageContext.place
      placeButton.background = java.awt.Color(202,209,220)

      text.text = ""
      orientationComboBox = new ComboBox(Seq(controller.field.languageContext.horizontal, controller.field.languageContext.vertical))
      menuBar = setMenuBar
    }
  }

  def drawStonesAfterRound(player: PlayerInterface, numberOfCards: Int, players: List[PlayerInterface]): List[PlayerInterface] = {
    if (controller.field.stoneContainer.stones.isEmpty || numberOfCards == 0) {
      players
    } //newPlayer(name, points, tleslist[Stone],
    else {
      val newStone = controller.drawRandomStone(controller.field.stoneContainer)
      println(newStone)
      controller.removeStonefromContainer(newStone, controller.field.stoneContainer)
      val newPlayer: PlayerInterface = new Player(player.getName, player.getPoints, player.playerTiles :+ newStone) // create new Player via controller
      val newPlayerList = controller.field.players.updated(controller.field.players.indexOf(player), newPlayer)
      controller.field = new ScrabbleField(controller.field.matrix, controller.field.dictionary, controller.field.squareFactory, controller.field.languageEnum, newPlayer, newPlayerList, controller.field.stoneContainer)
      drawStonesAfterRound(newPlayer, numberOfCards - 1, newPlayerList)

    }
  }


  def consoleColorToSwingColor(color: String): java.awt.Color = {
    color match
      case "white" => java.awt.Color(121,139,127)
      case Console.RED => java.awt.Color(133, 33, 32)
      case Console.MAGENTA => java.awt.Color(80, 4, 44)
      case Console.YELLOW => java.awt.Color(227,128, 0)
      case Console.BLUE => java.awt.Color(27, 47, 109)
      case _ => java.awt.Color.BLACK
  }

}

