package ca.bjad.foldersizes.delegate;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringReader;

import javax.swing.JOptionPane;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import ca.bjad.foldersizes.events.AppEventListener;
import ca.bjad.foldersizes.events.EventType;
import ca.bjad.foldersizes.events.ExportEventBean;
import ca.bjad.foldersizes.model.ExportDirectoryModel;
import ca.bjad.foldersizes.model.ExportFormatType;
import ca.bjad.foldersizes.model.ExportModel;
import ca.bjad.foldersizes.model.ExportOptionsModel;
import ca.bjad.foldersizes.ui.ExportOptionsDialog;

/**
 * Export delegate responsible for exporting the data shown in the 
 * app to a file
 *
 *
 * @author 
 *   Ben Dougall
 */
public class ExportDelegate implements AppEventListener
{
   /**
    * Exports the model to file format using the configuration passed 
    * to the method.
    * 
    * @param model
    *    The model to export. 
    * @param saveToTarget
    *    The location to save the file to.
    * @param format 
    *    The format to save the report in
    */
   public static void exportReport(ExportModel model, File saveToTarget, ExportFormatType format)   
   {
      switch (format)
      {
      case HTML:
         saveHTMLReport(model, saveToTarget);
         break;
      case CSV:
         saveCSVReport(model, saveToTarget);
         break;
      case XML:       
      default:
         exportToXML(model, saveToTarget);
         break;
      }
      
   }
   
   /**
    * Saves a CSV version of the report. 
    * @param model
    *    The model whose information will be exported to the CSV file
    * @param saveToTarget
    *    The file to save the CSV report to.
    */
   private static void saveCSVReport(ExportModel model, File saveToTarget)
   {
      try (PrintWriter pw = new PrintWriter(saveToTarget))
      {
         pw.println("Path,Size,DirCount,FileCount");
         for (ExportDirectoryModel dir : model.getDirectories())
         {
            pw.println(String.format("%s,%s,%s,%s",
                  dir.getPath(),
                  dir.getSize(),
                  dir.getTotalDirCount().toString(),
                  dir.getTotalFileCount().toString()));
         }
      }
      catch (Exception ex)
      {
         ex.printStackTrace();
      }
   }

   /**
    * @param model
    * @param saveToTarget
    */
   private static void exportToXML(ExportModel model, File saveToTarget)
   {
      try (PrintWriter pw = new PrintWriter(saveToTarget))
      {
         pw.println(model.toXMLString());
      } 
      catch (FileNotFoundException ex)
      {       
         ex.printStackTrace();
      }
   }

   private static void saveHTMLReport(ExportModel model, File saveToTarget)
   {       
      try (
            InputStream xsltis = ClassLoader.getSystemResourceAsStream("XMLtoHTML.xslt");
            StringReader xmlReader = new StringReader(model.toXMLString())
          )
      {
         TransformerFactory tFactory= TransformerFactory.newInstance();

         Source xslDoc=new StreamSource(xsltis);
         Source xmlDoc=new StreamSource(xmlReader);

         OutputStream htmlFile=new FileOutputStream(saveToTarget);
         Transformer trasform=tFactory.newTransformer(xslDoc);
         trasform.transform(xmlDoc, new StreamResult(htmlFile));
      } 
      catch (Exception ex)
      {
         ex.printStackTrace();
      }
   }

   @Override
   public void appEventOccurred(EventType eventType, Object source, Object eventBean)
   {
      if (EventType.EXPORT_RESULTS.equals(eventType) && eventBean != null && eventBean instanceof ExportEventBean)
      {
         ExportEventBean bean = (ExportEventBean)eventBean;
         
         // Show dialog to get the export options.
         ExportOptionsDialog dlg = new ExportOptionsDialog();
         dlg.setLocationRelativeTo(bean.getParent());
         dlg.setVisible(true);         
         ExportOptionsModel options = dlg.getOptions();
         
         if (options != null)
         {            
            ExportModel model = ExporrtModelDelegate.createExportModel(bean.getDirectoryInfo(), options);
            File saveTarget = new File(options.getSaveToPath());
            ExportDelegate.exportReport(model, saveTarget, options.getFormat());
            
            if (saveTarget.isFile())
            {
               String msg = String.format("%s has been saved.%sWould you like to navigate to the file?", 
                     saveTarget.getAbsolutePath(),
                     System.lineSeparator());
                  
               int choice = JOptionPane.showConfirmDialog(
                     dlg,
                     msg, 
                     "Report has been saved", 
                     JOptionPane.YES_NO_OPTION,
                     JOptionPane.QUESTION_MESSAGE);
               if (choice == JOptionPane.YES_OPTION)
               {
                  StartExplorerDelegate.showWindowsExplorer(saveTarget);
               }
            }
         }
      }
   }
}
