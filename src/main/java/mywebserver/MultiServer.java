package main.java.mywebserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.logging.Logger;

public class MultiServer {

  private final static Logger logger = Logger.getLogger(MultiServer.class.getName());

  private ServerSocket serverSocket;

  public MultiServer(int port) throws IOException {
    this.serverSocket = new ServerSocket(port);
  }

  public void start() throws IOException {
    while (true){
      /*
       * The accept() call blocks. That is, the program stops here and waits, possibly for hours or
       * days, until a client connects on port
       */
      new ClientHandler(serverSocket.accept()).start();
    }
  }

  public void stop() throws IOException {
    serverSocket.close();
  }
}
