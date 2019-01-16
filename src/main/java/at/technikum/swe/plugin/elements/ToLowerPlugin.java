package at.technikum.swe.plugin.elements;

import static at.technikum.swe.common.Status.OK;
import static at.technikum.swe.foundation.SystemUtil.STATIC_FOLDER_PATH;

import BIF.SWE1.interfaces.Plugin;
import BIF.SWE1.interfaces.Request;
import BIF.SWE1.interfaces.Response;
import at.technikum.swe.common.ContentType;
import at.technikum.swe.foundation.PluginUtil;
import at.technikum.swe.response.ResponseImpl;
import com.sun.org.apache.bcel.internal.generic.SIPUSH;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

public class ToLowerPlugin implements Plugin {

  @Override
  public float canHandle(Request request) {
    float handleable = PluginUtil.getTheProbability(ToLowerPlugin.class, request);
    final String path = request.getUrl().getPath();

    return handleable;
  }

  @Override
  public Response handle(Request request) {
    final ResponseImpl res = new ResponseImpl();
    final String inputString = request.getContentString();
    String result = "value=N/A";
    try {
      result = URLDecoder.decode(inputString, "UTF-8");
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }

    String[] bodyKeyValue = result.split("=");

    res.setStatusCode(200);
    res.setContentType("text/plain");
    res.setContent(bodyKeyValue[1].toLowerCase());

    return res;
  }

  @Override
  public String toString() {
    return "ToLowerPlugin{}";
  }
}
