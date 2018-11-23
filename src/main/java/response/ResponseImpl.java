package main.java.response;

import BIF.SWE1.interfaces.Response;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;
import main.java.foundation.Ensurer;

/**
 * Examples of Response Message
 * HTTP/1.1 200 OK
 * Date: Mon, 27 Jul 2009 12:28:53 GMT
 * Server: Apache/2.2.14 (Win32)
 * Last-Modified: Wed, 22 Jul 2009 19:15:56 GMT
 * Content-Length: 88
 * Content-Type: text/html
 * Connection: Closed
 * 
 * <html>
 * <body>
 * <h1>Hello, World!</h1>
 * </body>
 * </html>
 */
public class ResponseImpl implements Response {

  private final static Logger logger = Logger.getLogger("response");

  private Map<String, String> headers = new HashMap<>();
  private String contentType;
  private int contentLength;
  private Status status;
  private String serverHeader;
  private String content;

  public ResponseImpl() {
  }

  @Override
  public Map<String, String> getHeaders() {
    return headers;
  }

  @Override
  public int getContentLength() {
    return contentLength;
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
    if(status != null)
      return status.getStatusCode();

    throw new IllegalArgumentException("statuscode is null");
  }

  @Override
  public String getStatus() {
    if(status != null)
      return new StringBuilder()
          .append(status.getStatusCode())
          .append(status.getDescription())
          .toString();

    throw new IllegalArgumentException("status is null");
  }

  @Override
  public void addHeader(String s, String s1) {
    Ensurer.ensureNotNull("headers", s, s1);
    headers.put(s, s1);
  }

  @Override
  public String getServerHeader() {
    return serverHeader;
  }

  @Override
  public void setServerHeader(String s) {
    Ensurer.ensureNotNull(s, "server header");
    serverHeader = s;
  }

  @Override
  public void setContent(String s) {
    Ensurer.ensureNotNull(s, "content");
    content = s;
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
