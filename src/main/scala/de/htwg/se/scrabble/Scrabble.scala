package de.htwg.se.scrabble
import aview.*
import de.htwg.se.scrabble.controller.ControllerComponent.ControllerBaseImpl.Controller
import de.htwg.se.scrabble.controller.ControllerComponent.ControllerInterface
import de.htwg.se.scrabble.model.LanguageEnum.ENGLISH
import de.htwg.se.scrabble.util.phaseChooseLanguage
import model.ScrabbleField

@main def run(): Unit = {
    val field = new ScrabbleField(15, ENGLISH)
    val controller: ControllerInterface = new Controller(field)
    val gui = new SwingGui(controller)
    val tui = TUI(controller)
    controller.notifyObservers(new phaseChooseLanguage)
}
