package at.technikum.swe.plugin.elements;

import static at.technikum.swe.common.Status.NOT_FOUND;
import static at.technikum.swe.common.Status.OK;
import static at.technikum.swe.foundation.SystemUtil.FILE_SEPERATOR;
import static at.technikum.swe.foundation.SystemUtil.STATIC_FOLDER_PATH;
import static at.technikum.swe.foundation.SystemUtil.USER_DIR;

import BIF.SWE1.interfaces.Plugin;
import BIF.SWE1.interfaces.Request;
import BIF.SWE1.interfaces.Response;
import at.technikum.swe.common.ContentType;
import at.technikum.swe.common.Status;
import at.technikum.swe.foundation.EnumUtil;
import at.technikum.swe.foundation.PluginUtil;
import at.technikum.swe.foundation.SystemUtil;
import at.technikum.swe.mywebserver.MultiServer;
import at.technikum.swe.response.ResponseImpl;
import at.technikum.swe.url.UrlImpl;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

public class StaticPlugin implements Plugin {

  private final static Logger logger = Logger.getLogger(MultiServer.class.getName());

  private ResponseImpl res;

  private ContentType contentType;

  private InputStream inputStream;
  private UrlImpl url;
  private String fileName;
  private String extension;

  @Override
  public float canHandle(Request request) {
    float handleable = PluginUtil.getTheProbability(StaticPlugin.class, request);
    final String path = request.getUrl().getPath();
    long slashCounter = path.chars().filter(character -> character == '/').count();

    if (slashCounter > 0) {
      handleable += 0.1f;
    }

    if (slashCounter == 1l) {
      handleable += 0.2f;
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

    final File folderPath = new File(STATIC_FOLDER_PATH);

    try {
      //TODO - Issue!
      // Proper handling for server handling file exists/file does not exist/path is directory
      if (url.isFile()) {
        this.fileName = url.getFileName();
        this.extension = url.getExtension();

        logger.info(String
            .format("Searching for fileName: %s", STATIC_FOLDER_PATH + FILE_SEPERATOR + fileName));

        File[] matchingFiles = folderPath
            .listFiles((dir, name) -> name.equals(fileName));


        if (matchingFiles != null && matchingFiles.length > 0) {
          contentType = EnumUtil.getContainingEnumType(ContentType.class, extension);
          res.setContentType(contentType);

          this.inputStream = new FileInputStream(matchingFiles[0]);

          res.setStatusCode(OK);
          res.setContent(inputStream);
        } else {
          res.setContentType(ContentType.TEXT_HTML);
          res.setStatusCode(NOT_FOUND);
          res.setContent(new FileInputStream(new File(folderPath, "404.html")));
        }

      } else {
        res.setContentType(ContentType.TEXT_HTML);
        res.setStatusCode(OK);
        res.setContent(new FileInputStream(new File(folderPath, "index.html")));
      }
    } catch (FileNotFoundException e) {
      logger.log(Level.SEVERE, "Unexpected error " + e.getMessage(), e);
    }

    return res;
  }

  @Override
  public String toString() {
    return "StaticPlugin{}";
  }
}
