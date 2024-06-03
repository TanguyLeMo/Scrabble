package de.htwg.se.scrabble.util

trait Command[T]:
  def noStep(t: T): T
  def doStep(t: T): T
  def undoStep(t: T): T
  def redoStep(t: T): T

class UndoManager[T]:
  private var undoStack: List[T] = Nil
  private var redoStack: List[T] = Nil

  def doStep(t: T, newState: T): T =
    undoStack = t :: undoStack
    redoStack = Nil // Clear the redo stack on a new operation
    newState

  def undoStep(t: T): T =
    undoStack match {
      case Nil => t
      case head :: stack =>
        redoStack = t :: redoStack
        undoStack = stack
        head
    }

  def redoStep(t: T): T =
    redoStack match {
      case Nil => t
      case head :: stack =>
        undoStack = t :: undoStack
        redoStack = stack
        head
    }
  def getUndoStack: List[T] = undoStack