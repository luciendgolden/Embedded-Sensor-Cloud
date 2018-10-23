package mywebserver;

import BIF.SWE1.interfaces.Request;
import BIF.SWE1.interfaces.Url;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import uebungen.UEB1Impl;

public abstract class Server implements Request {

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
    this.socket = serverSocket.accept();
  }

  public void start() throws IOException {
    setIn(socket.getInputStream());
    setOut(socket.getOutputStream());

    readMessage();
    writeMessage();

    stop();
  }

  public void stop() throws IOException {
    in.close();
    out.close();
    socket.close();
    serverSocket.close();
  }

  public String readMessage() throws IOException {
    if(in != null){
      this.input = in.readLine();

      if (input == null) {
        throw new IllegalArgumentException("Input must not be null!");
      }

      return input.split(" ")[1]; // i.e. GET /index.html?x=1&y=2 HTTP1.1
    }

    throw new IllegalArgumentException("BufferedReader must not be null!");
  }

  public void writeMessage() throws IOException {
    if(out != null){
      if (input.equals("index.html")) {
        out.println("hello client");
      }
      else {
        out.println("unrecognised greeting");
      }
    }

    throw new IllegalArgumentException("PrintWriter must not be null!");
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
}
