package de.htwg.se.scrabble.model.gameComponent


trait MatrixInterface {
  val columns, rows, rowsAndColumn: Int
  val field: Vector[Vector[ScrabbleSquare]]
  
  def updateBoard(board: Vector[Vector[ScrabbleSquare]], positions: List[(Int, Int)], factory: () => ScrabbleSquare): Vector[Vector[ScrabbleSquare]]
  def init(): MatrixInterface
  def placeWord(xPosition: Int, yPosition: Int, direction: Char, word: String): MatrixInterface
  def getSquare(col: Int, row: Int): ScrabbleSquare
  def wordFits(xPosition: Int, yPosition: Int, direction: Char, word: String): Boolean
  def removeWord(xPosition: Int, yPosition: Int, direction: Char, word: String): MatrixInterface
  def lettersAlreadyThere(xPosition: Int, yPosition: Int, direction: Char, word: String): List[StoneInterface]
  def equals(obj: Any): Boolean
  
}
