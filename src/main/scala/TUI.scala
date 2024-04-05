import scala.language.postfixOps

class ScrabbleField(row: Int, cols: Int) {
  val columns: Int = cols
  val rows: Int = row
  val field: Array[Array[Char]] = Array.ofDim[Char](rows, cols)
  val emptyTile = '_'
  val numofAlphabet = 26
  val numSymolPerColumn: Int = 2 + Math.ceil(rows.toDouble / numofAlphabet.toDouble).toInt

  def placeTile(row: Int, col: Int, tile: Char): Unit =
    if (0 <= col && col < cols && 0 <= row && row < rows)
      field(row)(col) = tile

  override def toString: String =
    s"    ${labelingXAxis(1)}\n${goThroughRow(0)}"

  def labelingXAxis(currcolum: Int): String =
    if (currcolum > columns) "" else getLetter(currcolum) + addSpace(numSymolPerColumn - getLetter(currcolum).length) + labelingXAxis(currcolum + 1)

  def addSpace(numSpaceToAdd: Int): String = numSpaceToAdd match {
    case n if n <= 0 => " "
    case n => " " + addSpace(n - 1)
  }

  def getLetter(n: Int): String =
    if (n <= 0) "" else getLetter((n - 1) / 26) + ('A' + (n - 1) % 26).toChar

  def getMaxNumOfChar: Int = Math.ceil(columns / numofAlphabet).asInstanceOf[Int]

  def goThroughRow(currentRow: Int): String =
    if (currentRow >= rows) "" else s"${currentRow.toString.padTo(3, ' ')} ${gothroughColumn(currentRow, 0) + "\n"}${goThroughRow(currentRow + 1)}"

  def gothroughColumn(currentRow: Int, currentColumn: Int): String = {
    val nextCol = currentColumn + 1
    val midRow = (rows - 1) / 2
    val midCol = (columns - 1) / 2

    if (currentColumn >= columns) ""
    else if (field(currentRow)(currentColumn) == 0) {
      val color = if (currentRow == midRow && currentColumn == midCol) Console.YELLOW
      else if ((currentRow == 0 || currentRow == rows - 1) && currentColumn == midCol) Console.RED
      else if ((currentRow == midRow && currentColumn == 0) || (currentRow == midRow && currentColumn == columns - 1)) Console.RED
      else if (currentRow == currentColumn || (currentRow + currentColumn) == rows - 1) {
        if (currentRow == 0 || currentRow == rows - 1) Console.RED
        else if (currentRow <= midRow + 2 && currentRow >= midRow - 2) Console.BLUE
        else Console.YELLOW
      } else Console.WHITE
      color + emptyTile + addSpace(numSymolPerColumn - 1) + gothroughColumn(currentRow, nextCol) + Console.WHITE
    } else Console.WHITE + field(currentRow)(currentColumn) + addSpace(numSymolPerColumn - 1) + gothroughColumn(currentRow, nextCol)
  }
}

object Main extends App {
  val numbsForBothSide = 51
  val field = new ScrabbleField(numbsForBothSide, numbsForBothSide)

  print("there")
  field.placeTile(3, 3, 'A')
  field.placeTile(7, 7, 'B')
  field.placeTile(9, 5, 'C')
  println(field)
}
