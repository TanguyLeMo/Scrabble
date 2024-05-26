package de.htwg.se.scrabble.model.scoring

class EnglishScoringFactory extends ScoringSystemFactory{
  override def createScoringSystem(): ScoringSystem = new EnglishScoringSystem()
}
