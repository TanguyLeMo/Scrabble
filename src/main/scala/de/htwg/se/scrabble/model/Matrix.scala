package de.htwg.se.scrabble.model

import de.htwg.se.scrabble.model.square.*

class Matrix(val field: Vector[Vector[ScrabbleSquare]]):
  val columns, rows, rowsAndColumn: Int = field.length
  private val letterFactory: SquareFactory = new LetterSquareFactory
  private val wordFactory: SquareFactory = new WordSquareFactory
  private val standardSquareFactory: SquareFactory = new StandardSquareFactory
  private val center = rowsAndColumn / 2
  private def updateBoard(board: Vector[Vector[ScrabbleSquare]], positions: List[(Int, Int)], factory: () => ScrabbleSquare): Vector[Vector[ScrabbleSquare]] =
    positions.foldLeft(board) { case (b, (row, col)) =>
      b.updated(row, b(row).updated(col, factory()))
    }
  private def initializeBoard(initialBoard: Vector[Vector[ScrabbleSquare]], positions: List[(Int, Int)], factory: () => ScrabbleSquare): Vector[Vector[ScrabbleSquare]] =
    updateBoard(initialBoard, symmetricPositions(positions), factory)

  private def initializeStandardBoard: Vector[Vector[ScrabbleSquare]] = {
    val baseDoubleLetterPositions = List((0, 3), (2, 6), (3, 0), (6, 2), (6, 6), (7, 3), (8, 2))
    val baseTripleLetterPositions = List((1, 5), (5, 1), (5, 5))
    val baseDoubleWordPositions = List((1, 1), (2, 2), (3, 3), (4, 4))
    val baseTripleWordPositions = List((0, 0), (0, 7))
    val initialBoard = Vector.fill(rowsAndColumn, rowsAndColumn)(standardSquareFactory.createDoubleSquare(new Stone))
    val boardWithDoubleLetters = initializeBoard(initialBoard, baseDoubleLetterPositions, () => letterFactory.createDoubleSquare(new Stone))
    val boardWithTripleLetters = initializeBoard(boardWithDoubleLetters, baseTripleLetterPositions, () => letterFactory.createTripleSquare(new Stone))
    val boardWithDoubleWords = initializeBoard(boardWithTripleLetters, baseDoubleWordPositions, () => wordFactory.createDoubleSquare(new Stone))
    initializeBoard(boardWithDoubleWords, baseTripleWordPositions, () => wordFactory.createTripleSquare(new Stone))
  }

  private def initializeNonStandardBoard: Vector[Vector[ScrabbleSquare]] = {
    val initBoard = Vector.fill(rowsAndColumn, rowsAndColumn)(standardSquareFactory.createDoubleSquare(new Stone))

    def updateAtPosition(board: Vector[Vector[ScrabbleSquare]], row: Int, col: Int, square: ScrabbleSquare): Vector[Vector[ScrabbleSquare]] =
      board.updated(row, board(row).updated(col, square))

    val tripleSquarePositions = List((0, 0), (0, rowsAndColumn - 1), (rowsAndColumn - 1, 0), (rowsAndColumn - 1, rowsAndColumn - 1), (center, 0), (center, rowsAndColumn - 1), (0, center), (rowsAndColumn - 1, center))
    val boardWithDiagonalSquares = initializeDiagonalSquares(initBoard)
    tripleSquarePositions.foldLeft(boardWithDiagonalSquares) { case (board, (row, col)) =>
      updateAtPosition(board, row, col, wordFactory.createTripleSquare(new Stone))
    }
  }
  //override def toString: String = field.map(_.map(_.toString).mkString(" ")).mkString("\n")
  private def initializeDiagonalSquares(board: Vector[Vector[ScrabbleSquare]]): Vector[Vector[ScrabbleSquare]] = {
    val topLeftBottomRightDiagonal = (0 until rowsAndColumn).map(i => (i, i)).toList
    val topRightBottomLeftDiagonal = (0 until rowsAndColumn).map(i => (i, rowsAndColumn - 1 - i)).toList

    def updateAtPosition(board: Vector[Vector[ScrabbleSquare]], row: Int, col: Int, square: ScrabbleSquare): Vector[Vector[ScrabbleSquare]] =
      board.updated(row, board(row).updated(col, square))

    val updatedBoard1 = topLeftBottomRightDiagonal.foldLeft(board) { case (b, (row, col)) =>
      updateAtPosition(b, row, col, wordFactory.createTripleSquare(new Stone))
    }
    topRightBottomLeftDiagonal.foldLeft(updatedBoard1) { case (b, (row, col)) =>
      updateAtPosition(b, row, col, wordFactory.createTripleSquare(new Stone))
    }
  }
  private def symmetricPositions(basePositions: List[(Int, Int)]): List[(Int, Int)] =
    basePositions.flatMap { case (row, col) =>
      List(
        (row, col),
        (row, rowsAndColumn - 1 - col),
        (rowsAndColumn - 1 - row, col),
        (rowsAndColumn - 1 - row, rowsAndColumn - 1 - col)
      ).distinct
    }.distinct
  
  def init(): Matrix = new Matrix(if(rows == 15) initializeStandardBoard else initializeNonStandardBoard)

  def placeWord(xPosition: Int, yPosition: Int, direction: Char, word: String): Matrix =
    if (!wordFits(xPosition, yPosition, direction, word)) this else
    direction.match
      case 'V' => val NewMatrix = verticalPlacement(xPosition, yPosition, word, 0, this); NewMatrix
      case 'H' => val newMatrix = horizontalPlacement(xPosition, yPosition, word, 0, this); newMatrix


  def getSquare(col: Int, row: Int): ScrabbleSquare = field(row)(col)


  def wordFits(xPosition: Int, yPosition: Int, direction: Char, word: String): Boolean =
    if (!fitsInBounds(xPosition, yPosition, direction, word)) return false
    direction.toUpper match {
      case 'V' =>
        field(yPosition).slice(xPosition, xPosition + word.length).zipWithIndex.forall {
          case (element, index) => element.letter.symbol == '_' || element.letter.symbol == word.charAt(index)
        }
      case 'H' =>
        field.slice(yPosition, yPosition + word.length).zipWithIndex.forall {
          case (element, index) => element(xPosition).letter.symbol == word.charAt(index) || '_' == element(xPosition).letter.symbol
        }
    }

  def horizontalPlacement(yCoordinate: Int, xCoordinate: Int, word: String, index: Int, updatedMatrix: Matrix): Matrix =
    if (word.length <= index) updatedMatrix
      else
      val newVector = updatedMatrix.field(xCoordinate).updated(yCoordinate, field(xCoordinate)(yCoordinate).update(Stone(word.charAt(index))))
      horizontalPlacement(yCoordinate, xCoordinate + 1, word, index + 1, Matrix (updatedMatrix.field.updated(xCoordinate, newVector)))

  def verticalPlacement(yCoordinate: Int, xCoordinate: Int, word: String, index: Int, updatedMatrix: Matrix): Matrix =
    if (word.length <= index) updatedMatrix
    else {
      val newVector = updatedMatrix.field(xCoordinate).updated(yCoordinate, field(xCoordinate)(yCoordinate).update(Stone(word.charAt(index).toUpper)))
      verticalPlacement(yCoordinate + 1, xCoordinate, word, index + 1, Matrix(updatedMatrix.field.updated(xCoordinate, newVector)))
    }

  
  
  def removeWord(xPosition: Int, yPosition: Int, direction: Char, word: String): Matrix = if(direction == 'H') removeHorizontally(xPosition, yPosition, word, 0, this) else removeVertically(xPosition, yPosition, word, 0, this)

    def removeHorizontally(ycoordinate: Int, xCoordinate: Int, word: String, index: Int, updatedMatrix: Matrix): Matrix =
      if (word.length <= index) updatedMatrix
      else
        val newVector = updatedMatrix.field(xCoordinate).updated(ycoordinate, field(xCoordinate)(ycoordinate).update(Stone(word.charAt(index))))
        removeHorizontally(ycoordinate, xCoordinate + 1, word, index + 1, Matrix(updatedMatrix.field.updated(xCoordinate, newVector)))

  def removeVertically(yCoordinate: Int, xCoordinate: Int, word: String, index: Int, updatedMatrix: Matrix): Matrix =
    if (word.length <= index) updatedMatrix
    else {
      val newVector = updatedMatrix.field(xCoordinate).updated(yCoordinate, field(xCoordinate)(yCoordinate).update(Stone(word.charAt(index).toUpper)))
      removeVertically(yCoordinate + 1, xCoordinate, word, index + 1, Matrix(updatedMatrix.field.updated(xCoordinate, newVector)))
    }

  def lettersAlreadyThere(xPosition: Int, yPosition: Int, direction: Char, word: String): List[Stone] = {
    direction.toUpper match {
      case 'H' =>
        word.zipWithIndex.flatMap {
          case (char, index) =>
            val element = field(yPosition)(xPosition + index)
            if (element.letter.symbol == char) Some(element.letter) else None
        }.toList
      case 'V' =>
        word.zipWithIndex.flatMap {
          case (char, index) =>
            val element = field(yPosition + index)(xPosition)
            if (element.letter.symbol == char) Some(element.letter) else None
        }.toList
    }
  }
  override def equals(obj: Any): Boolean = obj match
    case other: Matrix => this.columns == other.columns && this.rows == other.rows && this.field.zip(other.field).forall
      { case (row1, row2) => row1.zip(row2).forall { case (square1, square2) => square1.letter == square2.letter}}
    case _ => false

  def fitsInBounds(xPosition: Int, yPosition: Int, direction: Char, word: String): Boolean = {
    val validX = xPosition >= 0 && xPosition < columns
    val validY = yPosition >= 0 && yPosition < rows
    if (validX && validY)
      direction.toUpper match {
        case 'V' => xPosition + word.length <= rows
        case 'H' => yPosition + word.length <= columns
        case _ => false
      }
    else false
  }

