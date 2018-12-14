import static at.technikum.swe.foundation.SystemUtil.LINE_SEPERATOR;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import at.technikum.swe.foundation.SystemUtil;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import org.junit.Test;

public class TestCase {

  @Test
  public void myTest() {
    File file = new File(
        "/Users/alexander/git/java/EmbeddedSensorCloud/SWE1-Java/deploy/tmp-static-files/theme.html");

    boolean exists = file.exists();      // Check if the file exists
    boolean isDirectory = file.isDirectory(); // Check if it's a directory
    boolean isFile = file.isFile();      // Check if it's a regular file

    System.out.println("Exists: " + exists);
    System.out.println("IsDirectory: " + isDirectory);
    System.out.println("IsFile"
        + ": " + isFile);
  }

  @Test
  public void webAppTest() {
    ClassLoader classLoader = getClass().getClassLoader();
    InputStream in = classLoader.getResourceAsStream("WEB-INF/index.html");
    URL url = classLoader.getResource("WEB-INF/");

    final StringBuilder path = new StringBuilder();

    path.append(SystemUtil.USER_DIR);
    path.append(String.format("%ssrc%smain%swebapp%sWEB-INF%s404.html", SystemUtil.FILE_SEPERATOR,
        SystemUtil.FILE_SEPERATOR, SystemUtil.FILE_SEPERATOR, SystemUtil.FILE_SEPERATOR,
        SystemUtil.FILE_SEPERATOR));

    System.out.println(url.toString());
  }

  @Test
  public void testBody() throws IOException {
    String body = null;

    ByteArrayOutputStream ms = new ByteArrayOutputStream();
    PrintWriter sw = new PrintWriter(new OutputStreamWriter(ms,
        StandardCharsets.US_ASCII));
    sw.printf("{\n"
        + "    \"value\":\"helloWorld\"\n"
        + "}");
    sw.flush();
    InputStream input = new ByteArrayInputStream(ms.toByteArray());
    BufferedReader in = new BufferedReader(new InputStreamReader(input));

    if (in != null) {
      String line = null;
      StringBuilder finalstring = new StringBuilder();

      while ((line = in.readLine()) != null) {
        finalstring.append(line);
        finalstring.append(LINE_SEPERATOR);
      }

      body = finalstring.toString();
    }

    String[] jsonParts = body.split("[\\n]");
    String[] keyValue = jsonParts[1].split("[:]");
    String value = keyValue[1];
    String message = value.substring(1, value.length() - 1);
    String toLowerMessage = message.toLowerCase();

    assertEquals(toLowerMessage, "helloworld");
  }
}
