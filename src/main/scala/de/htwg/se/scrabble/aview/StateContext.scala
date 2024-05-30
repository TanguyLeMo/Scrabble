package de.htwg.se.scrabble.aview
import de.htwg.se.scrabble.model.{Player, ScrabbleField}


class StateContext(tui: TUI) extends Event {
  var state: () => StateContext = playerState
  def handle(e: Event): StateContext = {
    e match {
      case player: PlayerEvent => state = playerState
      case language: LanguageEvent => state = setLanguageState
      case dictionary: DictionaryEvent => state = addWordInDictionaryState
      case rounds: RoundsEvent => state = roundsState
      case gameEnd: GameEndEvent => state = () => gameEndState(gameEnd.players)
    }
    state()
  }

  def playerState(): StateContext = {
    tui.inputNamesAndCreateList(tui.numberOfPLayers())
    new StateContext(tui).handle(LanguageEvent())
  }

  def setLanguageState(): StateContext = {
    new StateContext(tui.setGameLanguage()).handle(DictionaryEvent())
  }

  def addWordInDictionaryState(): StateContext = {
    new StateContext(tui.dictionaryAddWords).handle(RoundsEvent(tui.controller.field.player, tui.controller.field.players))
  }

  def roundsState(): StateContext = {
    tui.update()
    new StateContext(tui.processInputLine(tui.controller.field.player)).handle(GameEndEvent(tui.controller.field.players))
  }

  def gameEndState(players: List[Player]): StateContext = {
    tui.displayLeaderboard(players)
    this
  }
}
