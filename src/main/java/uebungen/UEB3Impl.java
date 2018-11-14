package main.java.uebungen;

import java.io.IOException;
import java.io.InputStream;

import BIF.SWE1.interfaces.Plugin;
import BIF.SWE1.interfaces.Request;
import BIF.SWE1.interfaces.Response;
import BIF.SWE1.interfaces.UEB3;
import java.util.logging.Level;
import java.util.logging.Logger;
import main.java.response.ResponseImpl;
import main.java.request.factory.RequestFactory;

public class UEB3Impl implements UEB3 {

  private final static Logger logger = Logger.getLogger(UEB3Impl.class.getName());

  @Override
  public void helloWorld() {

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

  @Override
  public Plugin getTestPlugin() {
    return null;
  }
}
