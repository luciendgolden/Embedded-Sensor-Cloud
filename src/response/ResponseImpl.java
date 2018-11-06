package response;

import BIF.SWE1.interfaces.Response;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

public class ResponseImpl implements Response {

  @Override
  public Map<String, String> getHeaders() {
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
  public void setContentType(String s) {

  }

  @Override
  public int getStatusCode() {
    return 0;
  }

  @Override
  public void setStatusCode(int i) {

  }

  @Override
  public String getStatus() {
    return null;
  }

  @Override
  public void addHeader(String s, String s1) {

  }

  @Override
  public String getServerHeader() {
    return null;
  }

  @Override
  public void setServerHeader(String s) {

  }

  @Override
  public void setContent(String s) {

  }

  @Override
  public void setContent(byte[] bytes) {

  }

  @Override
  public void setContent(InputStream inputStream) {

  }

  @Override
  public void send(OutputStream outputStream) {

  }
}
