package de.htwg.se.scrabble.model

import de.htwg.se.scrabble.model.gameComponent.*
import de.htwg.se.scrabble.model.gameComponent.gameComponentBaseImpl.{Player, Stone}
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class PlayerSpec extends AnyWordSpec with Matchers {

  "A Player" should {

    "initialize with correct parameters" in {
      // Arrange
      val name = "Player1"
      val points = 0
      val tiles = List.empty[StoneInterface]

      // Act
      val player = new Player(name, points, tiles)

      // Assert
      player.getName should be(name)
      player.getPoints should be(points)
      player.playerTiles should be(tiles)
    }

    "be equal to another player with the same name" in {
      // Arrange
      val player1 = new Player("Player1", 0, List.empty)
      val player2 = new Player("Player1", 10, List(new Stone('A')))

      // Act & Assert
      player1 shouldEqual player2
    }
    "When comparing with another type" in {
      // Arrange
      val player = new Player("Player1", 0, List.empty)

      // Act & Assert
      player.equals("Player1") should be(false)
    }

    "add points correctly" in {
      // Arrange
      val player = new Player("Player1", 0, List.empty)
      val playersList = List(player)
      val pointsToAdd = 10

      // Act
      val updatedPlayers = player.AddPoints(pointsToAdd, player, playersList)

      // Assert
      updatedPlayers.head.getPoints should be(10)
    }

    "create a list of players" in {
      // Arrange
      val playerNames = Vector("Player1", "Player2")

      // Act
      val playersList = new Player("temp", 0, List.empty).CreatePlayersList(playerNames)

      // Assert
      playersList.size should be(2)
      playersList.map(_.getName) should contain allOf("Player1", "Player2")
    }

    "add stones to a player correctly" in {
      // Arrange
      val player = new Player("Player1", 0, List.empty)
      val playersList = List(player)
      val stones = List(new Stone('A'), new Stone('B'))

      // Act
      val updatedPlayers = player.addStones(player, playersList, stones)

      // Assert
      updatedPlayers.head.playerTiles should contain allOf(new Stone('A'), new Stone('B'))
    }

    "remove stones from a player correctly" in {
      // Arrange
      val stones = List(new Stone('A'), new Stone('B'), new Stone('C'))
      val player = new Player("Player1", 0, stones)
      val playersList = List(player)
      val stonesToRemove = List(new Stone('A'), new Stone('B'))

      // Act
      val updatedPlayers = player.removeStones(player, playersList, stonesToRemove)

      // Assert
      updatedPlayers.head.playerTiles should contain(new Stone('C'))
      updatedPlayers.head.playerTiles should not contain(new Stone('A'))
      updatedPlayers.head.playerTiles should not contain(new Stone('B'))
    }

    "check if player has required stones" in {
      // Arrange
      val stones = List(new Stone('H'), new Stone('E'), new Stone('L'), new Stone('L'), new Stone('O'))
      val player = new Player("Player1", 0, stones)
      val word = "HELLO"

      // Act & Assert
      player.hasStones(List.empty, word, player) should be(true)
      player.hasStones(List(new Stone('H')), word, player) should be(true)
    }

    "return the only required stones correctly" in {
      // Arrange
      val stones = List(new Stone('H'), new Stone('E'), new Stone('L'))
      val word = "HELLO"

      // Act
      val requiredStones = new Player("temp", 0, List.empty).OnlyRequiredStones(stones, word)

      // Assert
      requiredStones should contain allOf(new Stone('L'), new Stone('O'))
    }

    "return the next player correctly" in {
      // Arrange
      val player1 = new Player("Player1", 0, List.empty)
      val player2 = new Player("Player2", 0, List.empty)
      val player3 = new Player("Player3", 0, List.empty)
      val playersList = List(player1, player2, player3)

      // Act & Assert
      player1.nextTurn(playersList, player1) should be(player2)
      player1.nextTurn(playersList, player3) should be(player1)
    }
    "nextTurn when none " should{
      "return the first player" in {
        // Arrange
        val player1 = new Player("Player1", 0, List.empty)
        val player2 = new Player("Player2", 0, List.empty)
        val player3 = new Player("Player3", 0, List.empty)
        val playersList = List(player1, player2, player3)

        // Act & Assert
        player1.nextTurn(playersList, player3) should be(player1)
      }
    }
    "sort players by points correctly" in {
      // Arrange
      val player1 = new Player("Player1", 10, List.empty)
      val player2 = new Player("Player2", 30, List.empty)
      val player3 = new Player("Player3", 20, List.empty)
      val playersList = List(player1, player2, player3)

      // Act
      val sortedPlayers = player1.sortListAfterPoints(playersList)

      // Assert
      sortedPlayers should be(List(player2, player3, player1))
    }
    

  }
}
