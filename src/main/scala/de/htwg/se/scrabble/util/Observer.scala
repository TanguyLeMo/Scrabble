package de.htwg.se.scrabble
package util

trait Observer: 
  def update(): Unit
  
trait Observable:
  private var subscribers: Vector[Observer] = Vector()
  def add(s: Observer): Seq[Observer] = subscribers :+ s
  def remove(s: Observer): Unit = subscribers = subscribers.filter(o => o == s)
  def notifyObservers(): Unit = subscribers.foreach(o => o.update())
