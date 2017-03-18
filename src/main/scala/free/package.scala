package free

import cats.data.OptionT
import scala.concurrent.Future

object `package` {

  type FutureOfOption[A] = OptionT[Future, A]

}
