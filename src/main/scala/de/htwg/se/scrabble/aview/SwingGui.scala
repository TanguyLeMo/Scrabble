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
import javax.swing.{BorderFactory, ImageIcon}
import scala.util.{Failure, Success, Try}

class SwingGui(controller: ControllerInterface) extends Frame with Observer {
  controller.add(this)
  val mainMenuWindow = MainMenuWindow(controller)
  val languageWindow = LanguageWindow(controller)
  val numberPlayerWindow = NumberPlayerWindow(controller)
  val dictionaryWindow = DictionaryWindow(controller)
  val gameWindow = GameWindow(controller)

  override def update(event: ScrabbleEvent): String = {
    event match
      case event: RoundsScrabbleEvent =>
        gameWindow.update()
      case event: DictionaryScrabbleEvent =>

      case event: RequestEnterLanguage =>


      case event: WordDoesntFit => showErrorDialog(controller.languageContext.wordDoesntFit)
      case event: NameAlreadyTaken => showErrorDialog(controller.languageContext.nameAlreadyTaken)
      case event: NameCantBeEmpty => showErrorDialog(controller.languageContext.nameCantBeEmpty)
      case event: InvalidNumber => showErrorDialog(controller.languageContext.invalidNumber)
      case event: WordAlreadyAddedToDictionary => showErrorDialog(controller.languageContext.wordAlreadyAddedToDictionary)
      case event: NotEnoughStones => showErrorDialog(controller.languageContext.notEnoughStones)
      case event: WordAddedToDictionary => showErrorDialog(controller.languageContext.wordAddedToDictionary)
      case event: WordNotInDictionary => showErrorDialog(controller.languageContext.wordNotInDictionary)
      case event: phaseChooseLanguage =>
      case event: phasePlayerAndNames =>
      case event: phaseaddWordsToDictionary =>
      case event: phaseMainGame =>
        mainMenuWindow.top.visible = true
        
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


  case class MainMenuWindow(val controller: ControllerInterface) extends SimpleSwingApplication {
    def top = new MainFrame {
      iconImage = new ImageIcon(getClass.getResource("/scrabbleloggo.png")).getImage
      title = "Scrabble"
      val newGame = new Button("New Game") {
        maximumSize = new Dimension(100, 30)
        xLayoutAlignment = java.awt.Component.CENTER_ALIGNMENT
        background = java.awt.Color(202, 209, 220)
        opaque = true
      }
      val load = new Button("Load") {
        maximumSize = new Dimension(100, 30)
        xLayoutAlignment = java.awt.Component.CENTER_ALIGNMENT
        background = java.awt.Color(202, 209, 220)
        opaque = true
      }


      val backgroundImage = new ImageIcon(getClass.getResource("/scrabble.jpg"))


      val backgroundLabel = new Label {
        icon = backgroundImage
      }

      val buttonPanel = new BoxPanel(Orientation.Vertical) {
        contents += newGame
        contents += load
        border = Swing.EmptyBorder(60, 20, 20, 20)
        background = new Color(0, 0, 0, 0)
        opaque = false
      }

      val borderPanel = new BorderPanel {
        layout(backgroundLabel) = BorderPanel.Position.North
        layout(buttonPanel) = BorderPanel.Position.Center
        background = java.awt.Color(160, 182, 171)
        opaque = true
      }
      contents = borderPanel

      background = java.awt.Color(160, 182, 171)


      listenTo(newGame, load)
      reactions += {
        case ButtonClicked(`newGame`) =>
          dispose()
          languageWindow.top.visible = true
        case ButtonClicked(`load`) =>
          controller.load
          dispose()
          gameWindow.update()
          gameWindow.top.visible = true
      }

    }

  }


  case class LanguageWindow(val controller: ControllerInterface) extends SimpleSwingApplication {
    def top = new MainFrame {
      iconImage = new ImageIcon(getClass.getResource("/scrabbleloggo.png")).getImage
      title = "Language Settings"
      val options = Seq("english", "german", "french", "italian")
      val comboBox = new ComboBox(options)
      val next = new Button(controller.field.languageContext.next)
      comboBox.background = java.awt.Color(202, 209, 220)
      comboBox.opaque = true
      val next = new Button("Next")
      next.background = java.awt.Color(202, 209, 220)
      next.opaque = true
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
          controller.gamestartPlayStones(controller.field.languageEnum)
          dispose()
          numberPlayerWindow.top.visible = true
      }
    }
  }


