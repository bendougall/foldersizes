package ca.bjad.foldersizes.delegate;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.junit.jupiter.api.Test;

/**
 * (Description)
 *
 *
 * @author 
 *   Ben Dougall
 */
class ExportTest
{

   @Test
   void test()
   {
      try (
            InputStream xsltis = ClassLoader.getSystemResourceAsStream("XMLtoHTML.xslt");
            InputStream xmlis = ClassLoader.getSystemResourceAsStream("TestXML.xml");
          )
      {
         TransformerFactory tFactory= TransformerFactory.newInstance();

         Source xslDoc=new StreamSource(xsltis);
         Source xmlDoc=new StreamSource(xmlis);

         assertNotNull(xslDoc);
         
         OutputStream htmlFile=new FileOutputStream("C:\\temp\\FolderSizes.html");
         Transformer trasform=tFactory.newTransformer(xslDoc);
         trasform.transform(xmlDoc, new StreamResult(htmlFile));
      } 
      catch (Exception ex)
      {
         ex.printStackTrace();
      }
   }

}
