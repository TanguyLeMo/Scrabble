import de.htwg.se.scrabble.ScrabbleField

class TUI{
  def main(args: Array[String]): Unit = {
    val currentScrabbleField = new ScrabbleField(Vector.fill(15)(Vector.fill(15)('_')))
  }
}