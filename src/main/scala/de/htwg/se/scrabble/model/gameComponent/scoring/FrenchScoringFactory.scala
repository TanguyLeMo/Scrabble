package de.htwg.se.scrabble.model.gameComponent.scoring

class FrenchScoringFactory extends ScoringSystemFactory{
  override def createScoringSystem(): ScoringSystem = new FrenchScoringSystem()
}
