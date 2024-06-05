package de.htwg.se.scrabble.model

import de.htwg.se.scrabble.model.Player
import de.htwg.se.scrabble.model.Stone

import java.util
import java.util.LinkedList

class Player (val name: String,val points: Int,val playerTiles: List[Stone]):

  def getPoints: Int = points

  def getName: String = name

  override def equals(obj: Any): Boolean = obj match
    case obj: Player => obj.getName == this.getName
    case _ => false
    
  def AddPoints(pointsToAdd: Int, player: Player, ListPlayers: List[Player]): List[Player] =
    val newPLayer = new Player(player.getName, player.getPoints+pointsToAdd, player.playerTiles)
    if(!ListPlayers.contains(player)) return ListPlayers
    val newListPlayers = ListPlayers.updated(ListPlayers.indexOf(player),newPLayer)
    newListPlayers
    
  def CreatePlayersList (playerNames : Vector[String]) : List[Player] =
    playerNames.map(name => new Player(name, 0,List[Stone]())).toList

  def addStones (player: Player, ListPlayers: List[Player],stones: List[Stone]): List[Player] =
    val newPlayer = new Player(player.getName, player.getPoints, player.playerTiles ++ stones)
    val newListPlayers = ListPlayers.updated(ListPlayers.indexOf(player),newPlayer)
    newListPlayers
  
  def removeStones(player: Player, ListPlayers: List[Player],stones: List[Stone]): List[Player] =
    val playerStonesChars = player.playerTiles.map(_.symbol)
    val stonesChars = stones.map(_.symbol)

    val newPlayerTilesChars = playerStonesChars.diff(stonesChars)
    val newPlayerTiles = newPlayerTilesChars.map(char => Stone(char))

    val newPlayer = new Player(player.getName, player.getPoints, newPlayerTiles)
    val newListPlayers = ListPlayers.updated(ListPlayers.indexOf(player), newPlayer)
    newListPlayers

  def hasStones(notRequiredStones: List[Stone], word: String, player: Player): Boolean = {
    val requiredStones = OnlyRequiredStones(notRequiredStones, word)
    requiredStones.forall(player.playerTiles.contains)
  }
  
  def OnlyRequiredStones(notRequiredStones: List[Stone], word: String): List[Stone] = {
    val notRequiredChars = notRequiredStones.map(_.symbol)
    val requiredCharsForWord = word.toCharArray.toList
    val requiredStones = requiredCharsForWord.diff(notRequiredChars).map(char => Stone(char))
    requiredStones
  }

  def nextTurn(playerList: List[Player], lastTurn: Player): Player =
    val index = playerList.indexOf(lastTurn) + 1
    val playerOption = playerList.lift(index)

    playerOption match 
      case Some(player) => player
      case None => playerList.head
    
    
  def sortListAfterPoints(players: List[Player]) : List[Player] =
    val sortedPlayers = players.sortBy(-_.getPoints)
    sortedPlayers

  override def toString: String =
    name + " Points: " + points + " Stones: " + playerTiles.mkString(" ")
