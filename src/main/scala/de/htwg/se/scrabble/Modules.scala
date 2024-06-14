package de.htwg.se.scrabble

import de.htwg.se.scrabble.model.gameComponent.{DictionaryInterface, MatrixInterface, PlayerInterface, ScoringSystemInterface, ScrabbleFieldInterface, ScrabbleSquareInterface, StoneContainerInterface, StoneInterface, squareFactoryInterface}
import de.htwg.se.scrabble.model.gameComponent.gameComponentBaseImpl.{Dictionary, Matrix, Player, ScrabbleField, Stone, StoneContainer}
import de.htwg.se.scrabble.model.gameComponent.scoringBaseImpl.{EnglishScoringSystem, ScoringSystem}
import de.htwg.se.scrabble.model.gameComponent.squareBaseImpl.StandardSquare
import de.htwg.se.scrabble.model.languageComponent.languages.EnglishStrategy
import de.htwg.se.scrabble.model.gameComponent.squareBaseImpl.StandardSquareFactory


object default {
  val defaultSize = 15
  val emptySquare = new StandardSquare(Stone('_'))
  val emptyRow = Vector.fill(defaultSize)(emptySquare)
  val emptyField = Vector.fill(defaultSize)(emptyRow)
  val field = new Matrix(emptyField).init().field
  given DictionaryInterface = Dictionary()
  given MatrixInterface = Matrix(field)
  given PlayerInterface = Player("Player", 0, Nil)
  given ScrabbleFieldInterface = ScrabbleField(defaultSize)
  given StoneInterface = Stone('_')
  given StoneContainerInterface = StoneContainer(Nil)
  given ScoringSystemInterface = EnglishScoringSystem()
  given ScrabbleSquareInterface = StandardSquare(new Stone('_'))
  given squareFactoryInterface = StandardSquareFactory()
}
