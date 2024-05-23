import de.htwg.se.scrabble.model.Stone
import de.htwg.se.scrabble.model.square._
class MatrixTemp(rowsAndColumn: Int) {
  private val letterFactory = new LetterSquareFactory
  private val wordFactory = new WordSquareFactory
  private val standardSquareFactory = new StandardSquareFactory
  private val center = rowsAndColumn / 2
  private val field: Vector[Vector[ScrabbleSquare]] =
    if (rowsAndColumn == 15) initializeStandardBoard
    else initializeNonStandardBoard
  private def symmetricPositions(basePositions: List[(Int, Int)]): List[(Int, Int)] =
    basePositions.flatMap { case (row, col) =>
      List(
        (row, col),
        (row, rowsAndColumn - 1 - col),
        (rowsAndColumn - 1 - row, col),
        (rowsAndColumn - 1 - row, rowsAndColumn - 1 - col)
      ).distinct
    }.distinct
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
  override def toString: String = field.map(_.map(_.toString).mkString(" ")).mkString("\n")
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
}
@main def run(): Unit = {
  val matrix = new MatrixTemp(15)
  println(matrix)
}
