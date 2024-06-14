package de.htwg.se.scrabble

import com.google.inject.name.Names
import com.google.inject.{AbstractModule, Guice, Injector, TypeLiteral}
import de.htwg.se.scrabble.controller.ControllerComponent.ControllerBaseImpl.Controller
import de.htwg.se.scrabble.controller.ControllerComponent.ControllerInterface
import de.htwg.se.scrabble.model.gameComponent.{DictionaryInterface, MatrixInterface, PlayerInterface, ScoringSystemInterface, ScrabbleFieldInterface, ScrabbleSquare, ScrabbleSquareInterface, SquareFactory, StoneContainerInterface, StoneInterface, squareFactoryInterface}
import de.htwg.se.scrabble.model.gameComponent.gameComponentBaseImpl.{Dictionary, Matrix, Player, ScrabbleField, Stone, StoneContainer}
import de.htwg.se.scrabble.model.gameComponent.scoringBaseImpl.{EnglishScoringSystem, ScoringSystem}
import de.htwg.se.scrabble.model.gameComponent.squareBaseImpl.{StandardSquare, StandardSquareFactory}
import de.htwg.se.scrabble.model.languageComponent.LanguageContextInterface
import de.htwg.se.scrabble.model.languageComponent.languages.LanguageContext
import de.htwg.se.scrabble.util.{CharacterProvider, IntegerProvider, LanguageEnum}
import net.codingwell.scalaguice.{ScalaModule, ScalaPrivateModule}




class Modules extends AbstractModule with ScalaModule{
  val defaultSize: Int = 15
  val defaultLanguage: String = "ENGLISH"
  val emptySquare = new StandardSquare(Stone('_'))
  val emptyRow = Vector.fill(defaultSize)(emptySquare)
  val emptyField = Vector.fill(defaultSize)(emptyRow)
  val matrix = new Matrix(emptyField).init()
  
  override def configure(): Unit = {
    bind(classOf[Integer]).toProvider(classOf[IntegerProvider])
    bind(classOf[Character]).toProvider(classOf[CharacterProvider])
    bind(classOf[ScoringSystemInterface]).to(classOf[EnglishScoringSystem])
    bind(classOf[ControllerInterface]).to(classOf[Controller])
    bind(classOf[ScrabbleFieldInterface]).to(classOf[ScrabbleField])
    bind(classOf[DictionaryInterface]).to(classOf[Dictionary])
    bind(classOf[MatrixInterface]).to(classOf[Matrix])
    bind(classOf[PlayerInterface]).to(classOf[Player])
    bind(classOf[ScrabbleSquareInterface]).to(classOf[StandardSquare])
    bind(classOf[StoneContainerInterface]).to(classOf[StoneContainer])
    bind(classOf[squareFactoryInterface]).to(classOf[StandardSquareFactory])
    bind(classOf[StoneInterface]).to(classOf[Stone])
    bind(classOf[LanguageContextInterface]).to(classOf[LanguageContext])
    bind(classOf[LanguageEnum]).toInstance(LanguageEnum.ENGLISH)
    bind(new TypeLiteral[List[PlayerInterface]]{}).toInstance(List(new Player("Player1", 0, Nil), new Player("Player2", 0, Nil)))
    bind(new TypeLiteral[List[StoneInterface]]{}).toInstance(List(new Stone('_')))
    bind(new TypeLiteral[Vector[Vector[ScrabbleSquare]]] {}).toInstance(matrix.field)

  }
}
