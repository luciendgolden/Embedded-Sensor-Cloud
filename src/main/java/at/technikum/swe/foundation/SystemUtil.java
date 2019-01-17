package at.technikum.swe.foundation;

public class SystemUtil {

  /**
   * get the file seperator
   */
  public final static String FILE_SEPERATOR = System.getProperty("file.separator");

  /**
   * get line seperator
   */
  public final static String LINE_SEPERATOR = System.getProperty("line.separator");

  /**
   * get the user directory absolute path
   */
  public final static String USER_DIR = System.getProperty("user.dir");

  /**
   * Get the static file folder path
   */
  // TODO - Fix /deploy for UnitTests
  public final static String STATIC_FOLDER_PATH = String
      .format("%s%sdeploy%stmp-static-files", USER_DIR, FILE_SEPERATOR, FILE_SEPERATOR);

}
