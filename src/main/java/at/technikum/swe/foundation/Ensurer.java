package at.technikum.swe.foundation;

public final class Ensurer {

  public static <T> T ensureNotNull(T object) {
    return ensureNotNull(object, "argument");
  }

  public static <T> void ensureNotNull(String objectName, T... objects) {
    for(T object:objects)
      ensureNotNull(object, objectName);
  }

  public static <T> T ensureNotNull(T object, String objectName) {
    if (object == null) {
      throw new IllegalArgumentException(String.format("%s must not be null!",
          objectName));
    }
    return object;
  }

  public static String ensureNotBlank(String object, String objectName) {
    if (object.isEmpty()) {
      throw new IllegalArgumentException(String.format("%s must not be null, " +
          "empty or blank!", objectName));
    }
    return object;
  }
}

