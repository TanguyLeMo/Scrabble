package de.htwg.se.scrabble.model
class Matrix(val field: Vector[Vector[Stone]]) :
  val columns: Int = field.length
  val rows: Int = if (columns > 0) field(0).length else 0
  def this(rowsAndColums : Int) = this(Vector.fill(rowsAndColums)(Vector.fill(rowsAndColums)(Stone('_'))))
  def placeWord(xPosition: Int, yPosition: Int, direction: Char, word: String): Matrix =
    if (!wordFits(xPosition, yPosition, direction, word)) this else
    direction.match
      case 'H' => val prefix = this.field(yPosition).take(xPosition)
        val updatedRow: Vector[Stone] = prefix ++ word.toVector.map(j => Stone(j)) ++ field(yPosition).takeRight(columns - (xPosition + word.length))
        new Matrix(field.updated(yPosition, updatedRow))
      case 'V' => val newMatrix = placeVertically(xPosition, yPosition, word, 0, this)
        newMatrix
  def getStone(col: Int, row: Int): Stone = field(row)(col)
  def wordFits(xPosition: Int, yPosition: Int, direction: Char, word: String): Boolean = 
    if (!fitsInBounds(xPosition, yPosition, direction, word)) return false
    direction.toUpper match {
      case 'H' =>
        field(yPosition).slice(xPosition, xPosition + word.length).zipWithIndex.forall {
          case (element, index) => element.symbol == '_' || element.symbol == word.charAt(index)
        }
      case 'V' =>
        field.slice(yPosition, yPosition + word.length).zipWithIndex.forall {
          case (element, index) => element(xPosition).symbol == word.charAt(index) || '_' == element(xPosition).symbol
        }
    }
  def placeVertically(xPosition: Int, yPosition: Int, word: String, index: Int, updatedMatrix: Matrix): Matrix =
    if (word.length <= index) updatedMatrix 
      else 
      val newVector = updatedMatrix.field(yPosition).updated(xPosition, Stone(word.charAt(index)))
      placeVertically(xPosition, yPosition + 1, word, index + 1, Matrix (updatedMatrix.field.updated(yPosition, newVector)))
  override def equals(obj: Any): Boolean = obj match 
    case other: Matrix => this.columns == other.columns && this.rows == other.rows && this.field.zip(other.field).forall
      { case (row1, row2) => row1.zip(row2).forall { case (stone1, stone2) => stone1 == stone2}}
    case _ => false
  def fitsInBounds(xPosition: Int, yPosition: Int, direction: Char, word: String): Boolean = {
    val validX = xPosition >= 0 && xPosition < columns
    val validY = yPosition >= 0 && yPosition < rows
    if (validX && validY)
      direction.toUpper match {
        case 'H' => xPosition + word.length <= rows
        case 'V' => yPosition + word.length <= columns
        case _ => false
      }
    else false
  }

