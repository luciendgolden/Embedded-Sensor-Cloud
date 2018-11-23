package at.technikum.swe.plugin.elements;

import BIF.SWE1.interfaces.Plugin;
import BIF.SWE1.interfaces.Request;
import BIF.SWE1.interfaces.Response;
import BIF.SWE1.interfaces.Url;
import at.technikum.swe.plugin.PluginUtil;
import at.technikum.swe.response.ResponseImpl;

public class TestPlugin implements Plugin {
  @Override
  public float canHandle(Request request) {
    float handleable = PluginUtil.getTheProbability(TestPlugin.class, request);
    if(request.getUrl().getPath().equals("/"))
      handleable += 0.1f;

    return handleable;
  }

  @Override
  public Response handle(Request request) {
    Url url = request.getUrl();
    String fileFromRequest = url.getSegments()[url.getSegments().length - 1];
    Response res = new ResponseImpl();
    res.setStatusCode(200);

    if (fileFromRequest.endsWith(".html")) {
      //TODO - send plugin html file
    }


    res.setContent("the content is set with TEST content now");

    return res;
  }
}
