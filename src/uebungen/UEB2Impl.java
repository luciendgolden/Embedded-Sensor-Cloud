package uebungen;

import BIF.SWE1.interfaces.Request;
import BIF.SWE1.interfaces.Response;
import BIF.SWE1.interfaces.UEB2;
import BIF.SWE1.interfaces.Url;
import java.io.InputStream;
import mywebserver.Server;
import mywebserver.ServerImpl;
import mywebserver.UrlImpl;

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
    Server server = new ServerImpl();
    server.setIn(inputStream);

    return server;
  }

  @Override
  public Response getResponse() {
    return null;
  }
}
