import scala.language.postfixOps

/**
 * Represents a Scrabble game field.
 *
 * @param row  Number of rows in the field.
 * @param cols Number of columns in the field.
 */
class ScrabbleField(row: Int, cols: Int) {
  val numSymolPerColumn: Int = Math.ceil(rows.toDouble / numofAlphabet.toDouble).toInt
  private val columns = cols
  private val rows = row
  private val field = Array.ofDim[Char](rows, cols)
  private val emptyTile = '_'
  private val numofAlphabet = 26;

  /**
   * Place a tile on the field at specified row and column.
   *
   * @param row  Row index.
   * @param col  Column index.
   * @param tile Character representing the tile to be placed.
   */
  def placeTile(row: Int, col: Int, tile: Char): Unit = {
    if (col < 0 || col > cols || row > rows || row < 0)
      return;
    field(row)(col) = tile
  }

  override def toString: String = {
    val sb = new StringBuilder()
    sb.append(" ".padTo(4, ' ')).append(labelingXAxis(1))
    sb.append('\n')
    sb.append(gothroughrow(0))
    sb.toString()
  }

  /**
   * Generate the alphabet string representation for the given column index.
   *
   * @param currcolum Current column index.
   * @return Alphabet string representation for the column.
   */
  private def labelingXAxis(currcolum: Int): String = {
    if (currcolum > columns) {
      return "";
    }
    val letter: String = getLetter(currcolum)
    return letter + addSpace(numSymolPerColumn - letter.length) + (labelingXAxis(currcolum + 1));
  }

  /**
   * Add spaces to the string.
   *
   * @param numSpaceToAdd Number of spaces to add.
   * @return String with added spaces.
   */
  private def addSpace(numSpaceToAdd: Int): String = {
    if (numSpaceToAdd <= 0)
      return " "
    return " " + addSpace(numSpaceToAdd - 1);
  }

  /**
   * Generate A letter representation of an Integer.
   *
   * @param n
   * @return If the given number ist greater than the alphabet (26),
   *         * it will concatenate the letters numerically. E.g. 0 = A, 1 = B, ... 26 = AA, 27 = AB, ...
   */
  private def getLetter(n: Int): String = {
    if (n <= 0) {
      ""
    } else {
      val remainder = (n - 1) % 26
      getLetter((n - 1) / 26) + ('A' + remainder).toChar
    }
  }

  /**
   * Get the maximum number of characters that can be accommodated per column.
   *
   * @return Maximum number of characters per column.
   */
  private def getMaxNumOfChar(): Int = {
    val returnval: Int = Math.ceil(columns / numofAlphabet).asInstanceOf[Int]
    returnval
  }

  /**
   * Helper method to generate string representation of each row.
   *
   * @param rowidx
   * @return String representation of current State of the playing field.
   */
  private def gothroughrow(rowidx: Int): String = {
    if (rowidx >= rows) {
      return ""
    }
    val colInString = gothroughColumn(rowidx, 0) + "\n"
    return rowidx.toString.padTo(3, ' ') + " " + colInString + gothroughrow(rowidx + 1)
  }

  /**
   * Helper method to generate String representation of each row. Works recursively
   *
   * @param rowidx Index of current row.
   * @param colidx Index of current column
   * @return String representation of Column
   */
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

  private val numbsForBothSide = 27


  private val field = new ScrabbleField(numbsForBothSide, numbsForBothSide)


  field.placeTile(3, 3, 'A')
  field.placeTile(7, 7, 'B')
  field.placeTile(9, 5, 'C')
  println(field)
}
