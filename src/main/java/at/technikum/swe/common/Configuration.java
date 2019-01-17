package at.technikum.swe.common;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

public class Configuration {

  private static final String PROPERTY_ENVIRONMENT = "configuration.properties";

  private Properties properties;

  /**
   * Loads the properties at all into java environment
   * @return
   * @throws IOException
   */
  public Configuration initialize() throws IOException {
    properties = new Properties();

    try (InputStreamReader reader = new InputStreamReader(Configuration.class.getClassLoader()
        .getResourceAsStream(PROPERTY_ENVIRONMENT))) {
      properties.load(reader);
    }
    return this;
  }

  /**
   * get configuration property
   *
   * @param p property name
   * @return property value, if any, null else
   */
  public String getProperty(String p) {
    return properties.getProperty(p);
  }

}
