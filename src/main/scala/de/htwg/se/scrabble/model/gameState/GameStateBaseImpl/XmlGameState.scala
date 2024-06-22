package de.htwg.se.scrabble.model.gameState.GameStateBaseImpl

import de.htwg.se.scrabble.model.gameComponent.ScrabbleFieldInterface
import de.htwg.se.scrabble.model.gameState.GameStateInterface

class XmlGameState extends GameStateInterface{
  override def save(scrabbleField: ScrabbleFieldInterface): Boolean = {
    false
  }

  override def load: ScrabbleFieldInterface = {
    null
  }

}
