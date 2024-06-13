package de.htwg.se.scrabble.model.gameComponent.scoring

trait ScoringSystemFactory {
  def createScoringSystem(): ScoringSystem
}
