name := "TinyScalaWeb"

version := "1.1"

scalaVersion := "2.12.8"

libraryDependencies ++= Seq(
    "org.scalatest" %% "scalatest" % "3.0.5" % "test"
)

scalacOptions ++= Seq(
    "-deprecation",
    "-unchecked",
    "-explaintypes",
    "-Ywarn-dead-code",
    "-Xfatal-warnings"
)
