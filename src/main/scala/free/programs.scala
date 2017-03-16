package free

/*
 * Examples of larger programs built up from the DSL primitives
 */
object Programs {
  import DSL._

  def savePhoto(id: PhotoId, createdBy: String, content: Array[Byte]): Op[Unit] =
    for {
      s3key <- generateS3Key(id)
      _ <- insertDynamoRecord(id, s3key, createdBy)
      _ <- writeContentToS3(s3key, content)
    } yield ()

  def getPhoto(id: PhotoId): Op[Photo] =
    for {
      dynamoRecord <- getDynamoRecord(id)
      content <- readContentFromS3(dynamoRecord.s3key)
    } yield Photo(dynamoRecord.id, dynamoRecord.createdBy, content)

  def saveAndThenGetPhoto(id: PhotoId, createdBy: String, content: Array[Byte]): Op[Photo] =
    for {
      _ <- savePhoto(id, createdBy, content)
      photo <- getPhoto(id)
    } yield photo

}
