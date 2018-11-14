package main.java.response;

import BIF.SWE1.interfaces.Response;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import main.java.common.Status;
import main.java.foundation.Ensurer;

/**
 * Examples of Response Message HTTP/1.1 200 OK Date: Mon, 27 Jul 2009 12:28:53 GMT ClientHandler:
 * Apache/2.2.14 (Win32) Last-Modified: Wed, 22 Jul 2009 19:15:56 GMT Content-Length: 88
 * Content-Type: text/html Connection: Closed
 *
 * <html>
 * <body>
 * <h1>Hello, World!</h1>
 * </body>
 * </html>
 */
public class ResponseImpl implements Response {

  private final static Logger logger = Logger.getLogger(ResponseImpl.class.getName());

  private Map<String, String> headers = new HashMap<>();
  private String contentType;
  private int contentLength;
  private Status status;
  private String serverHeader;
  private String content;

  private final static String DEFAULT_SERVER_HEADER = "BIF-SWE1-Server";

  /**
   * https://www.tutorialspoint.com/http/http_responses.htm
   *
   * Example of Response Message
   *
   * HTTP/1.1 200 OK Date: Mon, 27 Jul 2009 12:28:53 GMT Server: Apache/2.2.14 (Win32)
   * Last-Modified: Wed, 22 Jul 2009 19:15:56 GMT Content-Length: 88 Content-Type: text/html
   * Connection: Closed
   * <html>
   * <body>
   * <h1>Hello, World!</h1>
   * </body>
   * </html>
   */
  public ResponseImpl() {
    this.serverHeader = DEFAULT_SERVER_HEADER;
  }

  @Override
  public Map<String, String> getHeaders() {
    return headers;
  }

  @Override
  public int getContentLength() {
    byte[] responseBytes = new byte[0];
    try {
      responseBytes = content.getBytes("UTF-8");
    }
    catch ( UnsupportedEncodingException e ) {
      logger.log(Level.SEVERE, "Unexpected error " + e.getMessage(), e);
    }

    return responseBytes.length;
  }

  @Override
  public String getContentType() {
    return contentType;
  }

  @Override
  public void setContentType(String s) {
    Ensurer.ensureNotNull(s, "contentType");
    Ensurer.ensureNotBlank(s, "contentType");
    contentType = s;
  }

  @Override
  public void setStatusCode(int i) {
    Ensurer.ensureNotNull(i, "statuscode");
    status = Status.compareStatusCode(i);
  }

  @Override
  public int getStatusCode() {
    if (status != null) {
      return status.getStatusCode();
    }

    throw new IllegalArgumentException("statuscode is null");
  }

  @Override
  public String getStatus() {
    if (status != null) {
      return new StringBuilder()
          .append(status.getStatusCode())
          .append(status.getDescription())
          .toString();
    }

    throw new IllegalArgumentException("status is null");
  }

  @Override
  public void addHeader(String s, String s1) {
    Ensurer.ensureNotNull("headers", s, s1);
    this.headers.put(s, s1);
  }

  @Override
  public String getServerHeader() {
    return serverHeader;

  }

  @Override
  public void setServerHeader(String s) {
    Ensurer.ensureNotNull(s, "server header");
    this.serverHeader = s;
  }

  @Override
  public void setContent(String s) {
    Ensurer.ensureNotNull(s, "content");
    this.content = s;
  }

  @Override
  public void setContent(byte[] bytes) {
    content = new String(bytes);
  }

  @Override
  public void setContent(InputStream inputStream) {

  }

  @Override
  public void send(OutputStream outputStream) {

  }
}
