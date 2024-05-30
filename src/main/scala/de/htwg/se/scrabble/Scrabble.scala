package de.htwg.se.scrabble

import scala.io.StdIn.readLine
import aview._
import controller.Controller
import de.htwg.se.scrabble.aview.languages.LanguageEnum.ENGLISH
import model.ScrabbleField
import model.Player
import de.htwg.se.scrabble.aview.StateContext
//This is to test the coverall feature


@main def run(): Unit = {


    val field = new ScrabbleField(15, ENGLISH)
    val player = new Player("Someone", 0)
    val controller = Controller(field, player)
    val tui = TUI(controller)
    val stateContext = new StateContext(tui)
    
    println("Welcome to Scrabble")
    val playerList = stateContext.state().asInstanceOf[List[Player]]
    stateContext.handle(LanguageEvent())
    stateContext.state()
    stateContext.handle(DictionaryEvent())
    stateContext.state()
    stateContext.handle(RoundsEvent(playerList.head,playerList))
    stateContext.state()
    stateContext.handle(GameEndEvent(playerList))
    stateContext.state()
}
