package main.java.foundation;

import java.util.EnumSet;

public class EnumUtil {

  /**
   * Search for a specific Type of an Enum
   * @param clazz - type of the Enum which should be searched
   * @param name - String to compare
   * @param <T> - Type of the Enum
   * @return boolean - if the Enum contains the given value
   */
  public static <T extends Enum<T>> boolean contains(String name, Class<T> clazz) {
    for (T e : EnumSet.allOf(clazz)) {
      if (e.name().equalsIgnoreCase(name)) {
        return true;
      }
    }

    return false;
  }

  public static <T extends Enum<T>> T fromString(String name, Class<T> clazz) {
    for (T e : EnumSet.allOf(clazz)) {
      if (e.name().equalsIgnoreCase(name)) {
        return e;
      }
    }
    return null;
  }
}
