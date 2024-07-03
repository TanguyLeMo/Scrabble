package de.htwg.se.scrabble.model.square

import de.htwg.se.scrabble.model.gameComponent.gameComponentBaseImpl.Stone
import de.htwg.se.scrabble.model.gameComponent.squareBaseImpl
import de.htwg.se.scrabble.model.gameComponent.squareBaseImpl.{DoubleLetterSquare, StandardSquare, TripleLetterSquare, TripleWordSquare}
import org.scalatest.matchers.must.Matchers
import org.scalatest.matchers.should.Matchers.should
import org.scalatest.wordspec.AnyWordSpec

class SquareSpec extends AnyWordSpec with Matchers{

    "A StandardSquare" when {
      "new" should {
        val standardSquare = new StandardSquare(Stone())
        "have a letter" in {
          standardSquare.letter should not be(null)
        }
        "have a multiplier" in {
          standardSquare.scoreModifier should be(1)
        }
        "have a wordMultiplier" in {
          standardSquare.scoreModifier should be(1)
        }
      }
      "setLetter" should {
        val standardSquare = new StandardSquare(Stone('A'))
        "set a letter" in {
          standardSquare.letter.symbol should be('A')
        }
      }
      "Color" should {
        val standardSquare = new StandardSquare(Stone())
        "have a color" in {
          standardSquare.color should be("white")
        }
      }
    }
    "A DoubleLetterSquare "should{
      "have a multiplier" in{
        val doubleLetterSquare = new DoubleLetterSquare(Stone())
        doubleLetterSquare.scoreModifier should be(2)
      }
      "Color" should {
        val doubleLetterSquare = new DoubleLetterSquare(Stone())
        "have a color" in {
          doubleLetterSquare.color should be(Console.BLUE)
        }
      }
      "A TrippleLetterSquare " should {
        "have a multiplier" in {
          val trippleLetterSquare = new TripleLetterSquare(Stone())
          trippleLetterSquare.scoreModifier should be(3)
        }
        "isEmpty " in {
          val trippleLetterSquare = new TripleLetterSquare(Stone())
          trippleLetterSquare.isEmpty should be(true)
        }
        "Color" should {
          val trippleLetterSquare = new TripleLetterSquare(Stone())
          "have a color" in {
            trippleLetterSquare.color should be(Console.YELLOW)
          }
          "Is Empty" should {
            val trippleLetterSquare = new DoubleLetterSquare(Stone())
            "be empty" in {
              trippleLetterSquare.isEmpty should be(true)
            }
          }
        }
      }
      "A trippleWordSquare " should {
        "have a multiplier" in {
          val doubleLetterSquare = new TripleWordSquare(Stone())
          doubleLetterSquare.scoreModifier should be(3)
        }
        "Color" should {
          val trippleWordSquare = new TripleWordSquare(Stone())
          "have a color" in {
            trippleWordSquare.color should be(Console.RED)
          }
          "Is Empty" should {
            val trippleWordSquare = new TripleWordSquare(Stone())
            "be empty" in {
              trippleWordSquare.isEmpty should be(true)
            }

          }
        }
      }

    }

}
