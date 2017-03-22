package tagless

import common._

import cats.Monad

import scala.language.higherKinds

/*
 * An algebra of primitive operations that we want to support
 *
 * `F` is the monadic effect that will be chosen by the interpreter.
 */
trait Monadic[F[_]] {

  implicit def M: Monad[F]

}

trait S3Alg[F[_]] {

  def generateS3Key(id: PhotoId): F[S3Key]

  def writeContentToS3(key: S3Key, content: Array[Byte]): F[Unit]

  def readContentFromS3(key: S3Key): F[Array[Byte]]
}

trait DynamoAlg[F[_]] {

  def insertDynamoRecord(id: PhotoId, s3key: S3Key, createdBy: String): F[DynamoRecord]

  def getDynamoRecord(id: PhotoId): F[DynamoRecord]

}


//trait Algebra[F[_]] extends S3Alg[F] with DynamoAlg[F] with Monadic[F]
