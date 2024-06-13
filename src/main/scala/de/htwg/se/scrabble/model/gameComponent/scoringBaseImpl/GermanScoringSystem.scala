package de.htwg.se.scrabble.model.gameComponent
package scoringBaseImpl

import de.htwg.se.scrabble.model.gameComponent.StoneInterface


class GermanScoringSystem extends ScoringSystem{
  override def determinPoints(stone: StoneInterface): Int = stone.symbol.toUpper match {
  case 'E' | 'N' | 'S' | 'I' | 'R' | 'T' | 'U' | 'A' | 'D' => 1
  case 'H' | 'G' | 'L' | 'O' => 2
  case 'M' | 'B' | 'W' | 'Z' => 3
  case 'C' | 'F' | 'K' | 'P' => 4
  case 'Ä' | 'J' | 'Ü' | 'V' => 6
  case 'Ö' | 'X' | 'ß'=> 8
  case 'Q' | 'Y' => 10
  case _ => 0
}

}
