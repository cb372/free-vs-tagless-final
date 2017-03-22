package tagless

import common._

import scala.concurrent._
import scala.concurrent.duration.Duration
import scala.concurrent.ExecutionContext.Implicits.global

/**
 * An example of executing a program using an interpreter
 */
object Example extends App {

  val prog = new Programs[FutureOfOption](FutureOfOptionInterpreter)
  
  val future: FutureOfOption[Photo] = 
    prog.saveAndThenGetPhoto(PhotoId("abc"), "Chris", "yolo".getBytes)
  
  println(Await.result(future.value, atMost = Duration.Inf))

}
