package at.technikum.swe.service;

import at.technikum.swe.DAL.MySQLAccess;
import at.technikum.swe.DAL.TemperatureDAL;
import at.technikum.swe.DAO.Temperature;
import at.technikum.swe.foundation.Constants;
import at.technikum.swe.parser.CSVParser;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TemperatureService {

  private final static Logger logger = Logger.getLogger(TemperatureService.class.getName());

  private final MySQLAccess mySQLAccess = MySQLAccess.newInstance();

  private TemperatureDAL temperatureDAL;
  private Map<Long, String[]> data;

  public synchronized void initialDBLoad() {
    Connection con = null;
    boolean isCommit = true;

    try {
      con = mySQLAccess.getConnect();
      CSVParser csvParser = new CSVParser();
      this.data = csvParser.loadCSVAdvanced(Constants.CSV_YEAR_COLUMN,
          Constants.CSV_MONTH_COLUMN,
          Constants.CSV_DAY_COLUMN,
          Constants.CSV_MAX_COLUMN,
          Constants.CSV_MIN_COLUMN);

      temperatureDAL = new TemperatureDAL();

      if (temperatureDAL.tableExist(con)) {
        return;
      }

      temperatureDAL.createTable(con);

      isCommit = con.getAutoCommit();

      con.setAutoCommit(false);

      logger.info("Preparing loading CSV into table TEMPERATURE..");

      for (Map.Entry<Long, String[]> entry : data.entrySet()) {
        Temperature temperature = new Temperature();
        String[] tempArr = entry.getValue();
        int year = Integer.parseInt(tempArr[0]);
        int month = Integer.parseInt(tempArr[1]);
        int day = Integer.parseInt(tempArr[2]);

        if (tempArr[3].isEmpty()) {
          tempArr[3] = "0";
        }
        if (tempArr[4].isEmpty()) {
          tempArr[4] = "0";
        }

        float max = Float.valueOf(tempArr[3]);
        float min = Float.valueOf(tempArr[4]);

        final LocalDate localDate = LocalDate.of(year, month, day);
        temperature.setDate(localDate);
        temperature.setMinTemperature(min);
        temperature.setMaxTemperature(max);

        temperatureDAL.insert(con, temperature);
      }

      logger.info("Loading datarows finished..");

      con.commit();
    } catch (SQLException e) {
      try {
        con.rollback();
      } catch (SQLException e1) {
        logger.log(Level.SEVERE, "SQLException initialize data" + e.getMessage(), e);
      }
    } finally {
      try {
        con.setAutoCommit(isCommit);
      } catch (SQLException e) {
        logger.log(Level.SEVERE, "SQLException setAutoCommit failed" + e.getMessage(), e);
      }
      mySQLAccess.returnConnection(con);
    }
  }

}
