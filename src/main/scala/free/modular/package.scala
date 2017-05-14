package free.modular

import cats.data.OptionT
import cats.data.Coproduct
import cats.free.Free

import scala.concurrent.Future

object `package` {

  type FutureOfOption[A] = OptionT[Future, A]

  // Note: Coproduct will be called EitherK in cats 1.0
  type Algebra[A] = Coproduct[S3Alg, DynamoAlg, A]

  type Program[A] = Free[Algebra, A]

}
