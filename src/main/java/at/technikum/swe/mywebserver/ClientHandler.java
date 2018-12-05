package at.technikum.swe.mywebserver;

import BIF.SWE1.interfaces.Plugin;
import BIF.SWE1.interfaces.Request;
import BIF.SWE1.interfaces.Response;
import at.technikum.swe.plugin.PluginManagerImpl;
import at.technikum.swe.request.RequestImpl;
import at.technikum.swe.response.ResponseImpl;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Logger;

public class ClientHandler extends Thread {

  private final static Logger logger = Logger.getLogger(ClientHandler.class.getName());

  private Socket clientSocket;

  /**
   * Constructs a handler thread, squirreling away the clientSocket. All the interesting work is
   * done in the run method.
   */
  public ClientHandler(Socket socket) {
    this.clientSocket = socket;
  }

  @Override
  public void run() {
    try {
      ClassLoader classLoader = getClass().getClassLoader();
      InputStream in = classLoader.getResourceAsStream("WEB-INF/index.html");

      //TODO - handle at.technikum.swe.request
      Request req = new RequestImpl(clientSocket.getInputStream());
      logger.info(String.format("Received request:\n%s", req));

      Response res = null;

      Iterable<Plugin> list = new PluginManagerImpl().getPlugins();
      Plugin pluginToHandle = null;
      float max=0f;

      for(Plugin plugin : list){
        float temp = plugin.canHandle(req);
        if(temp > max){
          max = temp;
          pluginToHandle = plugin;
        }
      }

      if(pluginToHandle!=null)
        res = pluginToHandle.handle(req);

      res.send(clientSocket.getOutputStream());

      clientSocket.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}