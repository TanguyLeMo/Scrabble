package de.htwg.se.scrabble.model.gameComponent

trait PlayerInterface {
  def getPoints: Int
  def getName: String
  def equals(obj: Any): Boolean
  def AddPoints(pointsToAdd: Int, player: Player, ListPlayers: List[Player]): List[Player]
  def CreatePlayersList (playerNames : Vector[String]) : List[Player]
  def addStones (player: Player, ListPlayers: List[Player],stones: List[Stone]): List[Player]
  def removeStones(player: Player, ListPlayers: List[Player],stones: List[Stone]): List[Player]
  def hasStones(notRequiredStones: List[Stone], word: String, player: Player): Boolean
  def OnlyRequiredStones(notRequiredStones: List[Stone], word: String): List[Stone]
  def nextTurn(playerList: List[Player], lastTurn: Player): Player
  def sortListAfterPoints(players: List[Player]) : List[Player]
  def toString: String
  def previousTurn(playerList: List[Player], currentTurn: Player): Player

}
