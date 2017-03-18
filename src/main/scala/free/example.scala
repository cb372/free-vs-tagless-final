package free

import common._

import scala.concurrent._
import scala.concurrent.duration.Duration
import scala.concurrent.ExecutionContext.Implicits.global

import cats.instances.future._

/**
 * An example of executing a program using an interpreter
 */
object Example extends App {

  import Programs._
  
  val futureOfOption: FutureOfOption[Photo] = 
    saveAndThenGetPhoto(PhotoId("abc"), "Chris", "yolo".getBytes)
      .foldMap(Interpreters.futureOfOptionInterpreter)
  
  println(Await.result(futureOfOption.value, atMost = Duration.Inf))

}
