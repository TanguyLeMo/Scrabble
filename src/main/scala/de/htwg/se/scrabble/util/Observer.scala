package de.htwg.se.scrabble
package util
trait Observer:
  def update(event : ScrabbleEvent): String

trait Observable:
  var subscribers: Vector[Observer] = Vector()
  def add(s: Observer): Unit = {
    subscribers = subscribers.filterNot(_.getClass == s.getClass)
    subscribers = subscribers :+ s
  }
  def remove(s: Observer): Unit = subscribers = subscribers.filter(o => o == s)
  def notifyObservers(event: ScrabbleEvent): Unit =
    subscribers.foreach(o => o.update(event))
/*
enum ScrabbleEvent {
  case PlayerScrabbleEvent
  case LanguageScrabbleEvent
  case DictionaryScrabbleEvent
  case RoundsScrabbleEvent
  case GameEndScrabbleEvent
  case RequestEnterLanguage
  case NoSuchLanguageScrabbleEvent
  case RequestNewWord
  case WordAlreadyAddedToDictionary
  case WordAddedToDictionary
  case InvalidCoordinates 
  case InvalidInput
  case NotInDictionary
  case WordNotInDictionary
  case EnterWordForDictionary
  case Stop
  case LanguageSetting
  case EnterWord
  case NoCorrectDirection
  case WordDoesntFit
  case Exit
  case NameAlreadyTaken
  case EnterNumberOfPlayers
  case InvalidNumber
  case CurrentPlayer
  case EnterPlayerName
  case NameCantBeEmpty
  case NotEnoughStones
  case DisplayLeaderBoard

  case phaseChooseLanguage
  case phasePlayerAndNames
  case phaseaddWordsToDictionary
  case phaseMainGame
  case phaseEndGame
  case phaseExit
}*/