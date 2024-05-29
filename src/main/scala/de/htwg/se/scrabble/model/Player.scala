package de.htwg.se.scrabble.model

import de.htwg.se.scrabble.model.Player

import java.util
import java.util.LinkedList

class Player (name: String, points: Int):

  def getPoints: Int = points

  def getName: String = name

  def AddPoints(pointsToAdd: Int, player: Player, ListPlayers: List[Player]): List[Player] =
    val newPLayer = new Player(player.getName, player.getPoints+pointsToAdd)
    val newListPlayers = ListPlayers.updated(ListPlayers.indexOf(player),newPLayer)
    newListPlayers
  def CreatePlayersList (playerNames : Vector[String]) : List[Player] =
    playerNames.map(name => new Player(name, 0)).toList

  def nextTurn(playerList: List[Player], lastTurn: Player): Player =
    if (playerList.indexOf(lastTurn) + 1 > playerList.size)
      playerList(0)
    playerList(playerList.indexOf(lastTurn) + 1)

  def sortListAfterPoints(players: List[Player]) : List[Player] =
    val sortedPlayers = players.sortBy(-_.getPoints)
    sortedPlayers

  override def toString: String =
    name + " Points: " + points