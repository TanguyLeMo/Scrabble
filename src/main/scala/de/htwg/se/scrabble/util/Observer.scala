package de.htwg.se.scrabble
package util
trait Observer:
  def update(): String

trait Observable:
  var subscribers: Vector[Observer] = Vector()
  def add(s: Observer): Unit = subscribers = subscribers :+ s
  def remove(s: Observer): Unit = subscribers = subscribers.filter(o => o == s)
  def notifyObservers(): Unit =
    subscribers.foreach(o => o.update())

