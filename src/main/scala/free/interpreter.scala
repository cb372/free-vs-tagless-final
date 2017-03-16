package free

import cats.~>

import scala.concurrent.Future

object Interpreters {

  val futureInterpreter = new (Algebra ~> Future) {
    override def apply[A](op: Algebra[A]): Future[A] = op match {
      case GenerateS3Key(id) => 
        println("Generating S3 key")
        Future.successful(S3Key(s"photos/$id"))
      
      case InsertDynamoRecord(id, s3key, createdBy) =>
        println("Inserting Dynamo record")
        // TODO write it to Dynamo
        Future.successful(DynamoRecord(id, s3key, createdBy))
      
      case GetDynamoRecord(id) =>
        println("Getting Dynamo record")
        // TODO look it up in Dynamo
        Future.successful(DynamoRecord(id, S3Key("the S3 key"), "Chris"))
      
      case WriteContentToS3(key, content) =>
        println("Writing to S3")
        // TODO write it to S3
        Future.successful(())
      
      case ReadContentFromS3(key) =>
        println("Reading from S3")
        // TODO read it from S3
        Future.successful("yolo".getBytes)
    }
  }

}
