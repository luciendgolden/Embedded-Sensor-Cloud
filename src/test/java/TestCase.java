import static at.technikum.swe.foundation.SystemUtil.LINE_SEPERATOR;

import static java.util.Optional.empty;
import static java.util.Optional.of;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import at.technikum.swe.DAL.MySQLAccess;
import at.technikum.swe.DAL.TemperatureDAL;
import at.technikum.swe.common.Configuration;
import at.technikum.swe.DAO.Temperature;
import at.technikum.swe.foundation.SystemUtil;
import at.technikum.swe.foundation.TimeUtil;
import at.technikum.swe.parser.CSVParser;
import at.technikum.swe.sensor.SensorReader;
import at.technikum.swe.service.TemperatureService;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.Test;

public class TestCase {

  @Test
  public void myTest() {
    File file = new File(
        "/Users/alexander/git/java/EmbeddedSensorCloud/SWE1-Java/deploy/tmp-static-files/theme.html");

    boolean exists = file.exists();      // Check if the file exists
    boolean isDirectory = file.isDirectory(); // Check if it's a directory
    boolean isFile = file.isFile();      // Check if it's a regular file

    System.out.println("Exists: " + exists);
    System.out.println("IsDirectory: " + isDirectory);
    System.out.println("IsFile"
        + ": " + isFile);
  }

  @Test
  public void webAppTest() {
    ClassLoader classLoader = getClass().getClassLoader();
    InputStream in = classLoader.getResourceAsStream("WEB-INF/index.html");
    URL url = classLoader.getResource("WEB-INF/");

    final StringBuilder path = new StringBuilder();

    path.append(SystemUtil.USER_DIR);
    path.append(String.format("%ssrc%smain%swebapp%sWEB-INF%s404.html", SystemUtil.FILE_SEPERATOR,
        SystemUtil.FILE_SEPERATOR, SystemUtil.FILE_SEPERATOR, SystemUtil.FILE_SEPERATOR,
        SystemUtil.FILE_SEPERATOR));

    System.out.println(url.toString());
  }

  @Test
  public void testBody() throws IOException {
    String body = null;

    ByteArrayOutputStream ms = new ByteArrayOutputStream();
    PrintWriter sw = new PrintWriter(new OutputStreamWriter(ms,
        StandardCharsets.US_ASCII));
    sw.printf("{\n"
        + "    \"value\":\"helloWorld\"\n"
        + "}");
    sw.flush();
    InputStream input = new ByteArrayInputStream(ms.toByteArray());
    BufferedReader in = new BufferedReader(new InputStreamReader(input));

    if (in != null) {
      String line = null;
      StringBuilder finalstring = new StringBuilder();

      while ((line = in.readLine()) != null) {
        finalstring.append(line);
        finalstring.append(LINE_SEPERATOR);
      }

      body = finalstring.toString();
    }

    String[] jsonParts = body.split("[\\n]");
    String[] keyValue = jsonParts[1].split("[:]");
    String value = keyValue[1];
    String message = value.substring(1, value.length() - 1);
    String toLowerMessage = message.toLowerCase();

    assertEquals(toLowerMessage, "helloworld");
  }

  @Test
  public void createPrimaryKey() {
    final AtomicLong counter = new AtomicLong(1000);
    System.out.println(counter);
    counter.incrementAndGet();
    System.out.println(counter);
  }

  @Test
  public void initialize() throws IOException {
    Properties properties = new Properties();

    try (InputStreamReader reader = new InputStreamReader(Configuration.class.getClassLoader()
        .getResourceAsStream("configuration.properties"))) {
      properties.load(reader);

      System.out.println(properties.getProperty("csv.weatherfile1"));
    }
  }

  @Test
  public void setupDatabaseCon() throws Exception {
    Configuration configuration = new Configuration().initialize();
    Connection connection = null;
    String username = configuration.getProperty("mysql.username");
    String password = configuration.getProperty("mysql.password");
    String db = configuration.getProperty("mysql.db");

    Class.forName("com.mysql.cj.jdbc.Driver");

    try {
      connection = DriverManager.getConnection(
          String.format("jdbc:mysql://localhost/%s", db), username, password);
      connection.setAutoCommit(false);

      PreparedStatement pstmt = connection
          .prepareStatement("CREATE TABLE IF NOT EXISTS photos\n"
              + "(\n"
              + "  id INT NOT NULL PRIMARY KEY,\n"
              + "  versionNumber INT NOT NULL,\n"
              + "  name VARCHAR(256) NOT NULL\n"
              + ")");
      pstmt.executeUpdate();

    } catch (SQLException e) {
      System.out.println("Connection Failed! Check output console");
      e.printStackTrace();
      return;
    } finally {
      connection.close();

    }
  }

