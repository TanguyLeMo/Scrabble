class ScrabbleField(row: Int, cols: Int) {
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


    for (row <- 0 until rows) {
      sb.append((row + 1).toString.padTo(3, ' '))
      for (col <- 0 until cols) {
        sb.append(if (field(row)(col) == 0) emptyTile else field(row)(col)).append("  " )
      }
      sb.append('\n')
    }
    sb.toString()
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
