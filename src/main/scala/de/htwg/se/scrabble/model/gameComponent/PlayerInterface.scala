package de.htwg.se.scrabble.model.gameComponent


trait PlayerInterface {
  val name: String
  val points: Int
  val playerTiles: List[StoneInterface]
  
  def getPoints: Int
  def getName: String
  def equals(obj: Any): Boolean
  def AddPoints(pointsToAdd: Int, player: PlayerInterface, ListPlayers: List[PlayerInterface]): List[PlayerInterface]
  def CreatePlayersList (playerNames : Vector[String]) : List[PlayerInterface]
  def addStones (player: PlayerInterface, ListPlayers: List[PlayerInterface],stones: List[StoneInterface]): List[PlayerInterface]
  def removeStones(player: PlayerInterface, ListPlayers: List[PlayerInterface],stones: List[StoneInterface]): List[PlayerInterface]
  def hasStones(notRequiredStones: List[StoneInterface], word: String, player: PlayerInterface): Boolean
  def OnlyRequiredStones(notRequiredStones: List[StoneInterface], word: String): List[StoneInterface]
  def nextTurn(playerList: List[PlayerInterface], lastTurn: PlayerInterface): PlayerInterface
  def sortListAfterPoints(players: List[PlayerInterface]) : List[PlayerInterface]
  def toString: String

}
