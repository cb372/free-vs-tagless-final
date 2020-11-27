package free

import common._

import cats.~>
import cats.Id
import cats.data.Writer
import cats.instances.list._

import org.scalatest._
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class FreeTest extends AnyFlatSpec with Matchers {

  import Programs._

  def testInterpreter(
    dynamo: Map[PhotoId, DynamoRecord],
    s3: Map[S3Key, Array[Byte]]
  ) = new (Algebra ~> Id) {
    def apply[A](op: Algebra[A]): A = op match {
      case GetDynamoRecord(id) => dynamo(id)
      case ReadContentFromS3(key) => s3(key)

      // irrelevant to this test
      case GenerateS3Key(_) => ???
      case InsertDynamoRecord(_, _, _) => ???
      case WriteContentToS3(_, _) => ???
    }
  }
  
  it should "fetch a photo from Dynamo and S3" in {
    val photoId = PhotoId("abc")
    val s3Key = S3Key("photos/abc")
    val bytes = "yolo".getBytes

    val interpreter = testInterpreter(
      dynamo = Map(
        photoId -> DynamoRecord(photoId, s3Key, "Chris")
      ),
      s3 = Map(
        s3Key -> bytes
      )
    )

    getPhoto(photoId).foldMap(interpreter) shouldBe
      Photo(photoId, "Chris", bytes)
  }

  type Log[A] = Writer[List[String], A]

  def loggingInterpreter(
    dynamo: Map[PhotoId, DynamoRecord],
    s3: Map[S3Key, Array[Byte]]
  ) = new (Algebra ~> Log) {
    def apply[A](op: Algebra[A]): Log[A] = op match {
      case GetDynamoRecord(id) => Writer(
        List(s"Get Dynamo record with ID ${id.value}"),
        dynamo(id)
      )

      case ReadContentFromS3(key) => Writer(
        List(s"Get S3 file with key ${key.value}"),
        s3(key)
      )

      // irrelevant to this test
      case GenerateS3Key(_) => ???
      case InsertDynamoRecord(_, _, _) => ???
      case WriteContentToS3(_, _) => ???
    }
  }

  it should "perform the operations I expect" in {
    val photoId = PhotoId("abc")
    val s3Key = S3Key("photos/abc")
    val bytes = "yolo".getBytes

    val interpreter = loggingInterpreter(
      dynamo = Map(
        photoId -> DynamoRecord(photoId, s3Key, "Chris")
      ),
      s3 = Map(
        s3Key -> bytes
      )
    )

    val (logs, result) = getPhoto(photoId)
      .foldMap(interpreter) 
      .run
    result shouldBe Photo(photoId, "Chris", bytes)
    logs shouldBe List(
      "Get Dynamo record with ID abc",
      "Get S3 file with key photos/abc"
    )
  }

}
