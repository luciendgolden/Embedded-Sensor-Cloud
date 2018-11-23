package main.java.request;

import BIF.SWE1.interfaces.Request;
import BIF.SWE1.interfaces.Url;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import main.java.foundation.EnumUtil;
import main.java.url.UrlImpl;

public class RequestImpl implements Request {

  private final static Logger logger = Logger.getLogger("requests");

  private BufferedReader in;
  private String[] parameters;

  /**
   * https://www.tutorialspoint.com/http/http_requests.htm
   *
   * @param in - Get an Inputstream with a standardized http header
   * GET / HTTP/1.1
   * Host: localhost
   * Connection: keep-alive
   * Accept: text/html,application/xhtml+xml
   * User-Agent: Unit-Test-Agent/1.0 (The OS)
   * Accept-Encoding: gzip,deflate,sdch
   * Accept-Language: de-AT,de;q=0.8,en-US;q=0.6,en;q=0.4
   */
  public RequestImpl(InputStream in) {
    /* TODO - FactoryPattern
       message-body

       i.e.
       application/x-www-form-urlencoded
       licenseID=string&content=string&/paramsXML=string

       text/xml
       <?xml version="1.0" encoding="utf-8"?>
       <string xmlns="http://clearforest.com/">string</string>
     */
    this.in = new BufferedReader(new InputStreamReader(in));
    try {
      setParameters();
    } catch (IOException e) {
      logger.log(Level.FINE, "Unexpected error " + e.getMessage(), e);
    }
  }

  private void setParameters() throws IOException {
    final String requestLine = in.readLine();
    this.parameters = requestLine.split("[ ]");
  }

  public void readMessage() throws IOException {
    if (in != null) {
      String raw;

      while ((raw = in.readLine()) != null) {
        System.out.println(raw);
      }
    }
  }

  @Override
  public boolean isValid() {
    if(parameters.length != 3)
      return false;

    return EnumUtil.contains(main.java.request.HttpMethods.class, parameters[0].toUpperCase())
        && parameters[1].startsWith("/");
  }

  @Override
  public String getMethod() {
    if(this.isValid())
      return parameters[0];

    return null;
  }

  @Override
  public Url getUrl() {
    if(this.isValid())
      return new UrlImpl(parameters[1]);

    return null;
  }

  @Override
  public Map<String, String> getHeaders() {
    return null;
  }

  @Override
  public int getHeaderCount() {
    return 0;
  }

  @Override
  public String getUserAgent() {
    return null;
  }

  @Override
  public int getContentLength() {
    return 0;
  }

  @Override
  public String getContentType() {
    return null;
  }

  @Override
  public InputStream getContentStream() {
    return null;
  }

  @Override
  public String getContentString() {
    return null;
  }

  @Override
  public byte[] getContentBytes() {
    return new byte[0];
  }
}
