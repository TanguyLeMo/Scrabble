package de.htwg.se.scrabble

import scala.io.StdIn.readLine
import aview.*
import controller.Controller
import de.htwg.se.scrabble.aview.languages.LanguageEnum.ENGLISH
import model.{Player, ScrabbleField, Stone}
import de.htwg.se.scrabble.aview.StateContext
//This is to test the coverall feature


@main def run(): Unit = {


    val field = new ScrabbleField(15, ENGLISH)
    val controller = Controller(field)
    val tui = TUI(controller)
    val stateContext = new StateContext(tui)
    
    println("Welcome to Scrabble")
    stateContext.state()
}
