package free

import common._

import cats.~>
import cats.data.OptionT
import cats.instances.future._

import scala.concurrent.ExecutionContext.Implicits.global

object Interpreters {

  /**
   * An example interpreter with `Future` as its effect.
   *
   * The interpreter is a natural transformation 
   * from Algebra to some monad, in this case Future.
   */
  val futureOfOptionInterpreter = new (Algebra ~> FutureOfOption) {
    override def apply[A](op: Algebra[A]): FutureOfOption[A] = op match {
      case GenerateS3Key(id) => 
        println("Generating S3 key")
        OptionT.pure(S3Key(s"photos/$id"))
      
      case InsertDynamoRecord(id, s3key, createdBy) =>
        println("Inserting Dynamo record")
        // TODO write it to Dynamo
        OptionT.pure(DynamoRecord(id, s3key, createdBy))
      
      case GetDynamoRecord(id) =>
        println("Getting Dynamo record")
        // TODO look it up in Dynamo
        OptionT.pure(DynamoRecord(id, S3Key("the S3 key"), "Chris"))
      
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

}
