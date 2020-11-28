scalaVersion := "2.13.3"

libraryDependencies ++= Seq(
  "org.typelevel" %% "cats-free" % "2.3.0",
  "org.scalatest" %% "scalatest" % "3.2.2" % Test,

  "org.scala-lang" % "scala-compiler" % scalaVersion.value // for REPLesent
)

scalacOptions ++= Seq("-language:_", "-feature", "-nowarn") // for REPLesent
