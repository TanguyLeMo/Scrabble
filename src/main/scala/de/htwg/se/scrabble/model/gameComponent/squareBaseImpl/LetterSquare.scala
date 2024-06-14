package de.htwg.se.scrabble.model.gameComponent
package squareBaseImpl

import com.google.inject.Inject
import de.htwg.se.scrabble.model.gameComponent.StoneInterface


abstract class LetterSquare @Inject (val stone : StoneInterface) extends ScrabbleSquare{
   def scoreModifier: Int 
}
