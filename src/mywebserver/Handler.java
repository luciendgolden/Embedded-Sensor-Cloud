package mywebserver;

import BIF.SWE1.interfaces.Url;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import uebungen.UEB1Impl;

public class Handler {

  private Socket socket;
  private BufferedReader in;
  private BufferedWriter out;

  /**
   * Constructs a handler thread, squirreling away the socket. All the interesting work is done in
   * the run method.
   */
  public Handler(Socket socket) {
    this.socket = socket;
    System.out.println("New connected client");
  }


  public void start() throws IOException {
    try {
      // Reader
      this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
      this.out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

      String input = in.readLine();

      if (input == null) {
        return;
      }

      final Url url = new UEB1Impl().getUrl(input);
    } finally {
      socket.close();
    }
  }
}
