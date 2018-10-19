package mywebserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.logging.Logger;
import uebungen.UEB1Impl;

public class Main {

  private final static int MY_PORT = 8082;

  public static void main(String[] args) throws Exception {
    final ServerSocket listener = new ServerSocket(MY_PORT);
    System.out.printf("Started Server on Port %s\n", listener.getLocalPort());
    try {
        new Handler(listener.accept()).start();
    }finally {
      listener.close();
    }
  }

}
