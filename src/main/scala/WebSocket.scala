import java.io._
import java.net._

object WebSocket {
  val builtHeader = 
    "HTTP/1.1 200 OK\r\nAccept_Ranges: bytes\r\nConnection: close\r\n" +
    "Server: TinyScalaHTTPServ v0.1\r\nContent-type: text/html\r\n\r\n"

  private def readFile(file: String): String = {
    val servFile = if( file.startsWith("/") ) file.replace("/","") else file
    try { 
      scala.io.Source.fromFile(servFile).getLines.mkString 
    } catch { case _ => "404 NOT FOUND" }
  }

  def htmlProcess(req: String) =
    if( req.startsWith("GET") ) readFile(req.split(" ")(1)) else "400 Bad Request"

  def main(args: Array[String]) {
    println("WebServer running on port 8081")
    val client = new ServerSocket(8081).accept()
    val in = new BufferedReader(new InputStreamReader(client.getInputStream()))
    val output = new DataOutputStream(client.getOutputStream())
    val line = in.readLine()
    output.writeBytes(builtHeader)
    output.writeBytes(htmlProcess(line))
    output.close()
  }
}
