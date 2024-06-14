package de.htwg.se.scrabble.util
import com.google.inject.Provider

class CharacterProvider extends Provider[Character] {
  override def get(): Character = {
    '_'
  }
}