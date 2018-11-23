package at.technikum.swe.plugin.elements;

import BIF.SWE1.interfaces.Plugin;
import BIF.SWE1.interfaces.Request;
import BIF.SWE1.interfaces.Response;

public class ToLowerPlugin implements Plugin {

  @Override
  public float canHandle(Request request) {
    return 0;
  }

  @Override
  public Response handle(Request request) {
    return null;
  }

  @Override
  public String toString() {
    return "ToLowerPlugin{}";
  }
}
