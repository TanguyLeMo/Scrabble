package de.htwg.se.scrabble.model.scoring
import de.htwg.se.scrabble.model.Stone

class FrenchScoringSystem extends ScoringSystem{
  override def determinPoints(stone: Stone): Int = stone.symbol match
    case 'A' | 'E' | 'I' | 'O' | 'U' | 'N' | 'R' | 'T' | 'L' | 'S' => 1
    case 'D' | 'M' | 'G' => 2
    case 'B' | 'C' | 'P' => 3
    case 'F' | 'H' | 'V' => 4
    case 'J' | 'Q' => 8
    case 'K' | 'W' | 'X' | 'Y'| 'Z' => 10
}
