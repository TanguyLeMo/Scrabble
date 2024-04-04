import scala.language.postfixOps


val quadraticfield = 15;
val columns: Int = quadraticfield;
val rows: Int = quadraticfield;
val numofAlphabet: Int = 26
val numSymolPerColumn = Math.ceil(rows.toDouble / numofAlphabet.toDouble).toInt
val emptyTile =  '_'
val field = Array.ofDim[Char](rows, columns)


def addSpace(numSpaceToAdd: Int): String = {
  if (numSpaceToAdd <= 0)
    return " "
  return " " + addSpace(numSpaceToAdd - 1);
}

 def getLetter(n: Int): String = {
  if (n <= 0) {
    ""
  } else {
    val remainder = (n - 1) % 26
    getLetter((n - 1) / 26) + ('A' + remainder).toChar
  }
}

 def getMaxNumOfChar(): Int = {
  val returnval: Int = Math.ceil(columns / numofAlphabet).asInstanceOf[Int]
  returnval
}

 def labelingXAxis(currcolum: Int): String = {
  if (currcolum > columns) {
    return "";
  }
  val letter: String = getLetter(currcolum)
  return letter + addSpace(numSymolPerColumn - letter.length) + (labelingXAxis(currcolum + 1));
}

 def gothroughColumn(rowidx: Int, colidx: Int): String = {

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



 def gothroughrow(rowidx: Int): String = {
  if (rowidx >= rows) {
    return ""
  }
  val colInString = gothroughColumn(rowidx, 0) + "\n"
  return rowidx.toString.padTo(3, ' ') + " " + colInString + gothroughrow(rowidx + 1)
}

val x: String = addSpace(5)
val t: String = gothroughrow(0)



val sb = new StringBuilder()
sb.append(" ".padTo(4, ' ')).append(labelingXAxis(1))
sb.append('\n')
sb.append(gothroughrow(0))
sb.toString()


