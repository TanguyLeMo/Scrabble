package de.htwg.se.scrabble.model.gameComponent.scoring

class ItalianScoringFactory extends ScoringSystemFactory{
  override def createScoringSystem(): ScoringSystem = new ItalianScoringSystem()
}
