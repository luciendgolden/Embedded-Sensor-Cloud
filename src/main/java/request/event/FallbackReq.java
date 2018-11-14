package main.java.request.event;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Optional;
import main.java.request.RequestLine;

public class FallbackReq extends AbstractRequest {

  private final Optional<String> messageBody;

  public FallbackReq(RequestLine requestLine, Map<String, String> requestHeader,
      Optional<String> messageBody) {
    super(requestLine, requestHeader);
    this.messageBody = messageBody;
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

  public Optional<String> getMessageBody() {
    return messageBody;
  }
}
