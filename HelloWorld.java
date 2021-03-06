import java.io.*;
import java.net.*;

public class HelloWorld {
    private static final String responseBody ="<html><body><p><img src=\"cool.jpg\" alt=\"parakeet\"></p></body></html>";

    public static void main(String... args) throws IOException {
        int port = Integer.parseInt(args[0]);
        try(ServerSocket serverSocket = new ServerSocket(port)) {
            while(true)
                try(Socket socket = serverSocket.accept();
                    InputStream input = socket.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                    OutputStream output = socket.getOutputStream();
                    PrintWriter writer = new PrintWriter(new OutputStreamWriter(output))) {
                    // Request-Header empfangen und ignorieren
                    for(String line = reader.readLine(); !line.isEmpty(); line = reader.readLine())
                        ;
                    System.out.println("request from " + socket.getRemoteSocketAddress());

                    // Response-Header senden
                    writer.println("HTTP/1.0 200 OK");
                    writer.println("Content-Type: text/html; charset=ISO-8859-1");
                    writer.println("Server: NanoHTTPServer");
                    writer.println();
                    // Response-Body senden
                    writer.println(responseBody);
                }
                catch(IOException iox) {
                    // Fehler
                }
        }
    }
}
