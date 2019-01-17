package at.technikum.swe;

import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import at.technikum.swe.mywebserver.MultiServer;

public class Main {

  private final static Logger logger = Logger.getLogger(Main.class.getName());

  private final static int MY_PORT = 8080;

  public static void main(String[] args) {
    MultiServer server = null;

    try {
      logger.info("Starting server on port 127.0.0.1:" + MY_PORT);
      server = new MultiServer(MY_PORT);
      server.start();
    } catch (IOException e) {
      logger.log(Level.SEVERE, "Cannot open port " + MY_PORT, e);
    } catch (SQLException | ClassNotFoundException e) {
      logger.log(Level.SEVERE, "Unexpected SQL Initializiation error " + e.getMessage(), e);
    } finally {
      try {
        server.stop();
        logger.info("stopped server..");
      } catch (IOException | SQLException e) {
        logger.log(Level.SEVERE, "Unexpected error " + e.getMessage(), e);
      }
    }
  }

}
