package de.htwg.se.scrabble
package util
trait Observer:
  def update(event : ScrabbleEvent): String

trait Observable:
  var subscribers: Vector[Observer] = Vector()
  def add(s: Observer): Unit = {
    subscribers = subscribers.filterNot(_.getClass == s.getClass)
    subscribers = subscribers :+ s
  }
  def remove(s: Observer): Unit = subscribers = subscribers.filter(o => o == s)
  def notifyObservers(event: ScrabbleEvent): Unit =
    subscribers.foreach(o => o.update(event))
