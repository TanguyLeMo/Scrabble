package de.htwg.se.scrabble.model.gameComponent

import de.htwg.se.scrabble.model.gameComponent.square.ScrabbleSquare

trait MatrixInterface {
  def updateBoard(board: Vector[Vector[ScrabbleSquare]], positions: List[(Int, Int)], factory: () => ScrabbleSquare): Vector[Vector[ScrabbleSquare]]
  def initializeBoard(initialBoard: Vector[Vector[ScrabbleSquare]], positions: List[(Int, Int)], factory: () => ScrabbleSquare): Vector[Vector[ScrabbleSquare]]
  def initializeStandardBoard: Vector[Vector[ScrabbleSquare]]
  def initializeNonStandardBoard: Vector[Vector[ScrabbleSquare]]
  def initializeDiagonalSquares(board: Vector[Vector[ScrabbleSquare]]): Vector[Vector[ScrabbleSquare]]
  def symmetricPositions(basePositions: List[(Int, Int)]): List[(Int, Int)]
  def init(): Matrix
  def placeWord(xPosition: Int, yPosition: Int, direction: Char, word: String): Matrix
  def getSquare(col: Int, row: Int): ScrabbleSquare
  def wordFits(xPosition: Int, yPosition: Int, direction: Char, word: String): Boolean
  def horizontalPlacement(yCoordinate: Int, xCoordinate: Int, word: String, index: Int, updatedMatrix: Matrix): Matrix
  def verticalPlacement(yCoordinate: Int, xCoordinate: Int, word: String, index: Int, updatedMatrix: Matrix): Matrix
  def removeWord(xPosition: Int, yPosition: Int, direction: Char, word: String): Matrix
  def removeHorizontally(ycoordinate: Int, xCoordinate: Int, word: String, index: Int, updatedMatrix: Matrix): Matrix
  def removeVertically(yCoordinate: Int, xCoordinate: Int, word: String, index: Int, updatedMatrix: Matrix): Matrix
  def lettersAlreadyThere(xPosition: Int, yPosition: Int, direction: Char, word: String): List[Stone]
  def equals(obj: Any): Boolean
  def fitsInBounds(xPosition: Int, yPosition: Int, direction: Char, word: String): Boolean
}
