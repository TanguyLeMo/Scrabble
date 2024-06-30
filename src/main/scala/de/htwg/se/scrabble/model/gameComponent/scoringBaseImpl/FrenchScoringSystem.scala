package de.htwg.se.scrabble.model.gameComponent
package scoringBaseImpl

import de.htwg.se.scrabble.model.gameComponent.StoneInterface


class FrenchScoringSystem extends ScoringSystem{
  override def determinPoints(stone: StoneInterface): Int = stone.symbol match
    case 'A' | 'E' | 'I' | 'O' | 'U' | 'N' | 'R' | 'T' | 'L' | 'S' => 1
    case 'D' | 'M' | 'G' => 2
    case 'B' | 'C' | 'P' => 3
    case 'F' | 'H' | 'V' => 4
    case 'J' | 'Q' => 8
    case 'K' | 'W' | 'X' | 'Y'| 'Z' => 10
    case _ => 0
}