  case class NumberPlayerWindow(val controller: ControllerInterface) extends SimpleSwingApplication {

    def top = new MainFrame {
      iconImage = new ImageIcon(getClass.getResource("/scrabbleloggo.png")).getImage
      title = "Scrabble"
      val text = new TextField(3)
      val next = new Button(controller.field.languageContext.next)
      next.background = java.awt.Color(202, 209, 220)
      next.opaque = true
      contents = new BoxPanel(Orientation.Vertical) {
        contents += new FlowPanel {
          contents += new Label(controller.field.languageContext.enterNumberofPlayers)
          contents += text
          background = java.awt.Color(160, 182, 171)
          opaque = true
        }
        contents += new BoxPanel(Orientation.Horizontal) {
          contents += Swing.HGlue
          contents += next
          contents += Swing.HGlue
          background = java.awt.Color(160, 182, 171)
          opaque = true
        }
        border = Swing.EmptyBorder(30, 10, 10, 10)
        background = java.awt.Color(160, 182, 171)
        opaque = true
      }

      listenTo(next)
      reactions += {
        case ButtonClicked(`next`) =>
          val isNumber: Try[Int] = Try(text.text.toInt)
          isNumber match {
            case Success(value) =>
              if (value > 0 && (controller.field.stoneContainer.stones.length / (value * 7.0)) >= 1.0)
                dispose()
                val namePlayerWindow = NamePlayerWindow(controller, text.text.toInt)
                namePlayerWindow.top.visible = true
              else {
                Dialog.showMessage(
                  message = controller.field.languageContext.invalidNumber,
                  title = controller.field.languageContext.invalidInput,
                  messageType = Dialog.Message.Info
                )
              }
            case Failure(exception) =>
              Dialog.showMessage(
                message = controller.field.languageContext.invalidNumber,
                title = controller.field.languageContext.invalidInput,
                messageType = Dialog.Message.Info
              )
          }
      }
    }
  }


  case class NamePlayerWindow(val controller: ControllerInterface, numberPlayer: Int) extends SimpleSwingApplication {

    def top = new MainFrame {
      val numberOfTextFields = numberPlayer
      title = "Scrabble"
      val textFields = for (i <- 1 to numberOfTextFields) yield new TextField(16)
      val next = new Button(controller.field.languageContext.next)
      next.background = java.awt.Color(202, 209, 220)
      next.opaque = true
      contents = new BoxPanel(Orientation.Vertical) {
        for (x <- textFields) {
          contents += new FlowPanel {
            contents += new Label("Player " + (textFields.indexOf(x) + 1) + ": ")
            contents += x
            background = java.awt.Color(160, 182, 171)
            opaque = true
          }
        }
        contents += new BoxPanel(Orientation.Horizontal) {
          contents += Swing.HGlue
          contents += next
          contents += Swing.HGlue
          background = java.awt.Color(160, 182, 171)
          opaque = true
        }
        border = Swing.EmptyBorder(10, 10, 10, 10)
        background = java.awt.Color(160, 182, 171)
        opaque = true
      }

      listenTo(next)
      reactions += {
        case ButtonClicked(`next`) =>
          val playerNames = textFields.map(_.text).toVector
          if (playerNames.distinct.length != playerNames.length){
            controller.notifyObservers(NameAlreadyTaken())
          } else if(playerNames.contains("")) {
            controller.notifyObservers(NameCantBeEmpty())
          }else {
            val PlayerListWithoutStones = controller.CreatePlayersList(playerNames)
            val PlayerListWithStones = playerStartStones(7, PlayerListWithoutStones)
            dispose()
            dictionaryWindow.top.visible = true
          }
      }
      size = new Dimension(300, numberPlayer*50+70)
    }

    def playerStartStones(numberStones: Int, playerList: List[PlayerInterface]): List[PlayerInterface] = {
      if (numberStones == 0) playerList
      else
        val updatedPlayerList = playerList.foldLeft(playerList) { (updatedList, player) =>
          val StonetoAdd = controller.drawRandomStone(controller.field.stoneContainer)
          controller.removeStonefromContainer(StonetoAdd, controller.field.stoneContainer)
          val AddStonetoPlayer: PlayerInterface = new Player(player.getName, player.getPoints, player.playerTiles :+ StonetoAdd)
          updatedList.updated(updatedList.indexOf(player), AddStonetoPlayer)
        }
        controller.field = new ScrabbleField(controller.field.matrix, controller.field.dictionary, controller.field.squareFactory, controller.field.languageEnum, updatedPlayerList.head, updatedPlayerList, controller.field.stoneContainer)
        playerStartStones(numberStones - 1, updatedPlayerList)
    }
  }

