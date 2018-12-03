package at.technikum.swe.plugin.elements;

import BIF.SWE1.interfaces.Plugin;
import BIF.SWE1.interfaces.Request;
import BIF.SWE1.interfaces.Response;
import BIF.SWE1.interfaces.Url;
import at.technikum.swe.common.ContentTypes;
import at.technikum.swe.foundation.EnumUtil;
import at.technikum.swe.foundation.PluginUtil;
import at.technikum.swe.response.ResponseImpl;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class StaticPlugin implements Plugin {

  @Override
  public float canHandle(Request request) {
    float handleable = PluginUtil.getTheProbability(TestPlugin.class, request);
    final String path = request.getUrl().getPath();
    long slashCounter = path.chars().filter(character -> character == '/').count();

    if(slashCounter > 0)
        handleable += 0.1f;

    if (slashCounter == 1l) {
      handleable += 0.9f;
    }

    if (handleable > 1) {
      return 1f;
    }

    return handleable;
  }

  @Override
  public Response handle(Request request) {
    Url url = request.getUrl();
    String fileFromRequest = url.getFileName();
    //TODO - exception handling
    String extension = url.getExtension();
    Response res = new ResponseImpl();
    res.setStatusCode(200);

    //ClassLoader classLoader = getClass().getClassLoader();
    //InputStream in = classLoader.getResourceAsStream(String.format("WEB-INF/%s", fileFromRequest));

    String myFile = "";

    try {
       myFile = Files.walk(Paths.get(fileFromRequest))
          .filter(Files::isRegularFile).map(Path::toString).findFirst()
          .orElse("tmp-static-files/404.txt");
    } catch (IOException e) {
      e.printStackTrace();
    }

    ContentTypes myContenttype = EnumUtil.getContainingEnumType(ContentTypes.class, extension);

    res.setContentType(myContenttype.getValue());

    res.setContent(myFile);

    return res;
  }

  @Override
  public String toString() {
    return "StaticPlugin{}";
  }
}
