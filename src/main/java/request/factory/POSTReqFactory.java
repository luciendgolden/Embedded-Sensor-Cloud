package main.java.request.factory;

import java.util.Map;
import main.java.request.HttpRequestParser;
import main.java.request.RequestLine;
import main.java.request.event.POSTReq;

public class POSTReqFactory implements BaseRequestFactory<POSTReq> {

  @Override
  public boolean isResponsible(String requestLine) {
    return requestLine.split("[ ]")[0].toUpperCase().equals("POST");
  }

  @Override
  public POSTReq createEventFromRequest(HttpRequestParser parser) {
    final RequestLine requestLine = new RequestLine(parser.getRequestLine());
    final Map<String, String> requestHeaders = parser.getRequestHeaders();
    final String messageBody = parser.getMessageBody();

    return new POSTReq(requestLine, requestHeaders, messageBody);
  }
}
