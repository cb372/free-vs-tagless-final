package tagless.modular

import common._

import scala.language.higherKinds

trait S3Alg[F[_]] {

  def generateS3Key(id: PhotoId): F[S3Key]

  def writeContentToS3(key: S3Key, content: Array[Byte]): F[Unit]

  def readContentFromS3(key: S3Key): F[Array[Byte]]

}

trait DynamoAlg[F[_]] {

  def insertDynamoRecord(id: PhotoId, s3key: S3Key, createdBy: String): F[DynamoRecord]

  def getDynamoRecord(id: PhotoId): F[DynamoRecord]

}
