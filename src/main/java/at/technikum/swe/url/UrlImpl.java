package at.technikum.swe.url;

import BIF.SWE1.interfaces.Url;
import java.util.Arrays;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class UrlImpl implements Url {

  private Map<String, String> parameterMap;
  private String raw;

  public UrlImpl() {
  }

  public UrlImpl(String raw) {
    this.raw = raw;
  }

  @Override
  public String getPath() {
    if (this.raw != null) {
      if (raw.contains("?")) {
        return this.raw.split("[?]")[0];
      } else if (raw.contains("#")) {
        return this.raw.split("[#]")[0];
      }else {
        return this.raw;
      }
    }

    return "";
  }

  @Override
  public String getRawUrl() {
    return this.raw;
  }

  @Override
  public String getFileName() {
    String[] segments = getSegments();
    String lastSegment = segments[segments.length-1];

    if(isValidSupportedFile(lastSegment))
      return lastSegment;

    return "";
  }

  @Override
  public String getExtension() {
    String[] segments = getSegments();
    String lastSegment = segments[segments.length-1];

    if(lastSegment.contains("."))
      return lastSegment.split("[.]")[1];

    return "";
  }

  @Override
  public String getFragment() {
    if (this.raw.contains("#")) {
      return raw.split("[#]")[1];
    }
    return "";
  }

  @Override
  public Map<String, String> getParameter() {
    if (getParameters() != null) {
      this.parameterMap = Arrays.stream(getParameters())
          .map(elem -> elem.split("="))
          .collect(Collectors.toMap(key -> key[0], value -> value[1]));
    }

    return this.parameterMap;

  }

  @Override
  public int getParameterCount() {
    return getParameters() != null ? getParameters().length : 0;
  }

  private String[] getParameters() {

    if (this.raw != null) {
      if (this.raw.contains("?")) {
        String parameters = "";
        parameters = this.raw.split("[?]")[1];
        String[] parameterArr = parameters.split("[&]");

        return parameterArr;
      }
    }
    return null;
  }


  @Override
  public String[] getSegments() {
    return Arrays.stream(raw.substring(1).split("/"))
        .map(String::trim)
        .toArray(String[]::new);
  }


  public boolean isValidSupportedFile(String arg){
    Pattern pattern = Pattern.compile("^([0-9A-Za-zöäü\\-_])+.([0-9A-Za-zöäü\\-_])+$");

    Matcher matcher = pattern.matcher(arg);

    return matcher.matches();
  }

  public boolean isFile(){
    return !getFileName().isEmpty();
  }
}
