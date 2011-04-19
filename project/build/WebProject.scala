import sbt._

class WebProject(info: ProjectInfo) extends DefaultProject(info)
  with extract.BasicSelfExtractingProject {
  override def installActions = "update" :: "run" :: Nil
}
