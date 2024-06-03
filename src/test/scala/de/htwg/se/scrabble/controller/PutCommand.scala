

package de.htwg.se.scrabble.controller
import de.htwg.se.scrabble.controller.PutCommand
import de.htwg.se.scrabble.aview.languages.LanguageContext
import de.htwg.se.scrabble.model.{placeWordsAsMove, ScrabbleField}
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers

class PutCommandSpec extends AnyWordSpec with Matchers{
  "A PutCommand" when {
    "new" should {
      val move = placeWordsAsMove(0, 0, 'H', "hello")
      val putCommand = new PutCommand(move)
      "have a valid doStep" in {
        val field = new ScrabbleField(15)
        val controller = new Controller(field)
        controller.placeWord(0, 0, 'H', "hello")
        putCommand.doStep(field) should not be field
      }
      "have a valid undoStep" in {
        val field = new ScrabbleField(15)
        val controller = new Controller(field)
        controller.placeWord(0, 0, 'H', "hello")
        putCommand.undoStep(field) should be(field)
      }
      "have a valid redoStep" in {
        val field = new ScrabbleField(15)
        val controller = new Controller(field)
        controller.placeWord(0, 0, 'H', "hello")
        putCommand.undoStep(field)
        val temp = putCommand.redoStep(field)
        temp shouldEqual field.placeWord(0, 0, 'H', "hello")
      }
      "noStep" in {
        val field = new ScrabbleField(15)
        val controller = new Controller(field)
        controller.placeWord(0, 0, 'H', "hello")
        putCommand.noStep(field) should be(field)
      }
    }
  }
}
