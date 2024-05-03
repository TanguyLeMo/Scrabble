package de.htwg.se.scrabble.util

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._

class ObservableSpec extends AnyWordSpec:

  "An Observable" when {
    "created" should {

      "have no subscribers and assert of no exception thrown" in {
        val observable = new Observable {}
        observable.notifyObservers()
      }
    }

    "subscribed to" should {
      "have one subscriber and assert of no exception thrown" in {
        val observable = new Observable {}
        val observer = new Observer { def update(): String = "" }
        observable.add(observer)
        observable.notifyObservers()
      }
    }

    "unsubscribed from" should {
      "have no subscribers and assert of no exception thrown" in {
        val observable = new Observable {}
        val observer = new Observer { def update(): String = "" }
        observable.add(observer)
        observable.remove(observer)
        observable.notifyObservers() 
      }
    }

    "notified" should {
      "trigger update on subscribers" in {
        var updateTriggered = false
        val observable = new Observable {}
        val observer = new Observer {
          def update(): String = {
            updateTriggered = true
            ""
          }
        }
        observable.add(observer)
        observable.notifyObservers()
        updateTriggered should be(true)
      }
    }
  }
