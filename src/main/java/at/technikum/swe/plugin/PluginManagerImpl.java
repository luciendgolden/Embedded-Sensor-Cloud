package at.technikum.swe.plugin;

import BIF.SWE1.interfaces.Plugin;
import BIF.SWE1.interfaces.PluginManager;
import BIF.SWE1.interfaces.Request;
import BIF.SWE1.interfaces.Response;
import at.technikum.swe.plugin.elements.NaviPlugin;
import at.technikum.swe.plugin.elements.TemperaturPlugin;
import at.technikum.swe.plugin.elements.ToLowerPlugin;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PluginManagerImpl implements PluginManager {

  // Not immutable list - so it can be modified
  // be careful with Arrays.asList because it will be immutable
  private final List<Plugin> pluginList = Stream.of(
      new ToLowerPlugin(),
      new NaviPlugin(),
      new TemperaturPlugin()
  ).collect(Collectors.toList());

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

  }

  @Override
  public void clear() {
    pluginList.clear();
  }
}
