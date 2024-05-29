package de.htwg.se.scrabble

import scala.io.StdIn.readLine
import aview.TUI
import controller.Controller
import model.ScrabbleField
import model.Player
//This is to test the coverall feature






@main def run(): Unit = {


    val field = new ScrabbleField(15,"english")
    val player = new Player("Someone", 0)
    val controller = Controller(field, player)
    val tui = TUI(controller)


    trait Event
    case class PlayerEvent() extends Event

    case class LanguageEvent() extends Event

    case class DictionaryEvent() extends Event

    case class RoundsEvent(player: Player, players: List[Player]) extends Event

    case class GameEndEvent(players: List[Player]) extends Event

    object StateContext {
        var state: () => Any = playerState

        def handle(e: Event) = {
            e match {
                case player: PlayerEvent => state = playerState
                case language: LanguageEvent => state = languageState
                case dictionary: DictionaryEvent => state = dictionaryState
                case rounds: RoundsEvent => state = () => roundsState(rounds.player,rounds.players)
                case gameEnd: GameEndEvent => state = () => gameEndState(gameEnd.players)

            }
        }
        def playerState(): List[Player] = {
            tui.inputNamesAndCreateList(tui.numberOfPLayers())
        }
        def languageState(): Unit = {
            while(!tui.inputDictionaryLanguage()){}
        }
        def dictionaryState(): Unit = {
            println("Enter your personal words, which should be available in the dictionary, apart from the default words")
            println("type: \u001B[1m stop \u001B[0m to finish the input of your personal words")
            while (!tui.dictionaryAddWords(readLine()).equals("stop")) {}
        }
        def roundsState(player: Player, players: List[Player]): Unit = {
            println(controller.toString)
            while (!tui.processInputLine(readLine(),player, players).equals("exit")) {}
        }
        def gameEndState(players: List[Player]): Unit = {
            tui.displayLeaderboard(players)
        }
    }

    println("Welcome to Scrabble")
    val playerList = StateContext.state().asInstanceOf[List[Player]]
    StateContext.handle(LanguageEvent())
    StateContext.state()
    StateContext.handle(DictionaryEvent())
    StateContext.state()
    StateContext.handle(RoundsEvent(playerList(0),playerList))
    StateContext.state()
    StateContext.handle(GameEndEvent(playerList))
    StateContext.state()







}
