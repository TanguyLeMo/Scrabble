package de.htwg.se.scrabble.util

import com.google.inject.Provider

class IntegerProvider extends Provider[Integer] {
  override def get(): Integer = {
    // return a new Integer instance
    // replace 0 with the actual integer you want to use
    0
  }
}
