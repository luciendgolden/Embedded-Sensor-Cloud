package at.technikum.swe.foundation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public final class Lists {

  public static <T> ArrayList<T> toList(T... args){
    final ArrayList<T> list = new ArrayList<>();

    for(T arg: args){
      list.add(arg);
    }

    return list;
  }

  public static <T> List<T> immutableList(List<T> list) {
    return Collections.unmodifiableList(new ArrayList<>(list));
  }

  public static <T> List<T> immutableList(T... elements) {
    return immutableList(Arrays.asList(elements));
  }
}
