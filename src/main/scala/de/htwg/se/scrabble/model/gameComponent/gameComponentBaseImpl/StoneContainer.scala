package de.htwg.se.scrabble.model.gameComponent
package gameComponentBaseImpl


import com.google.inject.Inject
import de.htwg.se.scrabble.util.LanguageEnum
import de.htwg.se.scrabble.model.gameComponent.StoneContainerInterface

import scala.util.{Failure, Success, Try}

class StoneContainer @Inject (val stones : List[StoneInterface]) extends StoneContainerInterface {

    def gamestartPlayStones(languageEnum: LanguageEnum): List[Stone] = {
        val source = scala.io.Source.fromResource(languageEnum match {
            case LanguageEnum.ENGLISH => "englishPlaystones.txt"
            case LanguageEnum.GERMAN => "germanPlaystones.txt"
            case LanguageEnum.FRENCH => "frenchPlaystones.txt"
            case LanguageEnum.ITALIAN => "italianPlaystones.txt"
            case null => "englishPlaystones.txt"
        })
        val letters: Try[List[Stone]] = Try {
            source.getLines.flatMap { line =>
                line.split(",").filterNot(_.isEmpty).map(s => new Stone(s.head))
            }.toList
        }
        letters match {
            case Success(value) =>
                value
            case Failure(exception) =>
                exception.printStackTrace()
                List[Stone]()
        }
    }

    def drawRandomeStone(Container: StoneContainerInterface): StoneInterface = {
        val random = new scala.util.Random
        val randomIndex = random.nextInt(Math.abs(Container.stones.length))
        Container.stones(randomIndex)
    }

    def removeStonefromContainer(StoneToRemove : StoneInterface,Container: StoneContainerInterface): StoneContainer = {
        val index = Container.stones.indexOf(StoneToRemove)
        new StoneContainer(Container.stones.patch(index, Nil, 1))
    }

    def ContainerEmtpy(Container: StoneContainerInterface): Boolean = {
        Container.stones.isEmpty
    }
}
