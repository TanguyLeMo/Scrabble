package de.htwg.se.scrabble.model.scoring
import de.htwg.se.scrabble.model.Stone

class ItalianScoringSystem extends ScoringSystem{
  //TODO: Implement creation of ItalianScoringSystem
override def determinPoints(stone: Stone): Int = stone.symbol match {
  case 'O' | 'A' | 'I' | 'E' => 1
  case 'C' | 'R' | 'S' | 'T' => 2
  case 'L' | 'M' | 'N' | 'U' => 3
  case 'B' | 'D' | 'F' | 'P' | 'V' => 5
  case 'G' | 'H' | 'Z' => 8
  case 'Q' => 10
  case _ => 0
}

}
