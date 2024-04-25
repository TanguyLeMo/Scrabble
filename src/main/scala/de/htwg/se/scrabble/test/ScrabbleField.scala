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
    }
    this


  def placeWord(xPosition: Int, yPosition: Int, direction : Char, word: String): ScrabbleField = {
    if(!wordFits(xPosition, yPosition, direction, word)){
      return this
    }
    direction.match
      case 'V' => val prefix = this.field(yPosition).take(xPosition)
        val updatedRow : Vector[Char] = prefix ++ word.toVector.padTo(columns, '_')
        new ScrabbleField(field.updated(yPosition, updatedRow))
      case 'H' => val tmp= field.patch(yPosition,word.toCharArray.toVector, word.length).map{
        case row: Vector[Char] => row
      }
        new ScrabbleField(tmp)
      //new ScrabbleField(field.patch(yPosition, word.toCharArray, word.length))
      case _ => this
  }

  def fitsInBound(xPosition: Int, yPosition: Int, direction: Char, word: String): Boolean = {
    val validX = xPosition >= 0 && xPosition < columns
    val validY = yPosition >= 0 && yPosition < rows
    validX && validY && (direction.toUpper match {
      case 'V' => yPosition + word.length <= rows
      case 'H' => xPosition + word.length <= columns
      case _ => false
    })
  }


  def wordFits(xPosition: Int, yPosition: Int, direction: Char, word: String): Boolean = {

    if (!fitsInBound(xPosition, yPosition, direction, word)) return false

    direction.toUpper match {
      case 'V' =>
        field(yPosition).slice(xPosition, xPosition + word.length).zipWithIndex.forall {
          case (element, index) => element == '_' || element == word.charAt(index)
        }
      case 'H' =>
        field.slice(yPosition, yPosition + word.length).zipWithIndex.forall {
          case (element, index) => '_' == element(xPosition) || element(xPosition) == word.charAt(index)
          //case (element, index) => element(xPosition) == '_' || element(xPosition)  == word.charAt(index)
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

  def translateCoordinate(coordinate: String): (Int, Int) = {
    val coordinates = coordinate.split(" ")
    (coordinates(0).toUpperCase().toCharArray.sum - 'A', coordinates(1).toInt)
  }

}


object Demo{
  def main(args:Array[String]): Unit = {
    val numbsForBothSide = 42
    val field = new ScrabbleField(Vector.fill(15)(Vector.fill(15)('_')))
    field.placeTile(3, 3, 'A')
    field.placeTile(7, 7, 'B')
    println(field)
    val newField = field.placeWord(2, 2, 'V', "test")
    println(newField)
    println(newField.translateCoordinate("0 A"))
  }
}
