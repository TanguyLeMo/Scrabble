package de.htwg.se.scrabble

import aview.TUI
import controller.Controller
import model.ScrabbleField
import model.Matrix
import model.Stone

@main def run: Unit =
  println("Welcome to TicTacToe")
  val field = new ScrabbleField(15)
  val controller = Controller(field)
  val tui = TUI(controller)
  tui.run