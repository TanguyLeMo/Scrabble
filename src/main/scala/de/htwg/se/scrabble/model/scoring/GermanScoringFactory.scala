package de.htwg.se.scrabble.model.scoring

class GermanScoringFactory extends ScoringSystemFactory{
  override def createScoringSystem(): ScoringSystem = new GermanScoringSystem()
}
