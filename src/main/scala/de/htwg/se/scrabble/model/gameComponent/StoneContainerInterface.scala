package de.htwg.se.scrabble.model.gameComponent

import de.htwg.se.scrabble.util.LanguageEnum

trait StoneContainerInterface {
  def gamestartPlayStones(languageEnum: LanguageEnum): List[Stone]
  def drawRandomeStone(Container: StoneContainer): Stone
  def removeStonefromContainer(StoneToRemove : Stone,Container: StoneContainer): StoneContainer
  def ContainerEmtpy(Container: StoneContainer): Boolean
}
