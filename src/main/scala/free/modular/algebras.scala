package free.modular

import common._

/*
 * Separate algebras for S3 operations and Dynamo operations
 */

sealed trait S3Alg[A]

case class GenerateS3Key(id: PhotoId) extends S3Alg[S3Key]

case class WriteContentToS3(key: S3Key, content: Array[Byte]) extends S3Alg[Unit]

case class ReadContentFromS3(key: S3Key) extends S3Alg[Array[Byte]]

sealed trait DynamoAlg[A]

case class InsertDynamoRecord(id: PhotoId, s3key: S3Key, createdBy: String) extends DynamoAlg[DynamoRecord]

case class GetDynamoRecord(id: PhotoId) extends DynamoAlg[DynamoRecord]

