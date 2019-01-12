package at.technikum.swe.DAL;

import at.technikum.swe.common.Configuration;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MySQLAccess {

  private final static Logger logger = Logger.getLogger(MySQLAccess.class.getName());

  private static MySQLAccess instance = null;

  private static final int INITIAL_CAPACITY = 50;

  private final LinkedList<Connection> connectionPool = new LinkedList<>();

  private static Configuration configuration = null;

  private static boolean isInit = false;

  private final String username = configuration.getProperty("mysql.username");
  private final String password = configuration.getProperty("mysql.password");
  private final String db = configuration.getProperty("mysql.db");

  static {
    try {
      // This will load the MySQL driver, each DB has its own driver
      Class.forName("com.mysql.cj.jdbc.Driver");

      configuration = new Configuration().initialize();
    } catch (IOException | ClassNotFoundException e) {
      logger.log(Level.SEVERE, "Unexpected error " + e.getMessage(), e);
    }
  }

  public static synchronized MySQLAccess newInstance(){
    if(instance == null){
      instance = new MySQLAccess();
    }

    return instance;
  }

  public void initializeConnection() throws SQLException, ClassNotFoundException {
    if(!isInit) {
      for (int i = 0; i < INITIAL_CAPACITY; i++) {
        connectionPool.add(DriverManager.getConnection(
            String.format("jdbc:mysql://localhost/%s", db), username, password));
      }
      isInit = true;
    }else {
      throw new IllegalArgumentException("MySQLAccess already initialized!");
    }
  }

  public Connection getConnect() throws SQLException {
    if (connectionPool.isEmpty()) {
      connectionPool.add(DriverManager.getConnection(
          String.format("jdbc:mysql://localhost/%s", db), username, password));
    }
    return connectionPool.pop();
  }

  public synchronized void returnConnection(Connection connection) {
    connectionPool.push(connection);
  }

  public synchronized void closeAllConnections() throws SQLException {
    for(Connection con:connectionPool){
      con.close();
    }
  }
}
