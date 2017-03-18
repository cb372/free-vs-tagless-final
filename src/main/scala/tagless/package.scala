package tagless

import cats.Monad
import cats.data.OptionT
import cats.instances.future._

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

object `package` {

  type FutureOfOption[A] = OptionT[Future, A]

  val futureOfOptionMonad: Monad[FutureOfOption] = implicitly

}
