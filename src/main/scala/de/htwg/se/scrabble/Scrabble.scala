package de.htwg.se.scrabble
import aview._
import controller.Controller
import de.htwg.se.scrabble.aview.languages.LanguageEnum.ENGLISH
import model.ScrabbleField

@main def run(): Unit = {
    val field = new ScrabbleField(15, ENGLISH)
    val controller = Controller(field)
    val gui = new SwingGui(controller)
    val tui = TUI(controller).setGameLanguage()
    val playerList = tui.inputNamesAndCreateList(tui.numberOfPlayers())
    val tuiDictionary = tui.dictionaryAddWords
    tuiDictionary.controller.nextTurn(tuiDictionary.controller.thisPlayerList,tuiDictionary.controller.field.player)
    val MainGame = tuiDictionary.processInputLine()
    println("Goodbye!")
}
