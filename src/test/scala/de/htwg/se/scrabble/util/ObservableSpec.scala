package de.htwg.se.scrabble.util

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._

class ObservableSpec extends AnyWordSpec:

  "An Observable" when {

    "created" should {

      "have no subscribers" in {
        val observable = new Observable {}
        observable.notifyObservers() // No exceptions should be thrown
      }
    }

    "subscribed to" should {
      "have one subscriber" in {
        val observable = new Observable {}
        val observer = new Observer { def update(): Unit = () }
        observable.add(observer)
        observable.notifyObservers() // No exceptions should be thrown
      }
    }

    "unsubscribed from" should {

      "have no subscribers" in {
        val observable = new Observable {}
        val observer = new Observer { def update(): Unit = () }
        observable.add(observer)
        observable.remove(observer)
        observable.notifyObservers() // No exceptions should be thrown
      }
    }

    "notified" should {

      "trigger update on subscribers" in {
        var updateTriggered = false
        val observable = new Observable {}
        val observer = new Observer { def update(): Unit = updateTriggered = true }
        observable.add(observer)
        observable.notifyObservers()
        updateTriggered should be(true)
      }
    }
  }
