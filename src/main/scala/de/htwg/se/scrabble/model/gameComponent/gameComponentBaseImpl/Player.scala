package de.htwg.se.scrabble.model.gameComponent
package gameComponentBaseImpl

import com.google.inject.Inject
import de.htwg.se.scrabble.model.gameComponent.gameComponentBaseImpl.Stone
import de.htwg.se.scrabble.model.gameComponent.PlayerInterface

import java.util
import java.util.LinkedList

class Player @Inject (val name: String,val points: Int,val playerTiles: List[StoneInterface]) extends PlayerInterface:
  def getPoints: Int = points
  def getName: String = name
  override def equals(obj: Any): Boolean = obj match
    case obj: Player => obj.getName == this.getName
    case _ => false
    
  def AddPoints(pointsToAdd: Int, player: PlayerInterface, ListPlayers: List[PlayerInterface]): List[PlayerInterface] =
    val newPLayer = new Player(player.getName, player.getPoints+pointsToAdd, player.playerTiles)
    if(!ListPlayers.contains(player)) return ListPlayers
    val newListPlayers = ListPlayers.updated(ListPlayers.indexOf(player),newPLayer)
    newListPlayers
    
  def CreatePlayersList (playerNames : Vector[String]) : List[PlayerInterface] =
    playerNames.map(name => new Player(name, 0,List[StoneInterface]())).toList

  def addStones (player: PlayerInterface, ListPlayers: List[PlayerInterface],stones: List[StoneInterface]): List[PlayerInterface] =
    val newPlayer = new Player(player.getName, player.getPoints, player.playerTiles ++ stones)
    val newListPlayers = ListPlayers.updated(ListPlayers.indexOf(player),newPlayer)
    newListPlayers
  
  def removeStones(player: PlayerInterface, ListPlayers: List[PlayerInterface],stones: List[StoneInterface]): List[PlayerInterface] =
    val newPlayerTiles = stones.foldLeft(player.playerTiles) { (remainingTiles, stone) =>
      val (before, after) = remainingTiles.span(_ != stone)
      before ++ after.drop(1)
    }
    val newPlayer = new Player(player.getName, player.getPoints, newPlayerTiles)
    val newListPlayers = ListPlayers.updated(ListPlayers.indexOf(player),newPlayer)
    newListPlayers

  def hasStones(notRequiredStones: List[StoneInterface], word: String, player: PlayerInterface): Boolean = {
    val requiredStones = OnlyRequiredStones(notRequiredStones, word)
    println(player)
    println(player.playerTiles)
    println(word)
    println(requiredStones.forall(player.playerTiles.contains))
    requiredStones.forall(player.playerTiles.contains)
  }

  def OnlyRequiredStones(notRequiredStones: List[StoneInterface], word: String): List[StoneInterface] = {
    val requiredStonesForWord = word.toCharArray.map(char => Stone(char)).toList

    val remainingNotRequired = scala.collection.mutable.ListBuffer(notRequiredStones*)

    val requiredStones = requiredStonesForWord.filter { stone =>
      if (remainingNotRequired.contains(stone)) {
        remainingNotRequired -= stone
        false
      } else {
        true
      }
    }

    requiredStones
  }


  def nextTurn(playerList: List[PlayerInterface], lastTurn: PlayerInterface): PlayerInterface =
    val index = playerList.indexOf(lastTurn) + 1
    val playerOption = playerList.lift(index)

    playerOption match 
      case Some(player) => player
      case None => playerList.head
    
    
  def sortListAfterPoints(players: List[PlayerInterface]) : List[PlayerInterface] =
    val sortedPlayers = players.sortBy(-_.getPoints)
    sortedPlayers

  override def toString: String =
    name + " Points: " + points

