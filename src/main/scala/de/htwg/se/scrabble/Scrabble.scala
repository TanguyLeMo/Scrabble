package de.htwg.se.scrabble

import scala.io.StdIn.readLine
import aview.TUI
import controller.Controller
import model.ScrabbleField
//This is to test the coverall feature
@main def run(): Unit = {
    val field = new ScrabbleField(15)
    val controller = Controller(field)
    val tui = TUI(controller)
    println("Welcome to Scrabble")
    println(controller.toString)
    println("Enter your Word, Coordinate and Direction(H|V) example: myWord A 0 H")
    while(!tui.processInputLine(readLine()).equals("exit"))
    println("Goodbye")
}


