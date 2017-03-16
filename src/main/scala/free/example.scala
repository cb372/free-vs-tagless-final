package free

import scala.concurrent._
import scala.concurrent.duration.Duration
import scala.concurrent.ExecutionContext.Implicits.global

import cats.instances.future._

object Example extends App {

  import Programs._
  
  val future: Future[Photo] = 
    saveAndThenGetPhoto(PhotoId("abc"), "Chris", "yolo".getBytes)
      .foldMap(Interpreters.futureInterpreter)
  
  println(Await.result(future, atMost = Duration.Inf))


}
