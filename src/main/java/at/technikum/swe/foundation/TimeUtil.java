package at.technikum.swe.foundation;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;
import java.util.logging.Logger;

public class TimeUtil {

  private final static Logger logger = Logger.getLogger(TimeUtil.class.getName());

  public static String getTime() {
    Calendar calendar = Calendar.getInstance();
    SimpleDateFormat dateFormat = new SimpleDateFormat(
        "EEE, dd MMM yyyy HH:mm:ss z", Locale.GERMANY);
    dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
    return dateFormat.format(calendar.getTime());
  }

  public static boolean checkDate(String... args) {
    boolean isValidDate = false;
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

    // Input to be parsed should strictly follow the defined date format
    // above.
    format.setLenient(false);

    StringBuilder builder = new StringBuilder();
    builder.append(args[0]);
    builder.append("-");
    builder.append(args[1]);
    builder.append("-");
    builder.append(args[2]);

    try {
      format.parse(builder.toString());
      isValidDate = true;
    } catch (ParseException e) {
      logger.info(String.format("Date %s isn't valid according to %s pattern", builder.toString(),
          format.toPattern()));
    }

    return isValidDate;

  }
}
