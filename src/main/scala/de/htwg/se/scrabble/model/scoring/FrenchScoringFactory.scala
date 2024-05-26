package de.htwg.se.scrabble.model.scoring

class FrenchScoringFactory extends ScoringSystemFactory{
  override def createScoringSystem(): ScoringSystem = new FrenchScoringSystem()
}
