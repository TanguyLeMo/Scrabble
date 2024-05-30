import de.htwg.se.scrabble.util.{AppendCommand, Command, UndoManager}
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec


class UndoManagerSpec extends AnyWordSpec with Matchers {
  "An UndoManager" when {
    "new" should {
      val undoManager = new UndoManager[String]
      val appendCommand = new AppendCommand('a')

      "have a doStep method" in {
        val result = undoManager.doStep("test", appendCommand)
        result shouldBe "testa"
      }
      "have an undoStep method" in {
        val result = undoManager.undoStep(undoManager.doStep("test", appendCommand))
        result shouldBe "test"
      }
      "have a redoStep method" in {
        val result = undoManager.redoStep(undoManager.undoStep(undoManager.doStep("test", appendCommand)))
        result shouldBe "testa"
      }
      "have a noStep method" in {
        val result = appendCommand.noStep("test")
        result shouldBe "test"
      }

    }
  }
}