package de.htwg.se.scrabble
import aview.*
import de.htwg.se.scrabble.controller.ControllerComponent.ControllerBaseImpl.Controller
import de.htwg.se.scrabble.controller.ControllerComponent.ControllerInterface
import de.htwg.se.scrabble.model.gameComponent.ScrabbleFieldInterface
import de.htwg.se.scrabble.model.gameComponent.gameComponentBaseImpl.ScrabbleField
import de.htwg.se.scrabble.util.LanguageEnum.ENGLISH
import de.htwg.se.scrabble.util.phaseChooseLanguage

@main def run(): Unit = {
    val field: ScrabbleFieldInterface = new ScrabbleField(15, ENGLISH)
    val controller: ControllerInterface = new Controller(field)
    val gui = new SwingGui(controller)
    val tui = TUI(controller)
    controller.notifyObservers(new phaseChooseLanguage)
}
