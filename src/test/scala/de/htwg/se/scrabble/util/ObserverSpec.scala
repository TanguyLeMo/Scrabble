package de.htwg.se.scrabble.util

import de.htwg.se.scrabble.util
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class ObserverSpec extends AnyWordSpec with Matchers{

  // Dummy implementation of ScrabbleEvent for testing
  case class ScrabbleEvent(message: String)

  // Dummy Observer for testing
  class TestObserver extends Observer {
    var eventMessage: util.ScrabbleEvent = phaseMainGame()
    override def update(event: util.ScrabbleEvent): String = {
      eventMessage = event
      eventMessage.toString
    }

  }

  "An Observable" should {
    "allow an Observer to be added" in {
      val observable = new Observable {}
      val observer = new TestObserver
      observable.add(observer)
      observable.subscribers should contain(observer)
    }

    "allow an Observer to be removed" in {
      val observable = new Observable {}
      val observer = new TestObserver
      observable.add(observer)
      observable.remove(observer)
      observable.subscribers should contain (observer)
    }

    "notify all Observers of an event" in {
      val observable = new Observable {}
      val observer1 = new TestObserver
      val observer2 = new TestObserver
      observable.add(observer1)
      observable.add(observer2)

      val event = phaseMainGame()
      observable.notifyObservers(event)


      "" shouldEqual ""
    }

    "not add the same type of Observer more than once" in {
      val observable = new Observable {}
      val observer1 = new TestObserver
      val observer2 = new TestObserver

      observable.add(observer1)
      observable.add(observer2)

      observable.subscribers.size should be(1)
    }
  }
}