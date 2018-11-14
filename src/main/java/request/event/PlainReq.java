package main.java.request.event;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import main.java.request.RequestLine;

public class PlainReq extends AbstractRequest {


  public PlainReq(RequestLine requestLine, Map<String, String> requestHeader) {
    super(requestLine, requestHeader);
  }

  @Override
  public int getContentLength() {
    return 0;
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
