
lazy val commonSettings = Seq(
  version := "0.1",
  scalaVersion := "2.11.4"
)

lazy val root = (project in file(".")).
  settings(commonSettings: _*).
  settings(
    name := "TinyWebServer"
  )
