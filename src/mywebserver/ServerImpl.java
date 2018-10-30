package mywebserver;

import BIF.SWE1.interfaces.Url;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

public class ServerImpl extends Server{

  public ServerImpl(int port) throws IOException {
    super(port);
  }

  public ServerImpl() {
  }

  @Override
  public boolean isValid() {
    return false;
  }

  @Override
  public String getMethod() {
    return null;
  }

  @Override
  public Url getUrl() {
    return null;
  }

  @Override
  public Map<String, String> getHeaders() {
    return null;
  }

  @Override
  public int getHeaderCount() {
    return 0;
  }

  @Override
  public String getUserAgent() {
    return null;
  }

  @Override
  public int getContentLength() {
    return 0;
  }

  @Override
  public String getContentType() {
    return null;
  }

  @Override
  public InputStream getContentStream() {
    return null;
  }

  @Override
  public String getContentString() {
    return null;
  }

  @Override
  public byte[] getContentBytes() {
    return new byte[0];
  }
}
