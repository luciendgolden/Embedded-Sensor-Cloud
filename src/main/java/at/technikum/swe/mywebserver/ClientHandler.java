package at.technikum.swe.mywebserver;

import BIF.SWE1.interfaces.Plugin;
import BIF.SWE1.interfaces.Request;
import BIF.SWE1.interfaces.Response;
import at.technikum.swe.plugin.PluginManagerImpl;
import at.technikum.swe.request.RequestImpl;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClientHandler extends Thread {

  private final static Logger logger = Logger.getLogger(ClientHandler.class.getName());

  private InputStream in;
  private OutputStream out;
  private Socket clientSocket;


  /**
   * Constructs a handler thread, squirreling away the clientSocket. All the interesting work is
   * done in the run method.
   */
  public ClientHandler(Socket clientsocket) {
    this.clientSocket = clientsocket;
  }

  @Override
  public void run() {
    try {
      logger.info(String.format("Client %s:%s has been connected", clientSocket.getInetAddress(),
          clientSocket.getPort()));

      this.in = clientSocket.getInputStream();
      this.out = clientSocket.getOutputStream();

      Request req = new RequestImpl(in);
      logger.info(String.format("Received request:\n%s", req));

      Response res = null;

      Iterable<Plugin> list = new PluginManagerImpl().getPlugins();
      Plugin pluginToHandle = null;
      float max = 0f;

      for (Plugin plugin : list) {
        float temp = plugin.canHandle(req);

        if (temp > max) {
          max = temp;
          pluginToHandle = plugin;
        }
      }

      if (pluginToHandle != null) {
        logger.info(String.format("Plugin '%s' will handle the request",
            pluginToHandle.getClass().getSimpleName()));
        res = pluginToHandle.handle(req);
      }

      res.send(out);

    } catch (IOException e) {
      logger.log(Level.SEVERE, "Unexpected error " + e.getMessage(), e);
    }finally {
      try {
        out.flush();
        out.close();
        in.close();
        clientSocket.close(); // Close the socket itself
      } catch (IOException e) {
        logger.log(Level.SEVERE, "Unexpected error " + e.getMessage(), e);
      }
    }
  }
}
