package de.htwg.se.scrabble.model

import de.htwg.se.scrabble.model.square.{ScrabbleSquare, StandardSquareFactory}

class ScrabbleField(val matrix: Matrix, val dictionary: Dictionary, squareFactory: StandardSquareFactory = new StandardSquareFactory()):
  val numOfAlphabet: Int = 26
  val numSymbolPerColumn: Int = Math.ceil(matrix.rows.toDouble / numOfAlphabet.toDouble).toInt + 1
  def this(field: Vector[Vector[ScrabbleSquare]]) = this(new Matrix(field), new Dictionary().readLines)


  def this(rowsAndColumns : Int) = this(new Matrix(Vector.fill(rowsAndColumns, rowsAndColumns)(new StandardSquareFactory().createDoubleSquare(Stone()))).init(rowsAndColumns), new Dictionary().readLines)

  def labelingXAxis(currcolum: Int): String =
    if(currcolum > matrix.columns)""
    else translateLetter(currcolum)+addSpace(numSymbolPerColumn-translateLetter(currcolum).length)+labelingXAxis(currcolum + 1)
  def addSpace(numSpaceToAdd: Int): String = numSpaceToAdd match
    case n if n <= 0 => " "
    case n => " " + addSpace(n - 1)
  def placeWord(xCoordinate: Int, yCoordinte : Int, direction :Char, word : String): ScrabbleField = new ScrabbleField(matrix.placeWord(xCoordinate, yCoordinte, direction, word), dictionary)
  def wordFits(xPosition: Int, yPosition: Int, direction: Char, word: String) : Boolean = matrix.wordFits(xPosition, yPosition, direction, word)
  def translateLetter(n: Int): String = if (n <= 0) "" else translateLetter((n - 1) / 26) + ('A' + (n - 1) % 26).toChar
  def concatenateRows(currentRow: Int): String =
    if (currentRow >= matrix.rows) "" else s"${currentRow.toString.padTo(3, ' ')} ${concatenateColumnsOfCurrentRow(currentRow, 0) + "\n"}${concatenateRows(currentRow + 1)}"
  def concatenateColumnsOfCurrentRow(currentRow: Int, currentColumn: Int): String =
    val nextCol = currentColumn + 1
    if (currentColumn >= matrix.columns) ""
    else matrix.getSquare(currentRow, currentColumn).toString + addSpace(numSymbolPerColumn - 1) + concatenateColumnsOfCurrentRow(currentRow, nextCol)
  override def toString: String =s"    ${labelingXAxis(1)}\n${concatenateRows(0)}"
  override def equals(that: Any): Boolean =
    that match
      case that: ScrabbleField => this.matrix.equals(that.matrix)
      case _ => false
  def addDictionaryWord(word: String): ScrabbleField = new ScrabbleField(matrix, dictionary.addWord(word))
