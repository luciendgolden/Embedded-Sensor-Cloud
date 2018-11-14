package main.java.request.event;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import main.java.request.RequestLine;

public class BodyReq extends AbstractRequest {

  private String messageBody;

  public BodyReq(RequestLine requestLine, Map<String, String> requestHeader,
      String messageBody) {
    super(requestLine, requestHeader);
    this.messageBody = messageBody;
  }

  @Override
  public int getContentLength() {
    return 0;
  }

  @Override
  public InputStream getContentStream() {
    return new ByteArrayInputStream(
        messageBody.getBytes(StandardCharsets.UTF_8));
  }

  @Override
  public String getContentString() {
    return messageBody;
  }

  @Override
  public byte[] getContentBytes() {
    return messageBody.getBytes();
  }
}
