package at.technikum.swe.exception;

public class PersistenceException extends RuntimeException {
  public PersistenceException(String errMsg)
  {
    super(errMsg);
  }
}
