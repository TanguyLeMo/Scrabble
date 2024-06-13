package de.htwg.se.scrabble.model.gameComponent
package scoringBaseImpl

import de.htwg.se.scrabble.model.gameComponent.StoneInterface



class ItalianScoringSystem extends ScoringSystem{
override def determinPoints(stone: StoneInterface): Int = stone.symbol match {
  case 'O' | 'A' | 'I' | 'E' => 1
  case 'C' | 'R' | 'S' | 'T' => 2
  case 'L' | 'M' | 'N' | 'U' => 3
  case 'B' | 'D' | 'F' | 'P' | 'V' => 5
  case 'G' | 'H' | 'Z' => 8
  case 'Q' => 10
  case _ => 0
}

}
