package main.java.plugin;

import BIF.SWE1.interfaces.Request;
import BIF.SWE1.interfaces.Response;

public class PluginB extends AbstractPlugin{

  @Override
  public float canHandle(Request request) {
    return 0;
  }

  @Override
  public Response handle(Request request) {
    return null;
  }
}
