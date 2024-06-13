package de.htwg.se.scrabble.model.gameComponent
package squareBaseImpl

import de.htwg.se.scrabble.model.gameComponent.StoneInterface

abstract class WordSquare(val stone : StoneInterface) extends ScrabbleSquare{
  override def scoreModifier: Int 
}