  case class DictionaryWindow(val controller: ControllerInterface) extends SimpleSwingApplication {

    def top = new MainFrame {
      iconImage = new ImageIcon(getClass.getResource("/scrabbleloggo.png")).getImage
      title = "Scrabble"
      val text = new TextField(15)
      val add = new Button("Add")
      add.background = java.awt.Color(202, 209, 220)
      add.opaque = true
      val next = new Button("Next")
      next.background = java.awt.Color(202, 209, 220)
      next.opaque = true
      contents = new BoxPanel(Orientation.Vertical) {
        contents += new BoxPanel(Orientation.Horizontal) {
          contents += Swing.HGlue
          contents += new Label("Own word for dictionary: ")
          contents += Swing.HGlue
          background = java.awt.Color(160, 182, 171)
          opaque = true
        }
        contents += text
        contents += new FlowPanel {
          contents += add
          contents += next
          border = Swing.EmptyBorder(10, 10, 10, 10)
          background = java.awt.Color(160, 182, 171)
          opaque = true
        }
        border = Swing.EmptyBorder(30, 10, 10, 10)
        background = java.awt.Color(160, 182, 171)
        opaque = true
      }

      listenTo(next, add)
      reactions += {
        case ButtonClicked(`add`) =>
          if (controller.contains(text.text)) {
            controller.notifyObservers(WordAlreadyAddedToDictionary())
          } else {
            controller.add(text.text)
            controller.notifyObservers(WordAddedToDictionary())
          }
        case ButtonClicked(`next`) =>
          dispose()
          controller.notifyObservers(RoundsScrabbleEvent())
          controller.notifyObservers(EnterWord())
          gameWindow.update()
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
      contents += new Label("") {
        background = java.awt.Color(200,216,208)
        opaque = true
      }
      background = java.awt.Color(200,216,208)
      for (col <- 0 until 15) {
        contents += new Label(('A' to 'O').map(_.toString)(col)) {
          horizontalAlignment = Alignment.Center
          border = BorderFactory.createEmptyBorder()
          background = java.awt.Color(200,216,208)
          opaque = true
        }
      }

      for (row <- 0 until 15) {
        contents += new Label((0 until controller.field.matrix.rows).map(_.toString)(row)) {
          horizontalAlignment = Alignment.Center
          border = Swing.EmptyBorder
          background = java.awt.Color(200,216,208)
          opaque = true
        }

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
          val directionunchecked = orientationComboBox.selection.item.toUpperCase().head
          val direction = if(directionunchecked == 'H' || directionunchecked == 'V') directionunchecked else 'H'
          val word = text.text.toUpperCase
          if(!controller.contains(word)){
            controller.notifyObservers(new WordNotInDictionary)
          }else if(!controller.wordFits(x, y, direction, word)){
            controller.notifyObservers(new WordDoesntFit)
          } else{
            val lettersAlreadyThere = controller.lettersAlreadyThere(x, y, direction, word)
            val onlyRequiredStones = controller.OnlyRequiredStones(lettersAlreadyThere, word)
            if(onlyRequiredStones.isEmpty){
              controller.notifyObservers(new WordDoesntFit)
            }
            else if (controller.hasStones(lettersAlreadyThere, word, controller.field.player)) {
              controller.removeStones(controller.field.player, controller.field.players, onlyRequiredStones)
              drawStonesAfterRound(controller.field.player, onlyRequiredStones.length, controller.field.players)
              controller.placeWord(x, y, direction, word)
              controller.nextTurn(controller.thisPlayerList, controller.field.player)
            } else {
              controller.notifyObservers(new NotEnoughStones)
            }
          }
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
      orientationComboBox.peer.setModel(ComboBox.newConstantModel(Seq(controller.field.languageContext.horizontal, controller.field.languageContext.vertical)))//new javax.swing.DefaultComboBoxModel(Array(controller.field.languageContext.horizontal, controller.field.languageContext.vertical)))
      menuBar = setMenuBar
    }
  }

  def drawStonesAfterRound(player: PlayerInterface, numberOfCards: Int, players: List[PlayerInterface]): List[PlayerInterface] = {
    if (controller.field.stoneContainer.stones.isEmpty || numberOfCards == 0) {
      players
    }
    else {
      val newStone = controller.drawRandomStone(controller.field.stoneContainer)
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

