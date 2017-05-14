package tagless.modular

import common._

import cats.Monad

import scala.language.higherKinds

/*
 * Examples of larger programs built up from the DSL primitives
 */
class Programs[F[_] : Monad](s3: S3Alg[F], dynamo: DynamoAlg[F]) {
  import s3._, dynamo._

  import cats.syntax.functor._
  import cats.syntax.flatMap._

  def savePhoto(id: PhotoId, createdBy: String, content: Array[Byte]): F[Unit] =
    for {
      s3key <- generateS3Key(id)
      _ <- insertDynamoRecord(id, s3key, createdBy)
      _ <- writeContentToS3(s3key, content)
    } yield ()

  def getPhoto(id: PhotoId): F[Photo] =
    for {
      dynamoRecord <- getDynamoRecord(id)
      content <- readContentFromS3(dynamoRecord.s3key)
    } yield Photo(dynamoRecord.id, dynamoRecord.createdBy, content)

  def saveAndThenGetPhoto(id: PhotoId, createdBy: String, content: Array[Byte]): F[Photo] =
    for {
      _ <- savePhoto(id, createdBy, content)
      photo <- getPhoto(id)
    } yield photo

}
