package at.technikum.swe.sensor;

import at.technikum.swe.DAO.MySQLAccess;
import at.technikum.swe.DAO.TemperatureDAO;
import at.technikum.swe.domain.Temperature;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SensorReader extends Thread {

  private final static Logger logger = Logger.getLogger(SensorReader.class.getName());

  private Connection con = null;
  private Temperature readTemp;

  @Override
  public void run() {
    MySQLAccess mySQLAccess = MySQLAccess.newInstance();

    try {
      con = mySQLAccess.getConnect();
      Random r = new Random();
      float random;

      while (true) {
        getTemp();
        readTemp.setDate(LocalDate.now());
        random = -10f + r.nextFloat() * (38f + 10f);
        readTemp.setMinTemperature(random);
        random = -10f + r.nextFloat() * (38f + 10f);
        readTemp.setMaxTemperature(random);

        TemperatureDAO temperatureDAO = new TemperatureDAO();
        temperatureDAO.insert(con, readTemp);
        logger.info(String.format("Inserted new datarow %s", readTemp));
        sleep(6000000l);
      }
    } catch (SQLException e) {
      logger.log(Level.SEVERE, "SQL-Exception error " + e.getMessage(), e);
    } catch (InterruptedException e) {
      logger.log(Level.SEVERE, "thread interrupted suddenly" + e.getMessage(), e);
    } finally {
      mySQLAccess.returnConnection(con);
    }
  }

  private synchronized void getTemp() {
    readTemp = new Temperature();
  }


}
