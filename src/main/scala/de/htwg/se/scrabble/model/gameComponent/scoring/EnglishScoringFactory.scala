package de.htwg.se.scrabble.model.gameComponent.scoring

class EnglishScoringFactory extends ScoringSystemFactory{
  override def createScoringSystem(): ScoringSystem = new EnglishScoringSystem()
}
