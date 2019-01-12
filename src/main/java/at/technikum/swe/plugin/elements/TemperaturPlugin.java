package at.technikum.swe.plugin.elements;

import static at.technikum.swe.common.Status.NOT_FOUND;
import static at.technikum.swe.common.Status.OK;
import static java.util.Optional.empty;
import static java.util.Optional.of;
import static java.util.Optional.ofNullable;

import BIF.SWE1.interfaces.Plugin;
import BIF.SWE1.interfaces.PluginManager;
import BIF.SWE1.interfaces.Request;
import BIF.SWE1.interfaces.Response;
import at.technikum.swe.DAL.MySQLAccess;
import at.technikum.swe.DAL.TemperatureDAL;
import at.technikum.swe.DAO.Temperature;
import at.technikum.swe.common.ContentType;
import at.technikum.swe.foundation.PluginUtil;
import at.technikum.swe.foundation.TimeUtil;
import at.technikum.swe.response.ResponseImpl;
import at.technikum.swe.url.UrlImpl;
import at.technikum.swe.xml.XMLSerealizer;
import java.io.InputStream;
import java.sql.Connection;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class TemperaturPlugin implements Plugin {

  private final static Logger logger = Logger.getLogger(TemperaturPlugin.class.getName());

  private final static MySQLAccess mySQLAccess = MySQLAccess.newInstance();

  private Connection connection = null;

  private final TemperatureDAL temperatureDAL = new TemperatureDAL();

  private List<Temperature> resultTemp = null;

  private Optional<Integer> rowCount;

  private final XMLSerealizer xmlSerealizer = new XMLSerealizer();

  @Override
  public float canHandle(Request request) {
    float handleable = PluginUtil.getTheProbability(ToLowerPlugin.class, request);
    final String[] segments = request.getUrl().getSegments();

    for (String s : segments) {
      if (s.equals("GetTemperature") || s.equals("temperature")) {
        handleable += 0.8f;
      }

      if (handleable > 1f) {
        return 1f;
      }

      return handleable;
    }

    return handleable;
  }

  @Override
  public Response handle(Request request) {
    try {
      connection = mySQLAccess.getConnect();

      /*
       * Eine REST Abfrage http://localhost:8080/GetTemperature/2012/09/20 soll alle Temeraturdaten
       * des angegebenen Tages als XML zurück geben. Das XML Format ist frei wählbar.
       */
      final ResponseImpl res = new ResponseImpl();
      final String[] segments = request.getUrl().getSegments();

      final UrlImpl urlimpl = (UrlImpl)request.getUrl();
      boolean isRESTRequestXML = false;


      if(segments.length>3) {
        isRESTRequestXML = (segments[segments.length - 4]).equals("GetTemperature");
      }

      boolean isGETRequestXML =  (segments[segments.length - 1]).equals("temperature");

      if (isRESTRequestXML) {
        //send XML for the year
        String year = segments[segments.length - 3];
        String month = segments[segments.length - 2];
        String day = segments[segments.length - 1];
        LocalDate datetofind = getValidLocalDate(year, month, day);

        resultTemp = temperatureDAL.findByDate(connection, java.sql.Date.valueOf(datetofind));

      } else if(isGETRequestXML){
        String firstDate = urlimpl.getParameter().get("firstDate");
        String secondDate = urlimpl.getParameter().get("secondDate");
        Optional<Integer> page = ofNullable(Integer.valueOf(urlimpl.getParameter().get("page")));
        Optional<Integer> limit = ofNullable(Integer.valueOf(urlimpl.getParameter().get("limit")));

        String[] firstDateArr = firstDate.split("-");
        String[] secondDateArr = secondDate.split("-");

        LocalDate firstLocalDate = getValidLocalDate(firstDateArr[0], firstDateArr[1], firstDateArr[2]);
        LocalDate secondLocalDate = getValidLocalDate(secondDateArr[0], secondDateArr[1], secondDateArr[2]);

        rowCount = ofNullable(temperatureDAL.countDateBetween(connection, java.sql.Date.valueOf(firstLocalDate),
            java.sql.Date.valueOf(secondLocalDate)));

        resultTemp = temperatureDAL.findDateBetween(connection, java.sql.Date.valueOf(firstLocalDate),
            java.sql.Date.valueOf(secondLocalDate), page, limit);
      }

      Document document = buildXMLDocument();
      InputStream in = xmlSerealizer.toXML(document);

      res.setContentType(ContentType.APPLICATION_XML);
      res.setStatusCode(OK);
      res.setContent(in);

      return res;

    } catch (Exception e) {
      logger.log(Level.SEVERE, "Unexpected error " + e.getMessage(), e);
    } finally {
      mySQLAccess.returnConnection(connection);
    }

    return null;
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

  private Document buildXMLDocument() throws ParserConfigurationException {
    DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();

    DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();

    Document document = documentBuilder.newDocument();

    // root element
    Element root = document.createElement("temperature");
    document.appendChild(root);

    // set an attribute to temp element
    Attr dataset = document.createAttribute("dataset");
    dataset.setValue(String.valueOf(rowCount.orElse(0)));
    root.setAttributeNode(dataset);

    if (resultTemp != null) {
      for (Temperature tempElement : resultTemp) {
        // temp element
        Element temp = document.createElement("teperature-entry");

        root.appendChild(temp);

        // set an attribute to temp element
        Attr attr = document.createAttribute("id");
        attr.setValue(String.valueOf(tempElement.getId()));
        temp.setAttributeNode(attr);

        //you can also use staff.setAttribute("id", "1") for this

        // date element
        Element date = document.createElement("date");
        date.appendChild(document.createTextNode(String.valueOf(tempElement.getDate())));
        temp.appendChild(date);

        // min_temp element
        Element min_temp = document.createElement("min_temp");
        min_temp
            .appendChild(
                document.createTextNode(String.valueOf(tempElement.getMinTemperature())));
        temp.appendChild(min_temp);

        // max_temp element
        Element max_temp = document.createElement("max_temp");
        max_temp
            .appendChild(
                document.createTextNode(String.valueOf(tempElement.getMaxTemperature())));
        temp.appendChild(max_temp);
      }
      return document;
    }
    return null;
  }

  @Override
  public String toString() {
    return "TemperaturPlugin{}";
  }
}
