package at.technikum.swe.mywebserver;

import BIF.SWE1.interfaces.Request;
import BIF.SWE1.interfaces.Response;
import at.technikum.swe.request.RequestImpl;
import at.technikum.swe.response.ResponseImpl;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.logging.Logger;

public class ClientHandler extends Thread {

  private final static Logger logger = Logger.getLogger(ClientHandler.class.getName());

  private Socket clientSocket;

  /**
   * Constructs a handler thread, squirreling away the clientSocket. All the interesting work is
   * done in the run method.
   */
  public ClientHandler(Socket socket) {
    this.clientSocket = socket;
  }

  @Override
  public void run() {
    try {
      //ClassLoader classLoader = getClass().getClassLoader();
      //InputStream in = classLoader.getResourceAsStream("WEB-INF/index.html");
      final String content = "<html>\n"
          + "<body>\n"
          + "<h1>Hello, World!</h1>\n"
          + "</body>\n"
          + "</html>";

      //TODO - handle at.technikum.swe.request
      Request req = new RequestImpl(clientSocket.getInputStream());

      Response res = new ResponseImpl();
      res.setStatusCode(200);
      res.addHeader("Date", "Mon, 27 Jul 2009 12:28:53 GMT");
      res.setContentType("text/html");
      res.setContent(content);
      res.send(clientSocket.getOutputStream());

      clientSocket.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
