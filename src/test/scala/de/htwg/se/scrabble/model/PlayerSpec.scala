package de.htwg.se.scrabble.model
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._

class PlayerSpec extends AnyWordSpec {
  "A Player" when {
    "created" should {
      val player = new Player("Someone", 0, List[Stone]())
      "have a name" in {
        player.getName should be("Someone")
      }
      "have a score" in {
        player.getPoints should be(0)
      }
      "have a nice String representation" in {
        player.toString should be("Someone Points: 0")
      }
      "be equal to another player with the same name" in {
        val player2 = new Player("Someone", 0, List[Stone]())
        player shouldEqual player2
      }
      "not be equal to another player with a different name" in {
        val player2 = new Player("Someone else", 0, List[Stone]())
        player shouldNot equal(player2)
      }
      "not be equal to another type" in {
        val scrabbleField = new ScrabbleField(1)
        player shouldNot equal(scrabbleField)
      }

      "when adding points" should {
        val player = new Player("Someone", 0, List[Stone]())
        val playerList = List(player)
        "add the points to the player" in {
          val newPlayerList = player.AddPoints(10, player, playerList)
          newPlayerList.head.getPoints should be(10)
        }
      }
      "when creating a list of players" should {
        val playerNames = Vector("Someone", "Someone else")
        "create a list of players with the given names" in {
          val playerList = player.CreatePlayersList(playerNames)
          playerList.head.getName should be("Someone")
          playerList(1).getName should be("Someone else")
        }
      }
      "when sorting a list of players" should {
        val player1 = new Player("Someone", 10, List[Stone]())
        val player2 = new Player("Someone else", 20, List[Stone]())
        val player3 = new Player("Another one", 5, List[Stone]())
        val playerList = List(player1, player2, player3)
        "sort the list by points" in {
          val sortedList = player1.sortListAfterPoints(playerList)
          sortedList.head should be(player2)
          sortedList(1) should be(player1)
          sortedList(2) should be(player3)
        }
      }
      "when getting the next player in a list" should {
        val player1 = new Player("Someone", 10, List[Stone]())
        val player2 = new Player("Someone else", 20, List[Stone]())
        val player3 = new Player("Another one", 5, List[Stone]())
        val playerList = List(player1, player2, player3)
        "return the next player in the list" in {
          val nextPlayer = player1.nextTurn(playerList, player1)
          nextPlayer should be(player2)
        }
        "return the first player if the last player is the last in the list" in {
          val nextPlayer = player3.nextTurn(playerList, player3)
          nextPlayer should be(player1)
        }
      }
      
    }
  }
}
