package free.modular

import common._

object Programs {
  import DSL._

  def getPhoto(id: PhotoId)(implicit S: S3Ops[Algebra], D: DynamoOps[Algebra]): Program[Photo] = {
    import S._, D._

    for {
      dynamoRecord <- getDynamoRecord(id)
      content <- readContentFromS3(dynamoRecord.s3key)
    } yield Photo(dynamoRecord.id, dynamoRecord.createdBy, content)
  }

}
