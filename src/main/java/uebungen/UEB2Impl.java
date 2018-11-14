package main.java.uebungen;

import BIF.SWE1.interfaces.Request;
import BIF.SWE1.interfaces.Response;
import BIF.SWE1.interfaces.UEB2;
import BIF.SWE1.interfaces.Url;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import main.java.request.factory.RequestFactory;
import main.java.response.ResponseImpl;
import main.java.url.UrlImpl;

public class UEB2Impl implements UEB2 {

  private final static Logger logger = Logger.getLogger(UEB2Impl.class.getName());

  @Override
  public void helloWorld() {

  }

  @Override
  public Url getUrl(String s) {
    return new UrlImpl(s);
  }

  @Override
  public Request getRequest(InputStream inputStream) {
    try {
      return RequestFactory.createRequest(inputStream);
    } catch (IOException e) {
      logger.log(Level.SEVERE, "Unexpected error " + e.getMessage(), e);
    }

    return null;
  }

  @Override
  public Response getResponse() {
    return new ResponseImpl();
  }
}
