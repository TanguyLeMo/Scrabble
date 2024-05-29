package de.htwg.se.scrabble

import scala.io.StdIn.readLine
import aview.TUI
import controller.Controller
import model.ScrabbleField
import model.Player
//This is to test the coverall feature






@main def run(): Unit = {


    val field = new ScrabbleField(15)
    val player = new Player("Someone", 0)
    val controller = Controller(field, player)
    val tui = TUI(controller)


    trait Event
    case class PlayerEvent() extends Event

    case class LanguageEvent() extends Event

    case class DictionaryEvent() extends Event

    case class RoundsEvent() extends Event

    case class GameEndEvent(players: List[Player]) extends Event

    object StateContext {
        var state: () => Any = playerState

        def handle(e: Event) = {
            e match {
                case player: PlayerEvent => state = playerState
                case language: LanguageEvent => state = languageState
                case dictionary: DictionaryEvent => state = dictionaryState
                case rounds: RoundsEvent => state = roundsState
                case gameEnd: GameEndEvent => state = () => gameEndState(gameEnd.players)

            }
        }
        def playerState(): List[Player] = {
            tui.inputNamesAndCreateList(tui.numberOfPLayers())
        }
        def languageState(): Unit = {
        }
        def dictionaryState(): Unit = {
        }
        def roundsState(): Unit = {
        }
        def gameEndState(players: List[Player]): Unit = {
            tui.displayLeaderboard(players)
        }
    }



    println("Welcome to Scrabble")
    val playerList = StateContext.state().asInstanceOf[List[Player]]
    println(controller.toString)
    println("Enter your Word, Coordinate and Direction(H|V) example: myWord A 0 H")






}
