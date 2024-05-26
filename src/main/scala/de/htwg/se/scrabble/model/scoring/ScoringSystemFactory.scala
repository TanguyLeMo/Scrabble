package de.htwg.se.scrabble.model.scoring

trait ScoringSystemFactory {
  def createScoringSystem(): ScoringSystem
}
