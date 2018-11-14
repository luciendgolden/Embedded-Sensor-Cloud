package main.java;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import main.java.mywebserver.MultiServer;

public class Main {

  private final static Logger logger = Logger.getLogger(Main.class.getName());

  private final static int MY_PORT = 8082;

  public static void main(String[] args) {
    MultiServer server = null;

    try {
      logger.info("Starting server on port " + MY_PORT);
      server = new MultiServer(MY_PORT);
      server.start();
      server.stop();

    } catch (IOException e) {
      logger.log(Level.SEVERE, "Unexpected error " + e.getMessage(), e);
    }
  }

}
