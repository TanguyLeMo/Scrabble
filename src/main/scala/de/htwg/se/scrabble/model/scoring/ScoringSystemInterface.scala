package de.htwg.se.scrabble.model.scoring

import de.htwg.se.scrabble.model.Matrix

trait ScoringSystemInterface {
  def collectPoints(matrix: Matrix, xPosition: Int, yPosition: Int, direction: Char, word: String): Int
}
