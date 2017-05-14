package free

import common._

import cats.free.Free

/*
 * Boilerplate to lift our algebra into Free
 * in order to create a DSL of operations.
 * 
 * These primitive operations can be monadically 
 * combined into larger programs.
 */
object DSL {

  type Program[A] = Free[Algebra, A]

  def generateS3Key(id: PhotoId): Program[S3Key] =
    Free.liftF(GenerateS3Key(id))

  def insertDynamoRecord(id: PhotoId, s3key: S3Key, createdBy: String): Program[DynamoRecord] = 
    Free.liftF(InsertDynamoRecord(id, s3key, createdBy))

  def getDynamoRecord(id: PhotoId): Program[DynamoRecord] = 
    Free.liftF(GetDynamoRecord(id))

  def writeContentToS3(key: S3Key, content: Array[Byte]): Program[Unit] = 
    Free.liftF(WriteContentToS3(key, content))

  def readContentFromS3(key: S3Key): Program[Array[Byte]] = 
    Free.liftF(ReadContentFromS3(key))

}
