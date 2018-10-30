package mywebserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {

  //logger for audit stuff
  private final static Logger logger = Logger.getLogger("requests");

  private final static int MY_PORT = 8082;

  public static void main(String[] args) {
    Server server = null;

    try {
      logger.info( "Starting server on port " + MY_PORT);
      server = new Server(MY_PORT);
      server.start();

      System.out.println(server.getInput());
    } catch (IOException e) {
      logger.log(Level.SEVERE, "Unexpected error " + e.getMessage(), e);
    }
  }

}
