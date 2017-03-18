package free

import common._

import cats.~>
import cats.Id
import org.scalatest._

class FreeTest extends FlatSpec with Matchers {

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

    getPhoto(photoId).foldMap(interpreter) shouldBe Photo(
      photoId, "Chris", bytes
    )
  }

}
