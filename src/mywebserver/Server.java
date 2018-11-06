package mywebserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server{

  private ServerSocket serverSocket;
  private Socket socket;
  private BufferedReader in;
  private PrintWriter out;

  private String input;

  public Server() {
  }

  /**
   * Constructs a handler thread, squirreling away the socket. All the interesting work is done in
   * the run method.
   */
  public Server(int port) throws IOException {
    this.serverSocket = new ServerSocket(port);
    //The accept() call blocks. That is, the program stops here and waits, possibly for hours or days, until a client connects on port
    this.socket = serverSocket.accept();
  }

  public void start() throws IOException {
    setIn(socket.getInputStream());
    setOut(socket.getOutputStream());

    setInput(readMessageFirstLine());
    writeMessage();

    stop();
  }

  public void stop() throws IOException {
    in.close();
    out.close();
    socket.close();
    serverSocket.close();
  }

  public String readMessageFirstLine() throws IOException {
    if (in != null) {
      final String raw = in.readLine();

      if (raw == null) {
        throw new IllegalArgumentException("Input must not be null!");
      }

      return raw.split(" ")[1]; // i.e. GET /index.html?x=1&y=2 HTTP1.1
    }

    throw new IllegalArgumentException("BufferedReader must not be null!");
  }

  public void writeMessage() throws IOException {
    if (out != null) {
      if (input.equals("index.html")) {
        out.println("hello client");
      } else {
        out.println("unrecognised greeting");
      }

      out.flush();
    }else {
      throw new IllegalArgumentException("PrintWriter must not be null!");
    }
  }

  public ServerSocket getServerSocket() {
    return serverSocket;
  }

  public void setServerSocket(ServerSocket serverSocket) {
    this.serverSocket = serverSocket;
  }

  public Socket getSocket() {
    return socket;
  }

  public void setSocket(Socket socket) {
    this.socket = socket;
  }

  public BufferedReader getIn() {
    return in;
  }

  public void setIn(InputStream in) {
    this.in = new BufferedReader(new InputStreamReader(in));
  }

  public PrintWriter getOut() {
    return out;
  }

  public void setOut(OutputStream out) {
    this.out = new PrintWriter(new OutputStreamWriter(out));
  }

  public String getInput() {
    return input;
  }

  public void setInput(String input) {
    this.input = input;
  }
}
