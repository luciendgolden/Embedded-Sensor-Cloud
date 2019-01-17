package at.technikum.swe.foundation;


import BIF.SWE1.interfaces.Plugin;
import BIF.SWE1.interfaces.Request;
import BIF.SWE1.interfaces.Url;
import at.technikum.swe.url.UrlImpl;
import java.util.Map;

public class PluginUtil {

  /**
   * Get the probability to use a specific at.technikum.swe.at.technikum.swe.plugin in a given
   * at.technikum.swe.at.technikum.swe.request
   *
   * @param clazz - Specific pluginclass who extends from Plugin
   */
  public static <T extends Plugin> float getTheProbability(Class<T> clazz, Request req) {
    float handleable = 0f;
    Url url = req.getUrl();

    String name = clazz.getSimpleName().toLowerCase();
    String pluginName = name.substring(0, name.length() - 6);

    //look for Plugin name in URL
    String pathParts[] = req.getUrl().getSegments();
    for (String path : pathParts) {
      if (path.contains(pluginName.toLowerCase())) {
        handleable += 0.4f;
      }
    }

    if (url.getParameterCount() > 0) {
      Map<String, String> parameters = req.getUrl().getParameter();
      if (parameters.get(pluginName + "_plugin") != null && parameters.get(pluginName + "_plugin").equals("true")) {
        handleable += 0.4f;
      }
    }

    if (handleable > 1) {
      return 1f;
    }

    return handleable;
  }
}