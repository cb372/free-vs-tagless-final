package tagless

import common._

import scala.language.higherKinds

/*
 * An algebra of primitive operations that we want to support
 *
 * `F` is the monadic effect that will be chosen by the interpreter.
 */
trait Algebra[F[_]] {

  def generateS3Key(id: PhotoId): F[S3Key]

  def insertDynamoRecord(id: PhotoId, s3key: S3Key, createdBy: String): F[DynamoRecord]

  def getDynamoRecord(id: PhotoId): F[DynamoRecord]

  def writeContentToS3(key: S3Key, content: Array[Byte]): F[Unit]

  def readContentFromS3(key: S3Key): F[Array[Byte]]

}
