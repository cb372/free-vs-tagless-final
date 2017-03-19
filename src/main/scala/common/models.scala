package common

case class PhotoId(value: String) extends AnyVal

case class S3Key(value: String) extends AnyVal

case class Photo(id: PhotoId, createdBy: String, content: Array[Byte])

case class DynamoRecord(id: PhotoId, s3key: S3Key, createdBy: String)
