package de.htwg.se.scrabble.test

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
    if (currentRow >= rows) "" else s"${currentRow.toString.padTo(3, ' ')} ${goThroughColumn(currentRow, 0) + "\n"}${goThroughRow(currentRow + 1)}"

  def goThroughColumn(currentRow: Int, currentColumn: Int): String = {
    val nextCol = currentColumn + 1
    val midRow = (rows - 1) / 2
    val midCol = (columns - 1) / 2

    if (currentColumn >= columns) ""
    else if (field(currentRow)(currentColumn) == 0) {
      emptyTile + addSpace(numSymolPerColumn - 1) + goThroughColumn(currentRow, nextCol)
    } else  field(currentRow)(currentColumn) + addSpace(numSymolPerColumn - 1) + goThroughColumn(currentRow, nextCol)
  }


  object Main extends App {
  val numbsForBothSide = 15
  val field = new ScrabbleField(numbsForBothSide, numbsForBothSide)

  field.placeTile(3, 3, 'A')
  field.placeTile(7, 7, 'B')
  field.placeTile(9, 5, 'C')
  println(field)
}
