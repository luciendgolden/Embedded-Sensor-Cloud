package at.technikum.swe.uebungen;

import static at.technikum.swe.foundation.SystemUtil.FILE_SEPERATOR;

import BIF.SWE1.interfaces.Plugin;
import BIF.SWE1.interfaces.PluginManager;
import BIF.SWE1.interfaces.Request;
import BIF.SWE1.interfaces.UEB5;
import at.technikum.swe.plugin.PluginManagerImpl;
import at.technikum.swe.plugin.elements.StaticPlugin;
import at.technikum.swe.request.RequestImpl;
import java.io.File;
import java.io.InputStream;

public class UEB5Impl implements UEB5 {

  @Override
  public void helloWorld() {

  }

  @Override
  public Request getRequest(InputStream inputStream) {
    return new RequestImpl(inputStream);
  }

  @Override
  public PluginManager getPluginManager() {
    return new PluginManagerImpl();
  }

  @Override
  public Plugin getStaticFilePlugin() {
    return new StaticPlugin();
  }

  @Override
  public void setStatiFileFolder(String s) {

  }

  @Override
  public String getStaticFileUrl(String s) {
    File f = new File(System.getProperty("user.dir") + FILE_SEPERATOR + "tmp-static-files");
    File[] matchingFiles = f
        .listFiles((dir, name) -> name.equals(s));

    if (matchingFiles.length > 0) {
      return (matchingFiles[0].toString().replace(System.getProperty("user.dir"), ""))
          .replace(FILE_SEPERATOR, "/");
    }

    return String.format("/tmp-static-files/%s", s);
  }
}

