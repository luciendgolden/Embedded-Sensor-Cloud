package at.technikum.swe.plugin;

import static at.technikum.swe.foundation.Lists.toList;

import BIF.SWE1.interfaces.Plugin;
import BIF.SWE1.interfaces.PluginManager;
import at.technikum.swe.foundation.ClassLoader;
import at.technikum.swe.foundation.Lists;
import at.technikum.swe.plugin.elements.NaviPlugin;
import at.technikum.swe.plugin.elements.StaticPlugin;
import at.technikum.swe.plugin.elements.TemperaturPlugin;
import at.technikum.swe.plugin.elements.ToLowerPlugin;
import java.net.MalformedURLException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PluginManagerImpl implements PluginManager {

  // Not immutable list - so it can be modified
  // be careful with Arrays.asList because it will be immutable
  private final List<Plugin> pluginList = toList(
      new StaticPlugin(),
      new ToLowerPlugin(),
      new NaviPlugin(),
      new TemperaturPlugin());

  @Override
  public Iterable<Plugin> getPlugins() {
    return pluginList;
  }

  @Override
  public void add(Plugin plugin) {
    pluginList.add(plugin);
  }

  @Override
  public void add(String s)
      throws InstantiationException, IllegalAccessException, ClassNotFoundException {
    try {
      Plugin plugin = ClassLoader.loadClass(s);

      if(plugin != null)
        pluginList.add(plugin);

    } catch (MalformedURLException e) {
      e.printStackTrace();
    }

  }

  @Override
  public void clear() {
    pluginList.clear();
  }
}
