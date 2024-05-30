package de.htwg.se.scrabble.aview

import de.htwg.se.scrabble.controller
import de.htwg.se.scrabble.model.Player

import scala.io.StdIn.readLine

trait Event

case class PlayerEvent() extends Event

case class LanguageEvent() extends Event

case class DictionaryEvent() extends Event

case class RoundsEvent(player: Player, players: List[Player]) extends Event

case class GameEndEvent(players: List[Player]) extends Event