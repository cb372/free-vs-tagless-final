scalaVersion := "2.12.1"

libraryDependencies ++= Seq(
  "org.typelevel" %% "cats-free" % "0.9.0",
  "org.scalatest" %% "scalatest" % "3.0.1" % Test,

  "org.scala-lang" % "scala-compiler" % scalaVersion.value // for REPLesent
)

scalacOptions ++= Seq("-language:_", "-feature", "-nowarn") // for REPLesent
