package de.htwg.se.scrabble.model.gameComponent

import de.htwg.se.scrabble.util.LanguageEnum

trait StoneContainerInterface {
  val stones: List[StoneInterface]
  def gamestartPlayStones(languageEnum: LanguageEnum): List[StoneInterface]
  def drawRandomeStone(Container: StoneContainerInterface): StoneInterface
  def removeStonefromContainer(StoneToRemove : StoneInterface ,Container: StoneContainerInterface): StoneContainerInterface
  def ContainerEmtpy(Container: StoneContainerInterface): Boolean
}
