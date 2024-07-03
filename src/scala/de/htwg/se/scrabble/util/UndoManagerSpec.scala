package de.htwg.se.scrabble.util

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class UndoManagerSpec extends AnyWordSpec with Matchers {

  "An UndoManager" should {

    "perform a doStep correctly" in {
      val undoManager = new UndoManager[String]
      val initialState = "initial"
      val newState = "new"

      val result = undoManager.doStep(initialState, newState)
      result should be(newState)
      undoManager.getUndoStack should contain only initialState
    }

    "perform an undoStep correctly" in {
      val undoManager = new UndoManager[String]
      val initialState = "initial"
      val newState = "new"

      undoManager.doStep(initialState, newState)
      val result = undoManager.undoStep(newState)
      result should be(initialState)
      undoManager.getUndoStack should be(empty)
    }

    "perform a redoStep correctly" in {
      val undoManager = new UndoManager[String]
      val initialState = "initial"
      val newState = "new"

      undoManager.doStep(initialState, newState)
      undoManager.undoStep(newState)
      val result = undoManager.redoStep(initialState)
      result should be(newState)
      undoManager.getUndoStack should contain only initialState
    }

    "do nothing on undoStep if undoStack is empty" in {
      val undoManager = new UndoManager[String]
      val initialState = "initial"

      val result = undoManager.undoStep(initialState)
      result should be(initialState)
      undoManager.getUndoStack should be(empty)
    }

    "do nothing on redoStep if redoStack is empty" in {
      val undoManager = new UndoManager[String]
      val initialState = "initial"

      val result = undoManager.redoStep(initialState)
      result should be(initialState)
      undoManager.getUndoStack should be(empty)
    }

    "clear redoStack on a new doStep operation" in {
      val undoManager = new UndoManager[String]
      val initialState = "initial"
      val newState1 = "new1"
      val newState2 = "new2"

      undoManager.doStep(initialState, newState1)
      undoManager.undoStep(newState1)
      undoManager.doStep(initialState, newState2)

      val result = undoManager.redoStep(initialState)
      result should be(initialState)
      undoManager.getUndoStack should contain only initialState
    }
  }
}
