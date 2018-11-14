package main.java.request;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HttpRequestParser {

  private final static Logger logger = Logger.getLogger(HttpRequestParser.class.getName());

  private String requestLine;
  private Map<String, String> requestHeaders;
  private StringBuffer messageBody;

  public HttpRequestParser() {
    this.requestHeaders = new HashMap<>();
    this.messageBody = new StringBuffer();
  }

  /**
   * Parse the HTTP request.
   */
  public void parseRequest(InputStream request) throws IOException {
    final BufferedReader br = new BufferedReader(new InputStreamReader(request));

    if(setRequestLine(br.readLine()) != -1) {
      String header = br.readLine();
      while (header.length() > 0) {
        appendHeaderParameter(header);
        header = br.readLine();
      }

      String bodyLine = br.readLine();
      while (bodyLine != null) {
        appendMessageBody(bodyLine);
        bodyLine = br.readLine();
      }
    }
  }

  /**
   * The Request-Line begins with a method token, followed by the Request-URI and the protocol
   * version, and ending with CRLF. The elements are separated by space SP characters.
   */
  public String getRequestLine() {
    return requestLine;
  }

  private int setRequestLine(String requestLine) {
    if (requestLine == null || requestLine.length() == 0) {
      logger.log(Level.SEVERE, "Invalid Request-Line " + requestLine);

      return -1;
    }
    this.requestLine = requestLine;

    return 0;
  }

  private void appendHeaderParameter(String header) {
    int idx = header.indexOf(":");

    if (idx == -1) {
      logger.log(Level.SEVERE, "Invalid Header Parameter: " + header);
    }
    requestHeaders.put(header.substring(0, idx).toLowerCase(), header.substring(idx + 2));
  }

  /**
   * The message-body (if any) of an HTTP message is used to carry the entity-body associated with
   * the request or response. The message-body differs from the entity-body only when a
   * transfer-coding has been applied, as indicated by the Transfer-Encoding header field (section
   * 14.41).
   *
   * @return String with message-body
   */
  public String getMessageBody() {
    return messageBody.toString();
  }

  private void appendMessageBody(String bodyLine) {
    messageBody.append(bodyLine).append("\r\n");
  }

  /**
   * The request-header fields allow the client to pass additional information about the request,
   * and about the client itself, to the server. These fields act as request modifiers
   */
  public Map<String, String> getRequestHeaders() {
    return requestHeaders;
  }
}
