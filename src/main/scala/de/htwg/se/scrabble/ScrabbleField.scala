package de.htwg.se.scrabble

import scala.language.postfixOps

class ScrabbleField(field: Vector[Vector[Char]]) {
  val columns, rows = field.length
  val numofAlphabet = 26
  val numSymbolPerColumn: Int = 2 + Math.ceil(rows.toDouble / numofAlphabet.toDouble).toInt

  def placeTile(row: Int, col: Int, tile: Char): ScrabbleField =
    if (0 <= col && col < columns && 0 <= row && row < rows) {
      val newRow = field(row).updated(col, tile)
      new ScrabbleField(field.updated(row, newRow))
    } else{
      this
    }
  def placeWord(xPosition: Int, yPosition: Int, direction : Char, word: String): ScrabbleField = {
    if(!wordFits(xPosition, yPosition, direction, word)){
      return this
    }
    direction.match
      case 'H' => val prefix = this.field(yPosition).take(xPosition)
        val updatedRow : Vector[Char] = prefix ++ word.toVector ++ this.field(yPosition).takeRight(columns - (xPosition + word.length))
        new ScrabbleField(field.updated(yPosition, updatedRow))
      case 'V' => val newMatrix = placeVertically(xPosition, yPosition, word, 0, field)
                  new ScrabbleField(newMatrix)
      case _ => this
  }

   def placeVertically(xPosition: Int, yPosition: Int, word: String, index : Int, matrix: Vector[Vector[Char]]): Vector[Vector[Char]] = {
    if(word.length <= index){
      matrix
    }else{
      val newVector = matrix(yPosition).updated(xPosition, word.charAt(index))
      placeVertically(xPosition, yPosition+1, word, index+1, matrix.updated(yPosition, newVector))
    }
  }

  def fitsInBounds(xPosition: Int, yPosition: Int, direction: Char, word: String): Boolean = {
    val validX = xPosition >= 0 && xPosition < columns
    val validY = yPosition >= 0 && yPosition < rows
    if (validX && validY) {
      direction.toUpper match {
        case 'H' => xPosition + word.length <= rows
        case 'V' => yPosition + word.length <= columns
        case _ => false
      }
    } else {
      false
    }
  }


  def wordFits(xPosition: Int, yPosition: Int, direction: Char, word: String): Boolean = {
    if (!fitsInBounds(xPosition, yPosition, direction, word )) return false
    direction.toUpper match {
      case 'H' =>
        field(yPosition).slice(xPosition, xPosition + word.length).zipWithIndex.forall {
          case (element, index) => element == '_' || element == word.charAt(index)
        }
      case 'V' =>
        field.slice(yPosition, yPosition + word.length).zipWithIndex.forall {
          case (element, index) => '_' == element(xPosition) || element(xPosition) == word.charAt(index)
        }
      case _ => false
    }
  }
  def playfield: Vector[Vector[Char]] = field

  override def toString: String =
    s"    ${labelingXAxis(1)}\n${concatenateCurrentRow(0)}"

  def labelingXAxis(currcolum: Int): String =
    if (currcolum > columns) "" else getLetter(currcolum) + addSpace(numSymbolPerColumn - getLetter(currcolum).length) + labelingXAxis(currcolum + 1)

  def addSpace(numSpaceToAdd: Int): String = numSpaceToAdd match {
    case n if n <= 0 => " "
    case n => " " + addSpace(n - 1)
  }

  def getLetter(n: Int): String =
    if (n <= 0) "" else getLetter((n - 1) / 26) + ('A' + (n - 1) % 26).toChar

  def concatenateCurrentRow(currentRow: Int): String =
    if (currentRow >= rows) "" else s"${currentRow.toString.padTo(3, ' ')} ${concatenateColumnsOfCurrentRow(currentRow, 0) + "\n"}${concatenateCurrentRow(currentRow + 1)}"

  def concatenateColumnsOfCurrentRow(currentRow: Int, currentColumn: Int): String = {
    val nextCol = currentColumn + 1
    val midRow = (rows - 1) / 2
    val midCol = (columns - 1) / 2

    if (currentColumn >= columns) ""
    else
      field(currentRow)(currentColumn) + addSpace(numSymbolPerColumn - 1) + concatenateColumnsOfCurrentRow(currentRow, nextCol)
  }
}


  object Demo{
    def main(args:Array[String]): Unit = {
      val numbsForBothSide = 42
      val field = new ScrabbleField(Vector.fill(3)(Vector.fill(3)('_')))
      field.placeTile(3, 3, 'A')
      field.placeTile(7, 7, 'B')
      val newField = field.placeTile(1, 1, 'B')
      println(newField)
    }
  }
