package free

/*
 * An algebra of primitive operations that we want to support
 *
 * `A` is the return type of the operation.
 */
sealed trait Algebra[A]

case class GenerateS3Key(id: PhotoId) extends Algebra[S3Key]

case class InsertDynamoRecord(id: PhotoId, s3key: S3Key, createdBy: String) extends Algebra[DynamoRecord]

case class GetDynamoRecord(id: PhotoId) extends Algebra[DynamoRecord]

case class WriteContentToS3(key: S3Key, content: Array[Byte]) extends Algebra[Unit]

case class ReadContentFromS3(key: S3Key) extends Algebra[Array[Byte]]

