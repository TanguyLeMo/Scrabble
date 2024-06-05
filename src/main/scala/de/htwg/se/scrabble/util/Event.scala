package de.htwg.se.scrabble.util

import de.htwg.se.scrabble.controller
import de.htwg.se.scrabble.model.Player

import scala.io.StdIn.readLine

trait Event
case class PlayerEvent() extends Event
case class LanguageEvent() extends Event
case class DictionaryEvent() extends Event
case class RoundsEvent() extends Event
case class GameEndEvent() extends Event
case class RequestEnterLanguage() extends Event
case class NoSuchLanguageEvent() extends Event

case class RequestNewWord() extends Event
case class WordAlreadyAddedToDictionary() extends Event
case class WordAddedToDictionary() extends Event
case class InvalidCoordinates() extends Event
case class InvalidInput() extends Event
case class NotInDictionary() extends Event
case class WordNotInDictionary() extends Event
case class EnterWordForDictionary() extends Event
case class Stop() extends Event
case class LanguageSetting() extends Event
case class EnterWord() extends Event
case class NoCorrectDirection() extends Event
case class WordDoesntFit() extends Event
case class Exit() extends Event
case class NameAlreadyTaken() extends Event
case class EnterNumberOfPlayers() extends Event
case class InvalidNumber() extends Event
case class CurrentPlayer() extends Event
case class EnterPlayerName() extends Event
case class NameCantBeEmpty() extends Event
case class NotEnoughStones() extends Event
case class DisplayLeaderBoard() extends Event