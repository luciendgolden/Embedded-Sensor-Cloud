package at.technikum.swe.parser;

import at.technikum.swe.common.Configuration;
import at.technikum.swe.foundation.SystemUtil;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CSVParser {

  private final static Logger logger = Logger.getLogger(CSVParser.class.getName());

  private static Configuration configuration = null;

  private static final char DEFAULT_SEPARATOR = ',';
  private static final char DEFAULT_QUOTE = '"';


  private final Map<Long, String[]> data = new HashMap<>();
  private final AtomicLong counterId = new AtomicLong();

  private final String PATH_CSV_0 = configuration.getProperty("csv.weatherfile0");

  static {
    try {
      configuration = new Configuration().initialize();
    } catch (IOException e) {
      logger.log(Level.SEVERE, "Unexpected error " + e.getMessage(), e);
    }
  }

  public Map<Long, String[]> loadCSVAdvanced(int... columns) {
    //line Date: 9,10,11
    //line MAX: 12
    //line MIN: 14

    Scanner scanner = new Scanner(Configuration.class.getClassLoader()
        .getResourceAsStream(PATH_CSV_0));
    List<String> header = parseLine(scanner.nextLine());

    while (scanner.hasNext()) {
      String[] array = new String[columns.length];

      List<String> line = parseLine(scanner.nextLine());

      for (int i = 0; i < columns.length; i++) {
        array[i] = line.get(columns[i]);
      }

      data.put(counterId.getAndIncrement(), array);
    }
    scanner.close();

    return data;
  }

  public static List<String> parseLine(String cvsLine) {
    return parseLine(cvsLine, DEFAULT_SEPARATOR, DEFAULT_QUOTE);
  }

  public static List<String> parseLine(String cvsLine, char separators) {
    return parseLine(cvsLine, separators, DEFAULT_QUOTE);
  }

  public static List<String> parseLine(String cvsLine, char separators, char customQuote) {

    List<String> result = new ArrayList<>();

    //if empty, return!
    if (cvsLine == null && cvsLine.isEmpty()) {
      return result;
    }

    if (customQuote == ' ') {
      customQuote = DEFAULT_QUOTE;
    }

    if (separators == ' ') {
      separators = DEFAULT_SEPARATOR;
    }

    StringBuffer curVal = new StringBuffer();
    boolean inQuotes = false;
    boolean startCollectChar = false;
    boolean doubleQuotesInColumn = false;

    char[] chars = cvsLine.toCharArray();

    for (char ch : chars) {

      if (inQuotes) {
        startCollectChar = true;
        if (ch == customQuote) {
          inQuotes = false;
          doubleQuotesInColumn = false;
        } else {

          //Fixed : allow "" in custom quote enclosed
          if (ch == '\"') {
            if (!doubleQuotesInColumn) {
              curVal.append(ch);
              doubleQuotesInColumn = true;
            }
          } else {
            curVal.append(ch);
          }

        }
      } else {
        if (ch == customQuote) {

          inQuotes = true;

          //Fixed : allow "" in empty quote enclosed
          if (chars[0] != '"' && customQuote == '\"') {
            curVal.append('"');
          }

          //double quotes in column will hit this!
          if (startCollectChar) {
            curVal.append('"');
          }

        } else if (ch == separators) {

          result.add(curVal.toString());

          curVal = new StringBuffer();
          startCollectChar = false;

        } else if (ch == '\r') {
          //ignore LF characters
          continue;
        } else if (ch == '\n') {
          //the end, break!
          break;
        } else {
          curVal.append(ch);
        }
      }

    }

    result.add(curVal.toString());

    return result;
  }


}
