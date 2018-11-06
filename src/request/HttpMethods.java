package request;

public enum HttpMethods {
  GET,
  POST,
  DELETE,
  PATCH;

  public static boolean contains(String name) {
    for (HttpMethods type : HttpMethods.values()) {
      if (type.name().equals(name)) {
        return true;
      }
    }

    return false;
  }
}
