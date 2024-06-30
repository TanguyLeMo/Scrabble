package de.htwg.se.scrabble.model

import de.htwg.se.scrabble.model.gameComponent.gameComponentBaseImpl.StoneContainer
import de.htwg.se.scrabble.util.LanguageEnum.{ENGLISH, FRENCH, GERMAN, ITALIAN}
import org.scalatest.matchers.must.Matchers
import org.scalatest.wordspec.AnyWordSpec

class StoneContainerSpec extends AnyWordSpec with Matchers {
  "A StoneContainer" when {
    val stoneContainer = new StoneContainer(List.empty)
    val stoneContainerEnglish = new StoneContainer(stoneContainer.gamestartPlayStones(ENGLISH))
    val stoneContainerGerman = new StoneContainer(stoneContainer.gamestartPlayStones(GERMAN))
    val stoneContainerFrench = new StoneContainer(stoneContainer.gamestartPlayStones(FRENCH))
    val stoneContainerItalian = new StoneContainer(stoneContainer.gamestartPlayStones(ITALIAN))
    val stoneContainernull = new StoneContainer(stoneContainer.gamestartPlayStones(null))

    "new" should {
      "create a list of stones" in {
        stoneContainer.stones.length must be(0)
      }
    }
    "gamestartPlayStones" should {
      "create a list of stones" in {
        stoneContainerEnglish.stones.length must not be 0
        stoneContainerGerman.stones.length must not be 0
        stoneContainerFrench.stones.length must not be 0
        stoneContainerItalian.stones.length must not be 0
      }
    }
    "drawRandomeStone" should {
      "draw a random stone" in {
        val stone = stoneContainerEnglish.drawRandomeStone(stoneContainerEnglish)
        stoneContainerEnglish.stones.contains(stone) must be(true)
      }
    }
    "removeStonefromContainer" should {
      "remove a stone from the container" in {
        val stone = stoneContainerEnglish.drawRandomeStone(stoneContainerEnglish)
        val newContainer = stoneContainerEnglish.removeStonefromContainer(stone, stoneContainerEnglish)
        newContainer.stones.contains(stone) must be (true)
      }
    }
    "ContainerEmtpy" should {
      "check if the container is empty" in {
        stoneContainerEnglish.ContainerEmtpy(stoneContainerEnglish) must be(false)
        val stone = stoneContainerEnglish.drawRandomeStone(stoneContainerEnglish)
        val newContainer = stoneContainerEnglish.removeStonefromContainer(stone, stoneContainerEnglish)
        newContainer.ContainerEmtpy(newContainer) must be(false)
        val emptyContainer = new StoneContainer(List.empty)
        emptyContainer.ContainerEmtpy(emptyContainer) must be(true)
      }
    }
  }

}
