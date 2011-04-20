import java.io._
import java.net._

object WebSocket {
  val builtHeader = 
    "HTTP/1.1 200 OK\r\nAccept_Ranges: bytes\r\nConnection: close\r\n" +
    "Server: TinyScalaHTTPServ v0.1\r\nContent-type: text/html\r\n\r\n"

  def parseFile(file: String) = {
    println(file)
    val MatchFile = """GET (\/.*) HTTP.*""".r
    file match {
      case MatchFile(readFile) if readFile.endsWith("/") => "index.html"
      case MatchFile(readFile) if !(readFile contains "..") => readFile.replace("/","")
      case _ => throw new Exception("File not found!")
    }
  }

  def main(args: Array[String]) {
    println("WebServer running on port 8081")
    val client = new ServerSocket(8081).accept()
    val in = new BufferedReader(new InputStreamReader(client.getInputStream()))
    val output = new DataOutputStream(client.getOutputStream())
    val line = in.readLine()

    val fileRead = 
      try {
        scala.io.Source.fromFile(parseFile(line)).getLines
      } catch { case _ => List("404 File Not Found!") }
    output.writeBytes(builtHeader)
    fileRead.foreach{ output.writeBytes }
    output.close()
    client.close()
  }
}
