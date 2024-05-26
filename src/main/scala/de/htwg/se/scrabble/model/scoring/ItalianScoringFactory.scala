package de.htwg.se.scrabble.model.scoring

class ItalianScoringFactory extends ScoringSystemFactory{
  override def createScoringSystem(): ScoringSystem = new ItalianScoringSystem()
}
