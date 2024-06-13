package de.htwg.se.scrabble.util

import de.htwg.se.scrabble.controller
import de.htwg.se.scrabble.model.gameComponent.Player

import scala.io.StdIn.readLine

trait ScrabbleEvent
case class PlayerScrabbleEvent() extends ScrabbleEvent
case class LanguageScrabbleEvent() extends ScrabbleEvent
case class DictionaryScrabbleEvent() extends ScrabbleEvent
case class RoundsScrabbleEvent() extends ScrabbleEvent
case class GameEndScrabbleEvent() extends ScrabbleEvent
case class RequestEnterLanguage() extends ScrabbleEvent
case class NoSuchLanguageScrabbleEvent() extends ScrabbleEvent
case class RequestNewWord() extends ScrabbleEvent
case class WordAlreadyAddedToDictionary() extends ScrabbleEvent
case class WordAddedToDictionary() extends ScrabbleEvent
case class InvalidCoordinates() extends ScrabbleEvent
case class InvalidInput() extends ScrabbleEvent
case class NotInDictionary() extends ScrabbleEvent
case class WordNotInDictionary() extends ScrabbleEvent
case class EnterWordForDictionary() extends ScrabbleEvent
case class Stop() extends ScrabbleEvent
case class LanguageSetting() extends ScrabbleEvent
case class EnterWord() extends ScrabbleEvent
case class NoCorrectDirection() extends ScrabbleEvent
case class WordDoesntFit() extends ScrabbleEvent
case class Exit() extends ScrabbleEvent
case class NameAlreadyTaken() extends ScrabbleEvent
case class EnterNumberOfPlayers() extends ScrabbleEvent
case class InvalidNumber() extends ScrabbleEvent
case class CurrentPlayer() extends ScrabbleEvent
case class EnterPlayerName() extends ScrabbleEvent
case class NameCantBeEmpty() extends ScrabbleEvent
case class NotEnoughStones() extends ScrabbleEvent
case class DisplayLeaderBoard() extends ScrabbleEvent

case class phaseChooseLanguage() extends ScrabbleEvent
case class phasePlayerAndNames() extends ScrabbleEvent
case class phaseaddWordsToDictionary() extends ScrabbleEvent
case class phaseMainGame() extends ScrabbleEvent
case class phaseEndGame() extends ScrabbleEvent
case class phaseExit() extends ScrabbleEvent
