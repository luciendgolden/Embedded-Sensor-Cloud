package main.java.mywebserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Logger;
import main.java.foundation.Ensurer;
import main.java.request.factory.RequestFactory;

public class ClientHandler extends Thread {

  private final static Logger logger = Logger.getLogger(ClientHandler.class.getName());

  private Socket clientSocket;

  private BufferedReader in;
  private PrintWriter out;

  private String requestLine;

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
      String requestLine;

      this.out = new PrintWriter(clientSocket.getOutputStream(), true);
      this.in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

      Ensurer.ensureNotNull(in, "BufferedReader");
      Ensurer.ensureNotNull(out, "PrintWriter");

      RequestFactory.createRequest(clientSocket.getInputStream());


      /*
      while ((requestLine = in.readLine()) != null) {

        logger
            .info(String.format("Received message: %s", requestLine));

        if (requestLine.equals(".")) {
          out.println("EOF");
          break;
        } else {
          setRequestLine(requestLine);
          out.println(requestLine);
        }

      }
      */

      in.close();
      out.close();
      clientSocket.close();

    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public Socket getClientSocket() {
    return clientSocket;
  }

  public void setClientSocket(Socket clientSocket) {
    this.clientSocket = clientSocket;
  }

  public BufferedReader getIn() {
    return in;
  }

  public void setIn(BufferedReader in) {
    this.in = in;
  }

  public PrintWriter getOut() {
    return out;
  }

  public void setOut(PrintWriter out) {
    this.out = out;
  }

  public String getRequestLine() {
    return requestLine;
  }

  public void setRequestLine(String requestLine) {
    this.requestLine = requestLine;
  }
}
