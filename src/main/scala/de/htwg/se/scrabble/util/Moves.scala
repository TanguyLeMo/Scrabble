package de.htwg.se.scrabble.util

case class CreatePlayersListAsMove(playernames: Vector[String])
case class addWordInDictionaryAsMove(word: String)
case class placeWordsAsMove(yPosition: Int, xPosition : Int, direction :Char, word : String )
