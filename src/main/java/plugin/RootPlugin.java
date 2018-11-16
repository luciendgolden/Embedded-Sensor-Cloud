package main.java.plugin;


import BIF.SWE1.interfaces.Plugin;
import BIF.SWE1.interfaces.Request;
import BIF.SWE1.interfaces.Response;
import BIF.SWE1.interfaces.Url;
import main.java.response.ResponseImpl;

public class RootPlugin implements Plugin {

  @Override
  public float canHandle(Request request) {
    float handleable = PluginUtil.getTheProbability(TestPlugin.class, request);
    if(request.isValid()&&handleable<1)
      handleable +=0.1;

    return handleable;
  }

  @Override
  public Response handle(Request request) {
    Url url = request.getUrl();
    String fileFromRequest = url.getSegments()[url.getSegments().length - 1];
    Response res = new ResponseImpl();
    res.setStatusCode(200);
    //setz ich den status code auf nice 200 ok
    if (fileFromRequest.endsWith(".html")) {
      /* //trying to load from file -TODO load from file
      ClassLoader classLoader = TestPlugin.class.getClassLoader();
      System.out.println(classLoader);
      InputStream in = classLoader.getResourceAsStream("webapp/WEB-INF/foo.html");
      final BufferedReader br = new BufferedReader(new InputStreamReader(in));
      String line = "EMPTY";
      try {
        line = br.readLine();
      } catch (IOException e) {
        e.printStackTrace();
      }

      System.out.println(line);
    */
      res.setContent("foo.html");

    }
    return res;
  }
}
