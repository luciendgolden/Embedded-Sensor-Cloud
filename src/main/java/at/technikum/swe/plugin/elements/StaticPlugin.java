package at.technikum.swe.plugin.elements;

import BIF.SWE1.interfaces.Plugin;
import BIF.SWE1.interfaces.Request;
import BIF.SWE1.interfaces.Response;
import at.technikum.swe.common.ContentTypes;
import at.technikum.swe.common.Status;
import at.technikum.swe.foundation.EnumUtil;
import at.technikum.swe.foundation.PluginUtil;
import at.technikum.swe.mywebserver.MultiServer;
import at.technikum.swe.response.ResponseImpl;
import at.technikum.swe.url.UrlImpl;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

public class StaticPlugin implements Plugin {

  private final static Logger logger = Logger.getLogger(MultiServer.class.getName());

  private Response res;

  private ContentTypes contentType;

  private InputStream inputStream;
  private UrlImpl url;
  private String fileName;
  private String extension;

  @Override
  public float canHandle(Request request) {
    float handleable = PluginUtil.getTheProbability(TestPlugin.class, request);
    final String path = request.getUrl().getPath();
    long slashCounter = path.chars().filter(character -> character == '/').count();

    if (slashCounter > 0) {
      handleable += 0.1f;
    }

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
    this.res = new ResponseImpl();
    this.url = (UrlImpl) request.getUrl();

    if (url.isFile()) {
      this.fileName = url.getFileName();
      this.extension = url.getExtension();

      logger.info(String.format("Searching for fileName: %s",
          System.getProperty("user.dir") + "/tmp-static-files/" + fileName));

      File f = new File(System.getProperty("user.dir") + "/tmp-static-files");
      File[] matchingFiles = f
          .listFiles((dir, name) -> name.equals(fileName));


      try {
        contentType = EnumUtil.getContainingEnumType(ContentTypes.class, extension);
        res.setContentType(contentType.getValue());

        if (matchingFiles != null && matchingFiles.length > 0) {
          this.inputStream = new FileInputStream(matchingFiles[0]);

          res.setStatusCode(Status.OK.getStatusCode());
          res.setContent(inputStream);
        } else {
          res.setStatusCode(Status.NOT_FOUND.getStatusCode());
          res.setContent("404");
        }
      } catch (FileNotFoundException e) {
        logger.log(Level.SEVERE, "Unexpected error " + e.getMessage(), e);
      }
    } else {
      contentType = ContentTypes.TEXT_HTML;

      res.setContentType(contentType.getValue());
      res.setStatusCode(Status.OK.getStatusCode());
      res.setContent("index.html");
    }

    return res;
  }

  @Override
  public String toString() {
    return "StaticPlugin{}";
  }
}
