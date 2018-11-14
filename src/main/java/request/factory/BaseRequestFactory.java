package main.java.request.factory;

import BIF.SWE1.interfaces.Request;
import java.io.InputStream;
import main.java.request.HttpRequestParser;


public interface BaseRequestFactory<E extends Request>{
  boolean isResponsible(String requestLine);

  E createEventFromRequest(HttpRequestParser parser);
}
