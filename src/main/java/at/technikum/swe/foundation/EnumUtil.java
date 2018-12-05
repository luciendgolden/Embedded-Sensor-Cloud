package at.technikum.swe.foundation;

import java.util.EnumSet;

public class EnumUtil {

  /**
   * Search for a specific Type of an Enum
   *
   * @param clazz - type of the Enum which should be searched
   * @param name - String to compare
   * @param <T> - Type of the Enum
   * @return boolean - if the Enum contains the given value
   */
  public static <T extends Enum<T>> boolean contains(Class<T> clazz, String name) {
    for (T e : EnumSet.allOf(clazz)) {
      if (e.name().equals(name)) {
        return true;
      }
    }

    return false;
  }

  public static <T extends Enum<T>> T getContainingEnumType(Class<T> clazz, String name) {
    for (T e : EnumSet.allOf(clazz)) {
      if (e.name().contains(name.toUpperCase())) {
        return e;
      }
    }

    return null;
  }

  /**
   * Search for
   * @param clazz
   * @param search
   * @param myEnumType
   * @param <T>
   * @return
   */
  public static <T extends Enum<T>> boolean isEqualTo(Class<T> clazz, String search,
      T myEnumType) {
    return myEnumType.name().equals(search);
  }
}
