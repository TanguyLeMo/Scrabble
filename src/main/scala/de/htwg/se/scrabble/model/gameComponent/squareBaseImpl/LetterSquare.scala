package de.htwg.se.scrabble.model.gameComponent
package squareBaseImpl

import de.htwg.se.scrabble.model.gameComponent.StoneInterface


abstract class LetterSquare(val stone : StoneInterface) extends ScrabbleSquare{
   def scoreModifier: Int 
}
