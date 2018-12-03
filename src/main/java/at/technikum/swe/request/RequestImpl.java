package at.technikum.swe.request;

import static at.technikum.swe.foundation.EnumUtil.isEqualTo;

import BIF.SWE1.interfaces.Request;
import BIF.SWE1.interfaces.Url;
import at.technikum.swe.common.HttpMethods;
import at.technikum.swe.foundation.Ensurer;
import at.technikum.swe.foundation.EnumUtil;
import at.technikum.swe.url.UrlImpl;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class RequestImpl implements Request {

  private final static Logger logger = Logger.getLogger("requests");

  private String requestLine;

  private BufferedReader in;
  private String[] parameters;
  private Map<String,String> headers = new HashMap<String, String>();
  private String body;


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
   *
   * CONTENT
   **/
  public RequestImpl(InputStream in) {
    this.in = new BufferedReader(new InputStreamReader(in));
    try {
      setParameters();
      setHeaders();
      setBody();
    } catch (IOException e) {
      logger.log(Level.WARNING, "Unexpected error " + e.getMessage(), e);
    }
  }

  private void setBody() throws IOException {
    boolean isPOST = isEqualTo(HttpMethods.class, getMethod(), HttpMethods.POST);

    if(isPOST){
     if(in!=null){
       this.body=in.readLine();
     }
    }
  }

  private void setHeaders() throws IOException{
    if(in!=null) {
      String line = in.readLine();
      if(line!=null){
      while (!line.isEmpty()) {
        String parts[] = line.split(": ");
        this.headers.put(parts[0].toLowerCase(), parts[1]);
        line = in.readLine();
      }}
    }
  }

  private void setParameters() throws IOException {
    this.requestLine = in.readLine();
    this.parameters = requestLine.split("[ ]");
  }

  @Override
  public boolean isValid() {
    if(parameters.length != 3)
      return false;

    return EnumUtil.contains(HttpMethods.class, parameters[0].toUpperCase())
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
    System.out.println(this.headers.toString());
    return Ensurer.ensureNotNull(headers,"Headers");
  }

  @Override
  public int getHeaderCount() {
    return headers.size();
  }

  @Override
  public String getUserAgent() {
    if(headers.size()>0) {
      return headers.get("user-agent");
    }
    return null;
    }

  @Override
  public int getContentLength() {
    return Integer.parseInt(headers.get("content-length"));
  }

  @Override
  public String getContentType() {
    if(headers.size()>0){
      return headers.get("content-type");
    }
    return null;
  }

  @Override
  public InputStream getContentStream() {
  if(body!=null){
    return new ByteArrayInputStream(body.getBytes());
  }
    return null;
  }

  @Override
  public String getContentString() {
    if(body!=null){
      return body;
    }
    return null;
  }

  @Override
  public byte[] getContentBytes() {
    byte[] contentBytes = new byte[0];
    try {
      contentBytes = body.getBytes("UTF-8");
    } catch (UnsupportedEncodingException e) {
      logger.log(Level.SEVERE, "Unexpected error " + e.getMessage(), e);
    }

    return contentBytes;
  }

  public String getRequestLine() {
    return requestLine;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();

    String myHeaders = headers.entrySet().stream()
        .map(entry -> entry.getKey() + ": " + entry.getValue())
        .collect(Collectors.joining(System.getProperty("line.separator")));

    builder.append("Request Line: ");
    builder.append("\n\t");
    builder.append(getRequestLine());
    builder.append("\n\t");
    builder.append("Request Type: ");
    builder.append(getMethod());
    builder.append("\n\t");
    builder.append("Request Path ");
    builder.append(getUrl().getPath());
    builder.append("\n");
    builder.append("Headers ");
    builder.append("\n\t");
    builder.append(myHeaders);

    return builder.toString();
  }
}
