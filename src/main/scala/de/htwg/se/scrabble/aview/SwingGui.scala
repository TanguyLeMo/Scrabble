package de.htwg.se.scrabble
package aview


import de.htwg.se.scrabble.controller.Controller
import de.htwg.se.scrabble.util.Observer
import util.Event

import java.awt.Frame
import scala.swing.event.*
import scala.swing.*

class SwingGui(controller: Controller) extends Frame with Observer{
  controller.add(this)

}