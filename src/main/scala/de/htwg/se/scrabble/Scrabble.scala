package de.htwg.se.scrabble
import aview.*
import com.google.inject.{Guice, Injector}
import de.htwg.se.scrabble.controller.ControllerComponent.ControllerBaseImpl.Controller
import de.htwg.se.scrabble.controller.ControllerComponent.ControllerInterface
import de.htwg.se.scrabble.model.gameComponent.ScrabbleFieldInterface
import de.htwg.se.scrabble.model.gameComponent.gameComponentBaseImpl.ScrabbleField
import de.htwg.se.scrabble.util.LanguageEnum.ENGLISH
import de.htwg.se.scrabble.util.phaseChooseLanguage

@main def run(): Unit = {
    val injector: Injector = Guice.createInjector(new Modules)
    val field: ScrabbleFieldInterface = injector.getInstance(classOf[ScrabbleFieldInterface])
    val controller: ControllerInterface = injector.getInstance(classOf[ControllerInterface])
    val gui = new SwingGui(controller)
    val tui = TUI(controller)
    controller.notifyObservers(new phaseChooseLanguage)
}
