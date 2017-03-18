package tagless

import common._

import cats._
import org.scalatest._

class TaglessFinalTest extends FlatSpec with Matchers {

  def testAlgebra(
    dynamo: Map[PhotoId, DynamoRecord],
    s3: Map[S3Key, Array[Byte]]
  ) = new Algebra[Id] {

    implicit def M = implicitly

    def getDynamoRecord(id: PhotoId) = dynamo(id)
    def readContentFromS3(key: S3Key) = s3(key)

    // irrelevant to this test
    def generateS3Key(x: PhotoId) = ???
    def insertDynamoRecord(x: PhotoId, y: S3Key, z: String) = ???
    def writeContentToS3(x: S3Key, z: Array[Byte]) = ???
  }
  
  it should "fetch a photo from Dynamo and S3" in {
    val photoId = PhotoId("abc")
    val s3Key = S3Key("photos/abc")
    val bytes = "yolo".getBytes

    val algebra = testAlgebra(
      dynamo = Map(
        photoId -> DynamoRecord(photoId, s3Key, "Chris")
      ),
      s3 = Map(
        s3Key -> bytes
      )
    )

    val programs = new Programs(algebra)

    programs.getPhoto(photoId) shouldBe Photo(
      photoId, "Chris", bytes
    )
  }

}
