package de.htwg.se.scrabble
import aview.*
import controller.Controller
import de.htwg.se.scrabble.model.languageComponent.languages.LanguageEnum.ENGLISH
import de.htwg.se.scrabble.util.phaseChooseLanguage
import model.gameComponent.ScrabbleField

@main def run(): Unit = {
    val field = new ScrabbleField(15, ENGLISH)
    val controller = Controller(field)
    val gui = new SwingGui(controller)
    val tui = TUI(controller)
    controller.notifyObservers(new phaseChooseLanguage)
}
