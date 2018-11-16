package main.java.plugin;


import BIF.SWE1.interfaces.Plugin;
import BIF.SWE1.interfaces.Request;
import BIF.SWE1.interfaces.Url;
import java.util.Map;

public class PluginUtil {

  /**
   * Get the probability to use a specific at.technikum.swe.plugin in a given at.technikum.swe.request
   * @param clazz - Specific pluginclass who extends from Plugin
   * @param req
   * @param <T>
   * @return
   */
  public static <T extends Plugin> float getTheProbability(Class<T> clazz, Request req) {
    float handleable = 0f;
    Url url = req.getUrl();

    String name = clazz.getSimpleName();
    String pluginName = name.substring(0, name.length() - 6);

    //look for Plugin name in URL
    String pathParts[] = req.getUrl().getSegments();
    for (String path : pathParts) {
      if (path.contains(pluginName.toLowerCase())) {
        handleable += 0.1f;
      }
    }

    if(url.getParameterCount() > 0) {
      Map<String, String> parameters = req.getUrl().getParameter();
      if (parameters.get(name + "_plugin") == "true") {
        handleable += 0.2f;
      }
    }

    if (handleable > 1) {
      return 1f;
    }

    return handleable;
  }
}