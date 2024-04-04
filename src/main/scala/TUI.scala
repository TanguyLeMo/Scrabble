class ScrabbleField(row: Int, cols: Int) {
  val numSymolPerColumn: Int = Math.ceil(rows.toDouble / numofAlphabet.toDouble).toInt
  private val columns = cols
  private val rows = row

  private val field = Array.ofDim[Char](rows, cols)
  private val emptyTile = '_'

  def placeTile(row: Int, col: Int, tile: Char): Unit = {
    if (col < 0 || col > cols || row > rows || row < 0)
      return;


    field(row)(col) = tile

  }

  // Method to generate the string representation of the playing field
  override def toString: String = {
    val sb = new StringBuilder()

    // Append column headers (alphabetical)
    sb.append("  ")
    for (col <- 0 until cols) {
      sb.append(' ').append(('A' + col).toChar).append("  ")
    }
    sb.append('\n')


    sb.toString()
  }
  private def gothroughrow(rowidx: Int): String = {
    if (rowidx >= rows) {
      return ""
    }
    val colInString = gothroughColumn(rowidx, 0) + "\n"
    return rowidx.toString.padTo(3, ' ') + " " + colInString + gothroughrow(rowidx + 1)
  }
  private def gothroughColumn(rowidx: Int, colidx: Int): String = {

    if (colidx >= columns) {
      return ""
    }

    if (field(rowidx)(colidx) == 0) {
      if (rowidx.toDouble == (rows - 1) / 2 && colidx.toDouble == (columns - 1) / 2) {
        return Console.YELLOW + emptyTile + addSpace(numSymolPerColumn - 1) + gothroughColumn(rowidx, colidx + 1) + Console.WHITE
      }
      if ((rowidx == 0 && colidx == (columns - 1) / 2.0) || (rowidx == rows - 1 && colidx == (columns - 1) / 2.0)) {
        return Console.RED + emptyTile + addSpace(numSymolPerColumn - 1) + gothroughColumn(rowidx, colidx + 1) + Console.WHITE
      }
      if ((rowidx.toDouble == ((rows - 1) / 2.0) && colidx == 0) || (rowidx.toDouble == ((rows - 1) / 2.0) && colidx == columns - 1)) {
        return Console.RED + emptyTile + addSpace(numSymolPerColumn - 1) + gothroughColumn(rowidx, colidx + 1) + Console.WHITE
      }
      if (rowidx == colidx || (rowidx + colidx) == rows - 1) {
        if (rowidx == 0 || rowidx == rows - 1) {
          return Console.RED + emptyTile + addSpace(numSymolPerColumn - 1) + gothroughColumn(rowidx, colidx + 1) + Console.WHITE
        }
        if (rowidx.toDouble <= ((rows - 1) / 2.0) + 2 && rowidx.toDouble >= ((rows - 1) / 2.0) - 2) {
          return Console.BLUE + emptyTile + addSpace(numSymolPerColumn - 1) + gothroughColumn(rowidx, colidx + 1) + Console.WHITE
        }
        return Console.YELLOW + emptyTile + addSpace(numSymolPerColumn - 1) + gothroughColumn(rowidx, colidx + 1) + Console.WHITE
      }
      return Console.WHITE + emptyTile + addSpace(numSymolPerColumn - 1) + gothroughColumn(rowidx, colidx + 1)
    }
    return Console.WHITE + field(rowidx)(colidx) + addSpace(numSymolPerColumn - 1) + gothroughColumn(rowidx, colidx + 1)
  }


}

object Main extends App {

  private val numbsForBothSide = 15
  private val field = new ScrabbleField(numbsForBothSide,numbsForBothSide)


  field.placeTile(3, 3, 'A')
  field.placeTile(7, 7, 'B')
  field.placeTile(9, 5, 'C')
  println(field)
}
