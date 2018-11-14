package main.java.request.event;

import BIF.SWE1.interfaces.Request;
import BIF.SWE1.interfaces.Url;
import java.util.Map;
import main.java.foundation.Ensurer;
import main.java.request.RequestLine;
import main.java.url.UrlImpl;

public abstract class AbstractRequest implements Request {

  private RequestLine requestLine;
  private Map<String, String> requestHeader;

  public AbstractRequest(RequestLine requestLine, Map<String, String> requestHeader) {
    Ensurer.ensureNotNull(requestLine, "requestline");
    Ensurer.ensureNotNull(requestHeader, "requestheaders");
    this.requestLine = requestLine;
    this.requestHeader = requestHeader;
  }

  @Override
  public boolean isValid() {
    return requestLine.getMethod() != null && requestLine.getRequestUri().startsWith("/");
  }

  @Override
  public String getMethod() {
    if (this.isValid()) {
      return requestLine.getMethod().toString();
    }

    return null;
  }

  @Override
  public Url getUrl() {
    if (this.isValid()) {
      return new UrlImpl(requestLine.getRequestUri());
    }

    return null;
  }

  @Override
  public Map<String, String> getHeaders() {
    return requestHeader;
  }

  @Override
  public int getHeaderCount() {
    return requestHeader.size();
  }

  @Override
  public String getUserAgent() {
    return requestHeader.get("user-agent");
  }

  @Override
  public String getContentType() {
    return null;
  }

  public String getHeaderParam(String headerName) {
    return requestHeader.get(headerName);
  }

  public RequestLine getRequestLine() {
    return requestLine;
  }
}
