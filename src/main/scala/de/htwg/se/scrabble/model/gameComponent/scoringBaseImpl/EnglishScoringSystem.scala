package de.htwg.se.scrabble.model.gameComponent
package scoringBaseImpl

import de.htwg.se.scrabble.model.gameComponent.gameComponentBaseImpl.Stone


class EnglishScoringSystem extends ScoringSystem{
  
  override def determinPoints(stone : StoneInterface): Int = stone.symbol.toUpper match
    case 'A' | 'E' | 'I' | 'O' | 'U' | 'L' | 'N' | 'S' | 'T' | 'R' => 1
    case 'D' | 'G' => 2
    case 'B' | 'C' | 'M' | 'P' => 3
    case 'F' | 'H' | 'V' | 'W' | 'Y' => 4
    case 'K' => 5
    case 'J' | 'X' => 8
    case 'Q' | 'Z' => 10
    case _ => 0
}
