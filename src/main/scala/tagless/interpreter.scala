package tagless

import common._

import cats.Monad
import cats.data.OptionT
import cats.instances.future._

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

trait FutureOfOptionMonadic extends Monadic[FutureOfOption] {

  implicit def M: Monad[FutureOfOption] = futureOfOptionMonad

}

trait FutureOfOptionS3 extends DynamoAlg[FutureOfOption] {

  def generateS3Key(id: PhotoId): FutureOfOption[S3Key] = {
    println("Generating S3 key")
    OptionT.pure(S3Key(s"photos/$id"))
  }

  def writeContentToS3(key: S3Key, content: Array[Byte]): FutureOfOption[Unit] = {
    println("Writing to S3")
    // TODO write it to S3
    OptionT.pure(())
  }

  def readContentFromS3(key: S3Key): FutureOfOption[Array[Byte]] = {
    println("Reading from S3")
    // TODO read it from S3
    OptionT.pure("yolo".getBytes)
  }

}

trait FutureOfOptionDynamo extends DynamoAlg[FutureOfOption] {

  def insertDynamoRecord(id: PhotoId, s3key: S3Key, createdBy: String): FutureOfOption[DynamoRecord] = {
    println("Inserting Dynamo record")
    // TODO write it to Dynamo
    OptionT.pure(DynamoRecord(id, s3key, createdBy))
  }

  def getDynamoRecord(id: PhotoId): FutureOfOption[DynamoRecord] = {
    println("Getting Dynamo record")
    // TODO look it up in Dynamo
    OptionT.pure(DynamoRecord(id, S3Key("the S3 key"), "Chris"))
  }

}

object FutureOfOptionInterpreter 
  extends FutureOfOptionDynamo with DynamoAlg[FutureOfOption]
  with FutureOfOptionS3 with S3Alg[FutureOfOption]
  with FutureOfOptionMonadic with Monadic[FutureOfOption]
