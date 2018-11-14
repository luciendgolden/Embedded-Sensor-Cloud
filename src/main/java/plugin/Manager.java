package main.java.plugin;

import BIF.SWE1.interfaces.Plugin;
import BIF.SWE1.interfaces.PluginManager;
import java.util.Arrays;
import java.util.List;
import main.java.foundation.Ensurer;

public class Manager<T extends AbstractPlugin> implements PluginManager {
  private final List<Plugin> pluginList = Arrays.asList(
      new PluginA(),
      new PluginB()
  );

  public Manager() {
  }

  @Override
  public Iterable<Plugin> getPlugins() {
    return pluginList;
  }

  @Override
  public void add(Plugin plugin) {
    Ensurer.ensureNotNull(plugin, "plugin");
    pluginList.add(plugin);
  }

  @Override
  public void add(String s)
      throws InstantiationException, IllegalAccessException, ClassNotFoundException {
    Ensurer.ensureNotNull(s, "pluginString");
    // TODO - implementation
  }

  @Override
  public void clear() {

  }
}
