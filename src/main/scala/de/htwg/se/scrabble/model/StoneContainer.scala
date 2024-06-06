package de.htwg.se.scrabble.model

import de.htwg.se.scrabble.model.languages.LanguageEnum
import scala.util.{Failure, Success, Try}

class StoneContainer(val Stones : List[Stone]) {

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

    def drawRandomeStone(Container: StoneContainer): Stone = {

    val random = new scala.util.Random
    val randomIndex = random.nextInt(Container.Stones.length)
    Container.Stones(randomIndex)
}

    def removeStonefromContainer(StoneToRemove : Stone,Container: StoneContainer): StoneContainer = {
        val index = Container.Stones.indexOf(StoneToRemove)
        new StoneContainer(Container.Stones.patch(index, Nil, 1))
    }

    def ContainerEmtpy(Container: StoneContainer): Boolean = {
    Container.Stones.isEmpty
    }
}
