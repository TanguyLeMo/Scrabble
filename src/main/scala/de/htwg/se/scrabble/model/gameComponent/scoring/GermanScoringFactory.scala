package de.htwg.se.scrabble.model.gameComponent.scoring

class GermanScoringFactory extends ScoringSystemFactory{
  override def createScoringSystem(): ScoringSystem = new GermanScoringSystem()
}
