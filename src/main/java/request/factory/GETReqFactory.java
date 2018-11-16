package main.java.request.factory;

import java.util.Map;
import main.java.request.HttpRequestParser;
import main.java.request.RequestLine;
import main.java.request.event.GETReq;

public class GETReqFactory implements BaseRequestFactory<GETReq>{

  @Override
  public boolean isResponsible(String requestLine) {
    return requestLine.split("[ ]")[0].toUpperCase().equals("GET");
  }

  @Override
  public GETReq createEventFromRequest(HttpRequestParser parser) {
    final RequestLine requestLine = new RequestLine(parser.getRequestLine());
    final Map<String, String> requestHeader = parser.getRequestHeaders();

    return new GETReq(requestLine, requestHeader);
  }
}
