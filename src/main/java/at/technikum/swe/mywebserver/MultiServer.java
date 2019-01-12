package at.technikum.swe.mywebserver;

import at.technikum.swe.DAL.MySQLAccess;
import at.technikum.swe.sensor.SensorReader;
import at.technikum.swe.service.TemperatureService;
import java.io.IOException;
import java.net.ServerSocket;
import java.sql.SQLException;
import java.util.logging.Logger;

public class MultiServer {

  private final static Logger logger = Logger.getLogger(MultiServer.class.getName());

  private final MySQLAccess mySQLAccess = MySQLAccess.newInstance();

  private ServerSocket serverSocket = null;

  public MultiServer(int port) throws IOException {
    this.serverSocket = new ServerSocket(port);
  }

  public void start() throws IOException, SQLException, ClassNotFoundException {

    mySQLAccess.initializeConnection();
    new TemperatureService().initialDBLoad();

    SensorReader sensorReader = new SensorReader();

    sensorReader.start();

    while (true) {
      /*
       * The accept() call blocks. That is, the program stops here and waits, possibly for hours or
       * days, until a client connects on port
       */
      new ClientHandler(serverSocket.accept()).start();
    }
  }

  public synchronized void stop() throws IOException, SQLException {
    mySQLAccess.closeAllConnections();
    logger.info("closed all connections..");
    serverSocket.close();
    logger.info("closed serversocket..");
  }
}
