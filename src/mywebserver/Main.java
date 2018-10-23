package mywebserver;

import java.net.ServerSocket;

public class Main {

  private final static int MY_PORT = 8082;

  public static void main(String[] args) throws Exception {
    Server server = new ServerImpl(MY_PORT);
    server.start();
  }

}
