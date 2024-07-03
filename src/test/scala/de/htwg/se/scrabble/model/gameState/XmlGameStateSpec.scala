package de.htwg.se.scrabble.model.gameState

import de.htwg.se.scrabble.model.gameComponent.gameComponentBaseImpl.ScrabbleField
import de.htwg.se.scrabble.model.gameState.GameStateBaseImpl.XmlGameState
import de.htwg.se.scrabble.util.LanguageEnum
import org.scalatest.matchers.should.Matchers
import org.scalatest.matchers.should.Matchers.*
import org.scalatest.wordspec.AnyWordSpec

class XmlGameStateSpec extends AnyWordSpec with Matchers {
  "A XmlGameState" when {
    "A German ScrabbleField is saved" should {
      "be able to save the ScrabbleField" in {
         val xmlGameState = new XmlGameState()
          val scrabbleField = new ScrabbleField(15, LanguageEnum.GERMAN)
          val savedSF = xmlGameState.save(scrabbleField)
          savedSF should be(true)
      }
    }
    "An English ScrabbleField is saved" should {
      "be able to save the ScrabbleField" in {
        val xmlGameState = new XmlGameState()
        val scrabbleField = new ScrabbleField(15, LanguageEnum.ENGLISH)
        val savedSF = xmlGameState.save(scrabbleField)
        savedSF should be(true)
      }
    }
    "A French ScrabbleField is saved" should {
      "be able to save the ScrabbleField" in {
        val xmlGameState = new XmlGameState()
        val scrabbleField = new ScrabbleField(15, LanguageEnum.FRENCH)
        val savedSF = xmlGameState.save(scrabbleField)
        savedSF should be(true)
      }
    }
    "An Italian ScrabbleField is saved" should {
      "be able to save the ScrabbleField" in {
        val xmlGameState = new XmlGameState()
        val scrabbleField = new ScrabbleField(15, LanguageEnum.ITALIAN)
        val savedSF = xmlGameState.save(scrabbleField)
        savedSF should be(true)
      }
    }

    "loadXmlToLanguage is called" should {
      "return the correct language" in {
        val xmlGameState = new XmlGameState()
        val xml = <ScrabbleField>
          <language>GERMAN</language>
        </ScrabbleField>
        val language = xmlGameState.loadXmlToLanguage(xml)
        language should be(LanguageEnum.GERMAN)
      }
    }
    "loadXmlToLanguage in French is called " should {
      "return the correct language" in {
        val xmlGameState = new XmlGameState()
        val xml = <ScrabbleField>
          <language>FRENCH</language>
        </ScrabbleField>
        val language = xmlGameState.loadXmlToLanguage(xml)
        language should be(LanguageEnum.FRENCH)
      }
    }
    "loadXmlToLanguage in English is called " should {
      "return the correct language" in {
        val xmlGameState = new XmlGameState()
        val xml = <ScrabbleField>
          <language>ENGLISH</language>
        </ScrabbleField>
        val language = xmlGameState.loadXmlToLanguage(xml)
        language should be(LanguageEnum.ENGLISH)
      }
    }
    "loadXmlToLanguage in Italian is called " should {
      "return the correct language" in {
        val xmlGameState = new XmlGameState()
        val xml = <ScrabbleField>
          <language>ITALIAN</language>
        </ScrabbleField>
        val language = xmlGameState.loadXmlToLanguage(xml)
        language should be(LanguageEnum.ITALIAN)
      }
    }
    "loadXmlToPlayers is called" should {
      "return the correct players" in {
        val xmlGameState = new XmlGameState()
        val xml = <ScrabbleField>
          <players>
            <player>
              <name>Player1</name>
              <points>0</points>
              <stones>
                <stone>A</stone>
                <stone>B</stone>
              </stones>
            </player>
            <player>
              <name>Player2</name>
              <points>0</points>
              <stones>
                <stone>C</stone>
                <stone>D</stone>
              </stones>
            </player>
          </players>
        </ScrabbleField>
        val players = xmlGameState.loadXmlToPlayers(xml)
        players.length should be(2)
      }
    }
  }
}
