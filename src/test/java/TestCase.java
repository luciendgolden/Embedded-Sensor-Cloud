import static at.technikum.swe.foundation.SystemUtil.LINE_SEPERATOR;

import static java.util.Optional.of;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import BIF.SWE1.interfaces.Plugin;
import BIF.SWE1.interfaces.PluginManager;
import at.technikum.swe.DAO.MySQLAccess;
import at.technikum.swe.DAO.TemperatureDAO;
import at.technikum.swe.common.Configuration;
import at.technikum.swe.domain.Temperature;
import at.technikum.swe.foundation.Ensurer;
import at.technikum.swe.foundation.SystemUtil;
import at.technikum.swe.foundation.TimeUtil;
import at.technikum.swe.parser.CSVParser;
import at.technikum.swe.plugin.PluginManagerImpl;
import at.technikum.swe.plugin.elements.TemperaturPlugin;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicLong;
import javax.xml.parsers.ParserConfigurationException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class TestCase {

  @BeforeClass
  public static void initialize() throws SQLException, ClassNotFoundException {
    MySQLAccess mySQLAccess = MySQLAccess.newInstance();
    mySQLAccess.initializeConnection();
  }

  @AfterClass
  public static void closeConnection() throws SQLException {
    MySQLAccess mySQLAccess = MySQLAccess.newInstance();
    mySQLAccess.closeAllConnections();
  }

  @Test
  public void test004() {
    File file = new File(
        "/Users/alexander/git/java/EmbeddedSensorCloud/SWE1-Java/deploy/tmp-static-files/theme.html");

    boolean exists = file.exists();      // Check if the file exists
    boolean isDirectory = file.isDirectory(); // Check if it's a directory
    boolean isFile = file.isFile();      // Check if it's a regular file

    assertEquals(false, exists);
    assertEquals(false, isDirectory);
    assertEquals(false, isFile);
  }

  @Test
  public void test005() {
    ClassLoader classLoader = getClass().getClassLoader();
    URL url = classLoader.getResource("WEB-INF/index.html");

    final StringBuilder path = new StringBuilder();

    ///Users/alexander/git/java/EmbeddedSensorCloud/SWE1-Java/
    path.append("file:" + SystemUtil.USER_DIR);
    path.append(String.format("%sbin%sWEB-INF%sindex.html", SystemUtil.FILE_SEPERATOR,
        SystemUtil.FILE_SEPERATOR,
        SystemUtil.FILE_SEPERATOR));

    assertEquals(path.toString(), url.toString());
  }

  @Test
  public void test006() throws IOException {
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

    assertEquals("helloworld", toLowerMessage);
  }

  @Test
  public void test007() {
    final AtomicLong counter = new AtomicLong(1000);
    assertEquals("1000", counter.toString());
    counter.incrementAndGet();
    assertEquals("1001", counter.toString());
  }

  @Test
  public void test008() throws IOException {
    Properties properties = new Properties();

    try (InputStreamReader reader = new InputStreamReader(Configuration.class.getClassLoader()
        .getResourceAsStream("configuration.properties"))) {
      properties.load(reader);

      assertEquals("2005-2009_weather-data-spain.csv", properties.getProperty("csv.weatherfile1"));
    }
  }

  @Test
  public void test009() throws Exception {
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

      assertNotNull(pstmt);

    } catch (SQLException e) {
      System.out.println("Connection Failed! Check output console");
      e.printStackTrace();
      return;
    } finally {
      connection.close();

    }
  }

  @Test
  public void test010() throws Exception {
    int returnValue = -1;

    MySQLAccess mySQLAccess = MySQLAccess.newInstance();

    Connection con = mySQLAccess.getConnect();

    TemperatureDAO temperatureDAO = new TemperatureDAO();
    returnValue = temperatureDAO.createTable(con);
    mySQLAccess.returnConnection(con);

    assertEquals(0, returnValue);
  }

  @Test
  public void test011() {
    MySQLAccess mySQLAccess = null;
    Connection con = null;

    try {
      //given
      final TemperatureDAO temperatureDAO = new TemperatureDAO();
      final Temperature temperature = new Temperature();
      final LocalDate localDate = LocalDate.of(2019, 10, 12);
      temperature.setDate(localDate);
      temperature.setMinTemperature(-11.0f);
      temperature.setMaxTemperature(70f);

      mySQLAccess = MySQLAccess.newInstance();
      con = mySQLAccess.getConnect();

      //WHEN
      int worked = temperatureDAO.insert(con, temperature);

      //then
      assertEquals(1, worked);

    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      mySQLAccess.returnConnection(con);
    }
  }

  @Test
  public void test012() {
    Map<Long, String[]> map = null;
    CSVParser parcival = new CSVParser();
    map = parcival.loadCSVAdvanced();

    assertNotNull(map);
  }

  @Test
  public void test013() throws SQLException {
    TemperatureService service = new TemperatureService();
    service.initialDBLoad();

    assertNotNull(service);
  }

  @Test
  public void test014() throws SQLException {

    MySQLAccess mySQLAccess = MySQLAccess.newInstance();
    Connection con = mySQLAccess.getConnect();
    LocalDate myDate = LocalDate.of(2005, 10, 3);
    List<Temperature> temperatureList = null;

    TemperatureDAO temperatureDAO = new TemperatureDAO();

    temperatureList = temperatureDAO.findByDate(con, java.sql.Date.valueOf(myDate));

    assertNotNull(temperatureList);
    assertEquals(7,temperatureList.size());
  }

  @Test
  public void test015() {
    int value = 38838;
    LocalDate now = null;

    if (value != 0) {
      now = LocalDate.now();
    }
    assertEquals("2019-01-17",now.toString());
  }

  @Test
  public void test016() throws SQLException {
    MySQLAccess mySQLAccess = MySQLAccess.newInstance();
    Connection con = mySQLAccess.getConnect();

    TemperatureDAO temperatureDAO = new TemperatureDAO();
    List<Temperature> temperatureList = temperatureDAO.listAll(con);

    assertNotNull(temperatureList);
  }

  @Test
  public void test017() {
    boolean isValid = TimeUtil.checkDate("2018", "08", "ab");

    assertEquals(false,isValid);
  }

  @Test
  public void test018() throws InterruptedException {
    TemperatureService service = new TemperatureService();
    service.initialDBLoad();
    SensorReader sensy = new SensorReader();

    assertNotNull(sensy);
    sensy.start();

    assertNotNull(sensy);

  }

  @Test
  public void test003() throws SQLException {
    MySQLAccess mySQLAccess = MySQLAccess.newInstance();
    Connection con = mySQLAccess.getConnect();

    TemperatureDAO temperatureDAO = new TemperatureDAO();

    String firstDate = "2009-01-13";
    String secondDate = "2019-01-14";

    String[] firstDateArr = firstDate.split("-");
    String[] secondDateArr = secondDate.split("-");

    LocalDate firstLocalDate = getValidLocalDate(firstDateArr[0], firstDateArr[1], firstDateArr[2]);
    LocalDate secondLocalDate = getValidLocalDate(secondDateArr[0], secondDateArr[1],
        secondDateArr[2]);

    List<Temperature> list = temperatureDAO
        .findDateBetween(con, java.sql.Date.valueOf(firstLocalDate),
            java.sql.Date.valueOf(secondLocalDate), of(1), of(50));

    assertEquals(50, list.size());
  }

  @Test
  public void test002() throws SQLException {
    MySQLAccess mySQLAccess = MySQLAccess.newInstance();
    Connection con = mySQLAccess.getConnect();

    TemperatureDAO temperatureDAO = new TemperatureDAO();

    String firstDate = "2009-01-13";
    String secondDate = "2019-01-14";

    String[] firstDateArr = firstDate.split("-");
    String[] secondDateArr = secondDate.split("-");

    LocalDate firstLocalDate = getValidLocalDate(firstDateArr[0], firstDateArr[1], firstDateArr[2]);
    LocalDate secondLocalDate = getValidLocalDate(secondDateArr[0], secondDateArr[1],
        secondDateArr[2]);

    Integer counter = temperatureDAO.countDateBetween(con, java.sql.Date.valueOf(firstLocalDate),
        java.sql.Date.valueOf(secondLocalDate));

    assertEquals("2434", String.valueOf(counter));
  }

  private LocalDate getValidLocalDate(String year, String month, String day) {
    LocalDate datetofind;
    if (TimeUtil.checkDate(year, month, day)) {
      datetofind = LocalDate
          .of(Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(day));
    } else {
      datetofind = LocalDate
          .of(9999, 12, 12);
    }
    return datetofind;
  }

  @Test
  public void test019(){
    final PluginManager pluginManager = new PluginManagerImpl();
    List<Plugin> list = (List<Plugin>) pluginManager.getPlugins();

    assertNotNull(list);
    assertEquals(4, list.size());
  }

  @Test
  public void test020(){
    Temperature temperature = new Temperature();
    temperature.setId(1l);
    temperature.setDate(LocalDate.of(2018,01,01));
    temperature.setMinTemperature(18.4f);
    temperature.setMaxTemperature(19.4f);

    Object object = Ensurer.ensureNotNull(temperature);

    assertNotNull(object);
  }

  @Test
  public void test001() {
    //given
    final TemperaturPlugin temperaturPlugin = new TemperaturPlugin();
    final List<Temperature> temperatureList = new ArrayList<>();

    Document document = null;

    final Temperature temperature = new Temperature();
    temperature.setId(1l);
    temperature.setDate(LocalDate.of(2018, 01, 16));
    temperature.setMinTemperature(16.4f);
    temperature.setMaxTemperature(20.4f);

    temperatureList.add(temperature);
    //when
    try {
      document = temperaturPlugin.buildXMLDocument(temperatureList, of(1));
    } catch (ParserConfigurationException e) {
      //throw exception
    }

    NodeList nList = document.getElementsByTagName("temperature-entry");
    Node nNode = nList.item(0);
    Element eElement = (Element) nNode;

    assertEquals("temperature", document.getDocumentElement().getNodeName());
    assertEquals("1", document.getDocumentElement().getAttribute("dataset"));
    assertEquals(1, nList.getLength());

    assertEquals("temperature-entry", nNode.getNodeName());

    assertEquals(Node.ELEMENT_NODE, nNode.getNodeType());

    assertEquals("1", eElement.getAttribute("id"));
    assertEquals("2018-01-16", eElement.getElementsByTagName("date").item(0).getTextContent());
    assertEquals("16.4", eElement
        .getElementsByTagName("min_temp")
        .item(0)
        .getTextContent());
    assertEquals("20.4", eElement
        .getElementsByTagName("max_temp")
        .item(0)
        .getTextContent());
  }
}
