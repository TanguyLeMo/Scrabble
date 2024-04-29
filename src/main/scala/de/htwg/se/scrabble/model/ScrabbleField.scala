package de.htwg.se.scrabble.model
class ScrabbleField(val matrix: Matrix):
  val numOfAlphabet: Int = 26
  val numSymbolPerColumn: Int = Math.ceil(matrix.rows.toDouble / numOfAlphabet.toDouble).toInt + 1
  def this(field: Vector[Vector[Stone]]) = this(new Matrix(field))
  def this(rowsAndColumns : Int) = this(new Matrix(rowsAndColumns))
  def labelingXAxis(currcolum: Int): String =
    if(currcolum > matrix.columns)""
    else translateLetter(currcolum)+addSpace(numSymbolPerColumn-translateLetter(currcolum).length)+labelingXAxis(currcolum + 1)
  def addSpace(numSpaceToAdd: Int): String = numSpaceToAdd match
    case n if n <= 0 => " "
    case n => " " + addSpace(n - 1)
  def placeWord(xCoordinate: Int, yCoordinte : Int, direction :Char, word : String): ScrabbleField = new ScrabbleField(matrix.placeWord(xCoordinate, yCoordinte, direction, word))
  def wordFits(xPosition: Int, yPosition: Int, direction: Char, word: String) : Boolean = matrix.wordFits(xPosition, yPosition, direction, word)
  def translateLetter(n: Int): String = if (n <= 0) "" else translateLetter((n - 1) / 26) + ('A' + (n - 1) % 26).toChar
  def concatenateRows(currentRow: Int): String =
    if (currentRow >= matrix.rows) "" else s"${currentRow.toString.padTo(3, ' ')} ${concatenateColumnsOfCurrentRow(currentRow, 0) + "\n"}${concatenateRows(currentRow + 1)}"
  def concatenateColumnsOfCurrentRow(currentRow: Int, currentColumn: Int): String =
    val nextCol = currentColumn + 1
    if (currentColumn >= matrix.columns) ""
    else matrix.getStone(currentRow, currentColumn).symbol + addSpace(numSymbolPerColumn - 1) + concatenateColumnsOfCurrentRow(currentRow, nextCol)
  override def toString: String =s"    ${labelingXAxis(1)}\n${concatenateRows(0)}"
  def translateCoordinate(coordinate: String): (Int, Int) =
    val coordinates = coordinate.split(" ")
    (coordinates(0).toUpperCase().toCharArray.sum - 'A', coordinates(1).toInt)