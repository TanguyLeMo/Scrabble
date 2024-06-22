package de.htwg.se.scrabble.model.gameState

import de.htwg.se.scrabble.model.gameComponent.ScrabbleFieldInterface

trait GameStateInterface {
  def save(scrabbleField: ScrabbleFieldInterface): Boolean
  def load: ScrabbleFieldInterface
}
