package de.htwg.se.scrabble
import scala.io.StdIn.readLine
import aview.TUI
@main def run(): Unit = {
    val tui = TUI().start
    while(!tui.dictionaryAddWords(readLine()).equals("stop")){}
    while(!tui.processInputLine(readLine()).equals("exit")){}
    println("Goodbye")
}


