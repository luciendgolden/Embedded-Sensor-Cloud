package at.technikum.swe.foundation;

import BIF.SWE1.interfaces.Plugin;
import at.technikum.swe.Main;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;
import java.util.jar.JarFile;
import java.util.logging.Logger;

public class ClassLoader {

  private final static Logger logger = Logger.getLogger(ClassLoader.class.getName());

  public static Plugin loadClass(String className)
      throws MalformedURLException, ClassNotFoundException, IllegalAccessException, InstantiationException {
    File folder = new File(".");
    File[] listOfFiles = folder.listFiles();
    Plugin plugin = null;

    for (int i = 0; i < listOfFiles.length; i++) {
      if (!listOfFiles[i].isFile()) {
        continue;
      }
      final String file = listOfFiles[i].getName();
      if (!file.toLowerCase().endsWith(".jar")) {
        continue;
      }
      logger.info(String.format("Inspecting file %s", file));

      File jarFile = new File(file);

      URL fileURL = jarFile.toURI().toURL();
      String jarURL = "jar:" + fileURL + "!/";
      URL urls[] = {new URL(jarURL)};
      URLClassLoader ucl = new URLClassLoader(urls);
      Class<?> clazz = Class.forName(className, true, ucl);
      Class<?>[] clazzInterfaces = clazz.getInterfaces();

      /*
      Arrays.stream(clazzInterfaces).filter(e -> e.equals("Plugin"))
          .map(e -> e.newInstance())
      */

      plugin = (Plugin) clazz.newInstance();

    }

    return plugin;
  }

  }
