package main.java.request.factory;

import java.io.InputStream;
import java.util.Map;
import main.java.request.HttpRequestParser;
import main.java.request.RequestLine;
import main.java.request.event.PlainReq;

public class PlainReqFactory implements BaseRequestFactory<PlainReq>{

  @Override
  public boolean isResponsible(String requestLine) {
    return requestLine.split("[ ]")[0].toUpperCase().equals("GET");
  }

  @Override
  public PlainReq createEventFromRequest(HttpRequestParser parser) {
    final RequestLine requestLine = new RequestLine(parser.getRequestLine());
    final Map<String, String> requestHeader = parser.getRequestHeaders();

    return new PlainReq(requestLine, requestHeader);
  }
}
