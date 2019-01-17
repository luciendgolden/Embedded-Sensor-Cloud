package at.technikum.swe.DAO;

import at.technikum.swe.domain.Temperature;
import at.technikum.swe.exception.PersistenceException;
import at.technikum.swe.persistence.AbstractJdbcRepository;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

public class TemperatureDAO extends AbstractJdbcRepository<Temperature, Long> {

  private final static Logger logger = Logger.getLogger(TemperatureDAO.class.getName());
  public static final int COLUMN_IDX_ID = 1;
  public static final int COLUMN_IDX_DATE = 2;
  public static final int COLUMN_IDX_MIN_TEMP = 3;
  public static final int COLUMN_IDX_MAX_TEMP = 4;

  public static final String COUNTER = "counter";

  public TemperatureDAO() {
    super(Temperature.class);
  }

  /**
   * Insert a new row into db
   *
   * @param con Connection der Datenverbindung dataSource.getConnection
   * @param entity Produkt Entität
   */
  @Override
  public int insert(Connection con, Temperature entity) throws SQLException {
    final String usDatePattern = "yyyy-MM-dd";
    DateTimeFormatter usDateFormatter = DateTimeFormatter.ofPattern(usDatePattern);
    final String date = usDateFormatter.format(entity.getDate());

    PreparedStatement pstmt = con
        .prepareStatement("INSERT INTO TEMPERATURE " +
            "               (TEMP_DATE, TEMP_MIN, TEMP_MAX) VALUES (?, ?, ?)");
    pstmt.setDate(1, java.sql.Date.valueOf(date));
    pstmt.setFloat(2, entity.getMinTemperature());
    pstmt.setFloat(3, entity.getMaxTemperature());

    return pstmt.executeUpdate();
  }

  @Override
  public int createTable(Connection con) throws SQLException {
    PreparedStatement pstmt = con
        .prepareStatement("CREATE TABLE IF NOT EXISTS TEMPERATURE (\n"
            + "  ID INTEGER NOT NULL AUTO_INCREMENT, \n"
            + "  TEMP_DATE DATE NOT NULL, \n"
            + "  TEMP_MIN DECIMAL(10, 2) NOT NULL, \n"
            + "  TEMP_MAX DECIMAL(10, 2) NOT NULL,\n"
            + "  PRIMARY KEY (id)\n"
            + ")");

    return pstmt.executeUpdate();
  }

  public List<Temperature> findByDate(Connection con, Date date) throws SQLException {

    entities.clear();

    // create SQL query to fetch all player records
    final String sqlSelectQuery = "SELECT * FROM \n"
        + "TEMPERATURE \n"
        + "WHERE TEMP_DATE = ? \n"
        + "ORDER BY TEMP_DATE ASC";

    // Step 2.B: Creating JDBC Statement
    PreparedStatement preparedStatement = con.prepareStatement(sqlSelectQuery);
    preparedStatement.setDate(1, (java.sql.Date) date);

    // Step 2.C: Executing SQL & retrieve data into ResultSet
    ResultSet resultSet = preparedStatement.executeQuery();

    // processing returned data and printing into console
    setTemperatureObj(resultSet);

    return entities;
  }

  public List<Temperature> findDateBetween(Connection con, Date startDate, Date endDate,
      Optional<Integer> page, Optional<Integer> limit)
      throws SQLException {
    entities.clear();
    final int myPage = page.orElse(1);
    final int defaultLimit = limit.orElse(50);

    // create SQL query to fetch all player records
    final String sqlSelectQuery = "SELECT * FROM TEMPERATURE \n"
        + "WHERE TEMP_DATE BETWEEN ? AND ? \n"
        + "ORDER BY TEMP_DATE ASC\n"
        + "LIMIT ?\n"
        + "OFFSET ?";

    // Step 2.B: Creating JDBC Statement
    PreparedStatement preparedStatement = con.prepareStatement(sqlSelectQuery);
    preparedStatement.setDate(1, (java.sql.Date) startDate);
    preparedStatement.setDate(2, (java.sql.Date) endDate);

    preparedStatement.setInt(3, defaultLimit);
    preparedStatement.setInt(4,  defaultLimit * (myPage-1));


    logger.info(String.format("Preparing statement %s", preparedStatement));

    // Step 2.C: Executing SQL & retrieve data into ResultSet
    ResultSet resultSet = preparedStatement.executeQuery();

    // processing returned data and printing into console
    setTemperatureObj(resultSet);

    return entities;
  }

  public Integer countDateBetween(Connection con, Date startDate, Date endDate)
      throws SQLException {
    Integer counter = 0;

    // create SQL query to fetch all player records
    final String sqlSelectQuery = "SELECT count(*) as counter\n"
        + "FROM TEMPERATURE\n"
        + "WHERE TEMP_DATE\n"
        + "BETWEEN ?\n"
        + "AND ?";

    // Step 2.B: Creating JDBC Statement
    PreparedStatement preparedStatement = con.prepareStatement(sqlSelectQuery);
    preparedStatement.setDate(1, (java.sql.Date) startDate);
    preparedStatement.setDate(2, (java.sql.Date) endDate);

    // Step 2.C: Executing SQL & retrieve data into ResultSet
    ResultSet resultSet = preparedStatement.executeQuery();

    while (resultSet.next()) {
      counter = resultSet.getInt(COUNTER);
    }

    return counter;
  }

  private void setTemperatureObj(ResultSet resultSet) throws SQLException {
    while (resultSet.next()) {
      Temperature temperature = new Temperature();

      temperature.setId(resultSet.getLong(COLUMN_IDX_ID));
      temperature.setDate(resultSet.getDate(COLUMN_IDX_DATE).toLocalDate());
      temperature.setMinTemperature(resultSet.getFloat(COLUMN_IDX_MIN_TEMP));
      temperature.setMaxTemperature(resultSet.getFloat(COLUMN_IDX_MAX_TEMP));
      entities.add(temperature);
    }
  }

  @Override
  public int save(Connection con, Temperature entity) throws PersistenceException {
    return 0;
  }

  @Override
  public int delete(Connection con, Long id) throws PersistenceException {
    return 0;
  }

  @Override
  public Temperature findById(Connection con, Long key) throws PersistenceException {
    return null;
  }

  @Override
  public List<Temperature> listAll(Connection con) throws SQLException {
    entities.clear();

    // create SQL query to fetch all player records
    final String sqlSelectQuery = "SELECT * FROM TEMPERATURE ORDER BY TEMP_DATE ASC";

    // Step 2.B: Creating JDBC Statement
    PreparedStatement preparedStatement = con.prepareStatement(sqlSelectQuery);

    // Step 2.C: Executing SQL & retrieve data into ResultSet
    ResultSet resultSet = preparedStatement.executeQuery();

    // processing returned data and printing into console
    setTemperatureObj(resultSet);
    return entities;
  }
}