  @Test
  public void testDropAndCreateTable() throws Exception {
    MySQLAccess mySQLAccess = MySQLAccess.newInstance();
    mySQLAccess.initializeConnection();

    Connection con = mySQLAccess.getConnect();

    TemperatureDAL temperatureDAL = new TemperatureDAL();
    temperatureDAL.createTable(con);
    mySQLAccess.returnConnection(con);
    con.close();
  }

  @Test
  public void insertRowIntoDB(){
    MySQLAccess mySQLAccess = null;
    Connection con = null;

    try {
      //given
      final TemperatureDAL temperatureDAL = new TemperatureDAL();
      final Temperature temperature = new Temperature();
      final LocalDate localDate = LocalDate.of(2019,10,12);
      temperature.setDate(localDate);
      temperature.setMinTemperature(-11.0f);
      temperature.setMaxTemperature(70f);


      mySQLAccess = MySQLAccess.newInstance();
      con = mySQLAccess.getConnect();

      //when
      mySQLAccess.initializeConnection();

      int worked = temperatureDAL.insert(con,temperature);

      //then
      assertEquals(1,worked);

    } catch (SQLException | ClassNotFoundException e) {
      e.printStackTrace();
    }finally {
      mySQLAccess.returnConnection(con);
      try {
        con.close();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
  }

  @Test
  public void readCSV(){
    CSVParser parcival = new CSVParser();
      parcival.loadCSVAdvanced();
  }

  @Test
  public void initialDBLoad() throws SQLException {
    TemperatureService service = new TemperatureService();

    service.initialDBLoad();
  }

  @Test
  public void listAllEntries() throws SQLException {
    MySQLAccess mySQLAccess = MySQLAccess.newInstance();
    Connection con = mySQLAccess.getConnect();
    LocalDate myDate = LocalDate.of(2005,10,3);

    TemperatureDAL temperatureDAL = new TemperatureDAL();
    temperatureDAL.findByDate(con, java.sql.Date.valueOf(myDate));

  }

  @Test
  public void test(){
    int value = 38838;
    if(value!=0){
      LocalDate now = LocalDate.now();
      System.out.println(now);
      System.out.println("hello");
    }
  }

  @Test
  public void testForFindAll() throws SQLException {
    MySQLAccess mySQLAccess = MySQLAccess.newInstance();
    Connection con = mySQLAccess.getConnect();

    TemperatureDAL temperatureDAL = new TemperatureDAL();
    List<Temperature> temperatureList = temperatureDAL.listAll(con);

    temperatureList.forEach(System.out::println);
  }

  @Test
  public void testDate(){
    boolean isValid = TimeUtil.checkDate("2018", "08", "ab");

    System.out.println(isValid);
  }

  @Test
  public void TestRandomDataReader() throws InterruptedException {
    TemperatureService service = new TemperatureService();
    service.initialDBLoad();
    SensorReader sensy = new SensorReader();

      sensy.start();
      sensy.join();
  }

  @Test
  public void findDateBetween() throws SQLException {
    MySQLAccess mySQLAccess = MySQLAccess.newInstance();
    Connection con = mySQLAccess.getConnect();

    TemperatureDAL temperatureDAL = new TemperatureDAL();

    String firstDate = "2009-01-13";
    String secondDate = "2019-01-14";

    String[] firstDateArr = firstDate.split("-");
    String[] secondDateArr = secondDate.split("-");

    LocalDate firstLocalDate = getValidLocalDate(firstDateArr[0], firstDateArr[1], firstDateArr[2]);
    LocalDate secondLocalDate = getValidLocalDate(secondDateArr[0], secondDateArr[1], secondDateArr[2]);

    List<Temperature> list = temperatureDAL.findDateBetween(con, java.sql.Date.valueOf(firstLocalDate),
        java.sql.Date.valueOf(secondLocalDate), of(1), of(50));

    list.forEach(System.out::println);
  }

  @Test
  public void countDateBetween() throws SQLException {
    MySQLAccess mySQLAccess = MySQLAccess.newInstance();
    Connection con = mySQLAccess.getConnect();

    TemperatureDAL temperatureDAL = new TemperatureDAL();

    String firstDate = "2009-01-13";
    String secondDate = "2019-01-14";

    String[] firstDateArr = firstDate.split("-");
    String[] secondDateArr = secondDate.split("-");

    LocalDate firstLocalDate = getValidLocalDate(firstDateArr[0], firstDateArr[1], firstDateArr[2]);
    LocalDate secondLocalDate = getValidLocalDate(secondDateArr[0], secondDateArr[1], secondDateArr[2]);

    Integer counter = temperatureDAL.countDateBetween(con, java.sql.Date.valueOf(firstLocalDate),
        java.sql.Date.valueOf(secondLocalDate));

    System.out.println(counter);
  }

  private LocalDate getValidLocalDate(String year, String month, String day) {
    LocalDate datetofind;
    if (TimeUtil.checkDate(year, month, day)) {
      datetofind = LocalDate
          .of(Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(day));
    }
    else {
      datetofind = LocalDate
          .of(9999, 12, 12);
    }
    return datetofind;
  }
}
