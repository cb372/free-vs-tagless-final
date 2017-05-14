package free.modular

import common._

import cats.free.{Free, Inject}

object DSL {

  class DynamoOps[F[_]](implicit I: Inject[DynamoAlg, F]) {

    def insertDynamoRecord(id: PhotoId, s3key: S3Key, createdBy: String): Free[F, DynamoRecord] = 
      Free.inject[DynamoAlg, F](InsertDynamoRecord(id, s3key, createdBy))

    def getDynamoRecord(id: PhotoId): Free[F, DynamoRecord] = 
      Free.inject[DynamoAlg, F](GetDynamoRecord(id))

  }

  object DynamoOps {
    implicit def dynamoOps[F[_]](implicit I: Inject[DynamoAlg, F]): DynamoOps[F] = new DynamoOps[F]
  }

  class S3Ops[F[_]](implicit I: Inject[S3Alg, F]) {

    def generateS3Key(id: PhotoId): Free[F, S3Key] =
      Free.inject[S3Alg, F](GenerateS3Key(id))

    def writeContentToS3(key: S3Key, content: Array[Byte]): Free[F, Unit] = 
      Free.inject[S3Alg, F](WriteContentToS3(key, content))

    def readContentFromS3(key: S3Key): Free[F, Array[Byte]] = 
      Free.inject[S3Alg, F](ReadContentFromS3(key))

  }

  object S3Ops {
    implicit def s3Ops[F[_]](implicit I: Inject[S3Alg, F]): S3Ops[F] = new S3Ops[F]
  }


}
