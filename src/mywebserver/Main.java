package mywebserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.logging.Logger;
import uebungen.UEB1Impl;

public class Main {

  private final static int MY_PORT = 8082;

  public static void main(String[] args) throws Exception {
    final Server server = new ServerImpl(MY_PORT);
    server.start();
  }

}
