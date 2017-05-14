package free.modular

import common._

import cats.~>
import cats.data.OptionT
import cats.instances.future._

import scala.concurrent.ExecutionContext.Implicits.global

object Interpreters {

  val futureOfOptionS3Interpreter = new (S3Alg ~> FutureOfOption) {
    override def apply[A](op: S3Alg[A]): FutureOfOption[A] = op match {
      case GenerateS3Key(id) => 
        println("Generating S3 key")
        OptionT.pure(S3Key(s"photos/$id"))
      
      case WriteContentToS3(key, content) =>
        println("Writing to S3")
        // TODO write it to S3
        OptionT.pure(())
      
      case ReadContentFromS3(key) =>
        println("Reading from S3")
        // TODO read it from S3
        OptionT.pure("yolo".getBytes)
    }
  }

  val futureOfOptionDynamoInterpreter = new (DynamoAlg ~> FutureOfOption) {
    override def apply[A](op: DynamoAlg[A]): FutureOfOption[A] = op match {
      case InsertDynamoRecord(id, s3key, createdBy) =>
        println("Inserting Dynamo record")
        // TODO write it to Dynamo
        OptionT.pure(DynamoRecord(id, s3key, createdBy))
      
      case GetDynamoRecord(id) =>
        println("Getting Dynamo record")
        // TODO look it up in Dynamo
        OptionT.pure(DynamoRecord(id, S3Key("the S3 key"), "Chris"))
    }
  }

  val futureOfOptionInterpreter: (Algebra ~> FutureOfOption) = 
    futureOfOptionS3Interpreter or futureOfOptionDynamoInterpreter
}
