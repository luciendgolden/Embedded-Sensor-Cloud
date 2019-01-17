package at.technikum.swe.xml;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;

public class XMLSerealizer {

  public InputStream toXML(Document document)
      throws ParserConfigurationException, TransformerException {
    // create the xml file
    //transform the DOM Object to an inputstream
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    Source domSource = new DOMSource(document);
    Result outputTarget = new StreamResult(outputStream);
    TransformerFactory.newInstance().newTransformer().transform(domSource, outputTarget);
    return new ByteArrayInputStream(outputStream.toByteArray());

  }


}
