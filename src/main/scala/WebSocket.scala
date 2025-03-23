import java.io._
import java.net._

case class Response(statusCode: Int, resp: String)

object WebSocket {
    private val builtHeader = 
    "HTTP/1.1 200 OK\r\nAccept_Ranges: bytes\r\nConnection: close\r\n" +
    "Server: TinyScalaHTTPServ v0.1\r\nContent-type: text/html\r\n\r\n"

    private def parseFile(file: String) = {
        val MatchFile = """GET (\/.*) HTTP.*""".r
        file match {
            case MatchFile(readFile) if readFile.endsWith("/") => "index.html"
            case MatchFile(readFile) if !(readFile contains "..") => readFile.replace("/","")
            case _ => throw new Exception("File not found!")
        }
    }

    private def buildHeader(statusCode: Int): String = {
        // Depending on the StatuCode, build header response.
        statusCode match {
            case 200 => "200 OK\r\n"
            case 404 => "404 NOT FOUND\r\n"
            case _   => "500 SERVER ERROR\r\n"
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
            } catch { case _: Throwable => List("Not Found!") }

        val header = buildHeader(fileRead)
        output.writeBytes(header)
        fileRead.foreach{ output.writeBytes }
        output.close()
        client.close()
    }
}
