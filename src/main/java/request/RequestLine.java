package main.java.request;

import main.java.common.HttpMethod;
import main.java.foundation.Ensurer;
import main.java.foundation.EnumUtil;

public final class RequestLine {
  private HttpMethod method;
  private String requestUri;
  private String httpVersion;

  public RequestLine(String requestLine) {
    final String[] parameters = requestLine.split("[ ]");

    if(parameters.length == 3) {
      this.method = EnumUtil.fromString(parameters[0], HttpMethod.class);
      this.requestUri = parameters[1];
      this.httpVersion = parameters[2];
    }
  }

  public HttpMethod getMethod() {
    return method;
  }

  public String getRequestUri() {
    return requestUri;
  }

  public String getHttpVersion() {
    return httpVersion;
  }
}
