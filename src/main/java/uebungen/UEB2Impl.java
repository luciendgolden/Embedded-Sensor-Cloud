package main.java.uebungen;

import BIF.SWE1.interfaces.Request;
import BIF.SWE1.interfaces.Response;
import BIF.SWE1.interfaces.UEB2;
import BIF.SWE1.interfaces.Url;
import java.io.InputStream;
import main.java.request.RequestImpl;
import main.java.response.ResponseImpl;
import main.java.url.UrlImpl;

public class UEB2Impl implements UEB2 {

  @Override
  public void helloWorld() {

  }

  @Override
  public Url getUrl(String s) {
    return new UrlImpl(s);
  }

  @Override
  public Request getRequest(InputStream inputStream) {
    return new RequestImpl(inputStream);
  }

  @Override
  public Response getResponse() {
    return new ResponseImpl();
  }
}
