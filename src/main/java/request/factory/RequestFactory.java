package main.java.request.factory;

import static java.util.Optional.ofNullable;

import BIF.SWE1.interfaces.Request;
import java.io.IOException;
import java.io.InputStream;
import java.io.PushbackInputStream;
import java.util.List;
import java.util.Map;
import main.java.foundation.Lists;
import main.java.request.HttpRequestParser;
import main.java.request.RequestLine;
import main.java.request.event.FallbackReq;
import main.java.request.event.InvalidRequest;

public class RequestFactory {

  private static final List<BaseRequestFactory> FACTORIES = Lists.immutableList(
      new POSTReqFactory(),
      new GETReqFactory()
  );

  public RequestFactory() {
  }

  /**
   * https://www.tutorialspoint.com/http/http_requests.htm
   *
   * @param in - Get an Inputstream with a standardized http header
   *
   * Example of Request Message
   *
   * GET / HTTP/1.1 Host: localhost Connection: keep-alive Accept: text/html,application/xhtml+xml
   * User-Agent: Unit-Test-Agent/1.0 (The OS) Accept-Encoding: gzip,deflate,sdch Accept-Language:
   * de-AT,de;q=0.8,en-US;q=0.6,en;q=0.4
   */
  public static Request createRequest(InputStream in) throws IOException {

    PushbackInputStream pushbackInputStream = new PushbackInputStream(in);
    int b;
    b = pushbackInputStream.read();

    if ( b == -1 || b == 0) {
      return new InvalidRequest();
    }
    pushbackInputStream.unread(b);

    in = pushbackInputStream;

    if (in != null) {
      final HttpRequestParser parser = new HttpRequestParser();
      parser.parseRequest(in);

      if(parser.getRequestLine() == null)
        return new InvalidRequest();

      for (BaseRequestFactory factory : FACTORIES) {
        if (factory.isResponsible(parser.getRequestLine())) {
          return factory.createEventFromRequest(parser);
        }
      }

      final RequestLine requestLine = new RequestLine(parser.getRequestLine());
      final Map<String, String> requestHeaders = parser.getRequestHeaders();
      final String messageBody = parser.getMessageBody();

      return new FallbackReq(requestLine, requestHeaders, ofNullable(messageBody));
    }

    return new InvalidRequest();
  }

  private static InputStream checkStreamIsNotEmpty(InputStream inputStream) throws IOException {
    PushbackInputStream pushbackInputStream = new PushbackInputStream(inputStream);
    int b;
    b = pushbackInputStream.read();
    if (b == -1) {
      throw new IllegalArgumentException("No byte can be read from stream " + inputStream);
    }
    pushbackInputStream.unread(b);
    return pushbackInputStream;
  }
}
