package at.technikum.swe.response;

import BIF.SWE1.interfaces.Response;
import at.technikum.swe.common.Status;
import at.technikum.swe.foundation.Ensurer;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

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

  private final static Logger logger = Logger.getLogger(ResponseImpl.class.getName());

  /**
   * A Status-line
   *
   * Zero or more header (General|Response|Entity) fields followed by CRLF
   *
   * An empty line (i.e., a line with nothing preceding the CRLF)
   * indicating the end of the header fields
   *
   * Optionally a message-body
   */
  private Status status;
  private Map<String, String> headers = new HashMap<>();
  private String content;

  private static final String BLANK_SPACE = " ";
  private static final String LINE_BREAK = System.getProperty("line.separator");

  private final static String DEFAULT_SERVER_HEADER = "BIF-SWE1-Server";
  private final static String HTTP_DEFAULT_VERSION = "HTTP/1.1";

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
    headers.put("Server", DEFAULT_SERVER_HEADER);
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
    } catch (UnsupportedEncodingException e) {
      logger.log(Level.SEVERE, "Unexpected error " + e.getMessage(), e);
    }

    return responseBytes.length;
  }

  @Override
  public String getContentType() {
    return headers.get("Content-Type");
  }

  @Override
  public void setContentType(String s) {
    Ensurer.ensureNotNull(s, "contentType");
    Ensurer.ensureNotBlank(s, "contentType");
    headers.put("Content-Type", s);
  }

  @Override
  public void setStatusCode(int i) {
    status = Status.compareStatusCode(i);
    Ensurer.ensureNotNull(status, "status");
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
    return headers.get("Server");

  }

  @Override
  public void setServerHeader(String s) {
    Ensurer.ensureNotNull(s, "server header");
    headers.put("Server", s);
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
    final BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
    StringBuffer buffer = new StringBuffer();
    String line;
    try {
      while (null != (line = br.readLine())) {
        buffer.append(line);
      }

      this.content = buffer.toString();
    }catch (IOException e){
      logger.log(Level.SEVERE, "Unexpected error " + e.getMessage(), e);
    }
  }

  @Override
  public void send(OutputStream outputStream) {
    Ensurer.ensureNotNull(outputStream, "outputstream");
    Ensurer.ensureNotNull(status, "status");
    Ensurer.ensureNotNull(headers, "headers");
    try {
      final StringBuilder finalRes = new StringBuilder();

      finalRes.append(buildMessageStatusLine());
      finalRes.append(buildHeader());
      finalRes.append(LINE_BREAK);
      finalRes.append(buildBody());

      outputStream.write(finalRes.toString().getBytes(Charset.forName("UTF-8")));
    } catch (IOException e) {
      logger.log(Level.SEVERE, "Unexpected error " + e.getMessage(), e);
    }
  }

  private String buildBody(){
    final StringBuilder builder = new StringBuilder();

    if(content != null) {
      builder.append(content);
    }else if(content == null && headers.containsKey("Content-Type")){
      throw new IllegalArgumentException("Content-Type is set without content");
    }

    return builder.toString();
  }

  private String buildMessageStatusLine() {
    final StringBuilder builder = new StringBuilder(HTTP_DEFAULT_VERSION);

    builder.append(BLANK_SPACE);
    builder.append(status.getStatusCode()).append(BLANK_SPACE);
    builder.append(status.getDescription()).append(LINE_BREAK);

    return builder.toString();
  }

  private String buildHeader(){
    StringBuilder builder = new StringBuilder();

    String myHeaders = headers.entrySet().stream()
        .map(entry -> entry.getKey() + ": " + entry.getValue())
        .collect(Collectors.joining(LINE_BREAK));

    builder.append(myHeaders);
    builder.append(LINE_BREAK);

    return builder.toString();
  }
}