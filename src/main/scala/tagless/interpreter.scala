package tagless

import common._

import cats.Monad
import scala.concurrent.Future

/**
 * An example interpreter with `Future` as its effect.
 */
object FutureInterpreter extends Algebra[Future] {

  import scala.concurrent.ExecutionContext.Implicits.global
  import cats.instances.future._

  implicit val M: Monad[Future] = implicitly

  def generateS3Key(id: PhotoId): Future[S3Key] = {
    println("Generating S3 key")
    Future.successful(S3Key(s"photos/$id"))
  }

  def insertDynamoRecord(id: PhotoId, s3key: S3Key, createdBy: String): Future[DynamoRecord] = {
    println("Inserting Dynamo record")
    // TODO write it to Dynamo
    Future.successful(DynamoRecord(id, s3key, createdBy))
  }

  def getDynamoRecord(id: PhotoId): Future[DynamoRecord] = {
    println("Getting Dynamo record")
    // TODO look it up in Dynamo
    Future.successful(DynamoRecord(id, S3Key("the S3 key"), "Chris"))
  }

  def writeContentToS3(key: S3Key, content: Array[Byte]): Future[Unit] = {
    println("Writing to S3")
    // TODO write it to S3
    Future.successful(())
  }

  def readContentFromS3(key: S3Key): Future[Array[Byte]] = {
    println("Reading from S3")
    // TODO read it from S3
    Future.successful("yolo".getBytes)
  }

}
