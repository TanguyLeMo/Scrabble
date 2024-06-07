package de.htwg.se.scrabble.model
import de.htwg.se.scrabble.model.languages.*
import de.htwg.se.scrabble.model.scoring.*
import de.htwg.se.scrabble.model.StoneContainer
import de.htwg.se.scrabble.model.languages.LanguageEnum
import de.htwg.se.scrabble.model.languages.LanguageEnum.{ENGLISH, FRENCH, GERMAN, ITALIAN}
import de.htwg.se.scrabble.model.square.{ScrabbleSquare, StandardSquareFactory}

class ScrabbleField(val matrix: Matrix, val dictionary: Dictionary, val squareFactory: StandardSquareFactory, val languageEnum: LanguageEnum, val player : Player, val players : List[Player],val stoneContainer: StoneContainer):
  val numOfAlphabet: Int = 26
  val numSymbolPerColumn: Int = Math.ceil(matrix.rows.toDouble / numOfAlphabet.toDouble).toInt + 1
  val scoringSystem: ScoringSystem = languageEnum match
    case ENGLISH => new EnglishScoringSystem()
    case FRENCH => new FrenchScoringSystem()
    case GERMAN => new GermanScoringSystem()
    case ITALIAN => new ItalianScoringSystem()
  val languageSettings : String = languageEnum match {
    case ENGLISH => "Language is set to" + Console.YELLOW  + " English" + Console.RESET
    case FRENCH => "Le Language sera"+ Console.YELLOW +" Francais" + Console.RESET
    case GERMAN => "Sprache wurde eingestellt auf " + Console.YELLOW + " Deutsch" + Console.RESET
    case ITALIAN => "La linguga sara" + Console.YELLOW + " Italian" + Console.RESET
  }
  val languageContext: LanguageContext = new LanguageContext(languageEnum.toString)

  def this(rowsAndColumns: Int) = this(new Matrix(Vector.fill(rowsAndColumns, rowsAndColumns)(new StandardSquareFactory().createDoubleSquare(Stone()))).init(), new Dictionary().readLines(ENGLISH), new StandardSquareFactory, ENGLISH, new Player("Player1",0,List[Stone]()), Nil,new StoneContainer(List[Stone]()))
  def this(matrix : Matrix, newDictionary: Dictionary) = this(matrix, newDictionary, new StandardSquareFactory, ENGLISH, new Player("Player1",0,List[Stone]()), Nil, new StoneContainer(List[Stone]()))
  def this(rowsAndColumns : Int, languageEnum: LanguageEnum) = this(new Matrix(Vector.fill(rowsAndColumns, rowsAndColumns)(new StandardSquareFactory().createDoubleSquare(Stone()))).init(), new Dictionary().readLines(languageEnum), new StandardSquareFactory, languageEnum, new Player("Player1",0,List[Stone]()), Nil,new StoneContainer(List[Stone]()))
  def labelingXAxis(currcolum: Int): String =
    if(currcolum > matrix.columns)""
    else translateLetter(currcolum)+addSpace(numSymbolPerColumn-translateLetter(currcolum).length)+labelingXAxis(currcolum + 1)
  def addSpace(numSpaceToAdd: Int): String = numSpaceToAdd match
    case n if n <= 0 => " "
    case n => " " + addSpace(n - 1)
  def placeWord(yPosition: Int, xCoordinates : Int, direction :Char, word : String): ScrabbleField = new ScrabbleField(matrix.placeWord(yPosition, xCoordinates, direction, word), dictionary, squareFactory, languageEnum, player, players, stoneContainer)
  def wordFits(xPosition: Int, yPosition: Int, direction: Char, word: String) : Boolean = matrix.wordFits(xPosition, yPosition, direction, word)
  def translateLetter(n: Int): String = if (n <= 0) "" else translateLetter((n - 1) / 26) + ('A' + (n - 1) % 26).toChar
  def concatenateRows(currentRow: Int): String =
    if (currentRow >= matrix.rows) "" else s"${currentRow.toString.padTo(3, ' ')} ${concatenateColumnsOfCurrentRow(currentRow, 0) + "\n"}${concatenateRows(currentRow + 1)}"
  def concatenateColumnsOfCurrentRow(currentRow: Int, currentColumn: Int): String =
    val nextCol = currentColumn + 1
    if (currentColumn >= matrix.columns) ""
    else matrix.getSquare(currentRow, currentColumn).toString + addSpace(numSymbolPerColumn - 1) + concatenateColumnsOfCurrentRow(currentRow, nextCol)
  override def toString: String =s"    ${labelingXAxis(1)}\n${concatenateRows(0)}"
  override def equals(that: Any): Boolean =
    that match
      case that: ScrabbleField => this.matrix.equals(that.matrix) && this.numSymbolPerColumn == that.numSymbolPerColumn
      case _ => false
  def removeWord (yPosition: Int, xPosition: Int, direction: Char, word: String): ScrabbleField = new ScrabbleField(matrix.removeWord(yPosition, xPosition, direction, word), dictionary, squareFactory, languageEnum, player, players,stoneContainer)
  def addDictionaryWord(word: String): ScrabbleField = new ScrabbleField(matrix, dictionary.addWord(word))
  def setLanguageDictionary(languagee: LanguageEnum): ScrabbleField = new ScrabbleField(matrix, dictionary.readLines(languagee), squareFactory, languagee, player, players,stoneContainer)
  def previousTurn(currentTurn: Player): ScrabbleField = new ScrabbleField(matrix, dictionary, squareFactory, languageEnum, players.last, players, stoneContainer)

  def addPoints(pointsToAdd: Int, player: Player, ListPlayers: List[Player]): ScrabbleField =
    val newPlayer = new Player(player.getName, player.getPoints + pointsToAdd, player.playerTiles)
    if (!ListPlayers.contains(player)) return this
    val newListPlayers = ListPlayers.updated(ListPlayers.indexOf(player), newPlayer)
    new ScrabbleField(matrix, dictionary, squareFactory, languageEnum, newPlayer, newListPlayers, stoneContainer)