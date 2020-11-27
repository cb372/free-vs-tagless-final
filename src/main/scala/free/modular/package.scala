package free.modular

import cats.data.EitherK
import cats.data.OptionT
import cats.free.Free

import scala.concurrent.Future

object `package` {

  type FutureOfOption[A] = OptionT[Future, A]

  type Algebra[A] = EitherK[S3Alg, DynamoAlg, A]

  type Program[A] = Free[Algebra, A]

}
