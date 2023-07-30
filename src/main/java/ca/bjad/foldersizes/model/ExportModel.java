package ca.bjad.foldersizes.model;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

/**
 * The model object used for the export options to contain the 
 * initial summary information as well as the individual file and 
 * directory information.
 *
 * @author 
 *   Ben Dougall
 */
@XmlRootElement(name = "exportData")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { 
   "dateOfReport", 
   "numberOfLargestEntries",
   "directoryForResult",
   "largestDirectories",
   "largestFiles",
   "largestExtensions",
   "directories"})
public class ExportModel
{
   private String dateOfReport = "";   
   private ExportDirectoryModel directoryForResult;
   private List<ExportDirectoryModel> largestDirectories = new ArrayList<ExportDirectoryModel>();
   private List<ExportFileModel> largestFiles = new ArrayList<ExportFileModel>();
   private List<ExportExtensionModel> largestExtensions = new ArrayList<ExportExtensionModel>();
   private List<ExportDirectoryModel> directories = new ArrayList<ExportDirectoryModel>();
   private int numberOfLargestEntries = 10;
   @XmlTransient
   private ExportDateFormat dateFormat = ExportDateFormat.TIMESTAMP;
   
   
   /**
    * Returns the value of the ExportModel instance's 
    * dateOfReport property.
    *
    * @return 
    *   The value of dateOfReport
    */
   public String getDateOfReport()
   {
      return this.dateOfReport;
   }
   
   /**
    * Sets the value of the ExportModel instance's 
    * dateOfReport property.
    *
    * @param dateOfReport 
    *   The value to set within the instance's 
    *   dateOfReport property
    */
   public void setDateOfReport(String dateOfReport)
   {
      this.dateOfReport = dateOfReport;
   }
   /**
    * Returns the value of the ExportModel instance's 
    * directoryForResult property.
    *
    * @return 
    *   The value of directoryForResult
    */
   public ExportDirectoryModel getDirectoryForResult()
   {
      return this.directoryForResult;
   }
   
   /**
    * Sets the value of the ExportModel instance's 
    * directoryForResult property.
    *
    * @param directoryForResult 
    *   The value to set within the instance's 
    *   directoryForResult property
    */
   public void setDirectoryForResult(ExportDirectoryModel directoryForResult)
   {
      this.directoryForResult = directoryForResult;
   }
   /**
    * Returns the value of the ExportModel instance's 
    * largestDirectories property.
    *
    * @return 
    *   The value of largestDirectories
    */
   public List<ExportDirectoryModel> getLargestDirectories()
   {
      return this.largestDirectories;
   }
   
   /**
    * Sets the value of the ExportModel instance's 
    * largestDirectories property.
    *
    * @param largestDirectories 
    *   The value to set within the instance's 
    *   largestDirectories property
    */
   public void setLargestDirectories(List<ExportDirectoryModel> largestDirectories)
   {
      this.largestDirectories = largestDirectories;
   }
   /**
    * Returns the value of the ExportModel instance's 
    * largestFiles property.
    *
    * @return 
    *   The value of largestFiles
    */
   public List<ExportFileModel> getLargestFiles()
   {
      return this.largestFiles;
   }
   
   /**
    * Sets the value of the ExportModel instance's 
    * largestFiles property.
    *
    * @param largestFiles 
    *   The value to set within the instance's 
    *   largestFiles property
    */
   public void setLargestFiles(List<ExportFileModel> largestFiles)
   {
      this.largestFiles = largestFiles;
   }
   /**
    * Returns the value of the ExportModel instance's 
    * largestExtensions property.
    *
    * @return 
    *   The value of largestExtensions
    */
   public List<ExportExtensionModel> getLargestExtensions()
   {
      return this.largestExtensions;
   }
   
   /**
    * Sets the value of the ExportModel instance's 
    * largestExtensions property.
    *
    * @param largestExtensions 
    *   The value to set within the instance's 
    *   largestExtensions property
    */
   public void setLargestExtensions(List<ExportExtensionModel> largestExtensions)
   {
      this.largestExtensions = largestExtensions;
   }
   
   /**
    * Returns the value of the ExportModel instance's 
    * dateFormat property.
    *
    * @return 
    *   The value of dateFormat
    */
   public ExportDateFormat getDateFormat()
   {
      return this.dateFormat;
   }

   /**
    * Sets the value of the ExportModel instance's 
    * dateFormat property.
    *
    * @param dateFormat 
    *   The value to set within the instance's 
    *   dateFormat property
    */
   public void setDateFormat(ExportDateFormat dateFormat)
   {
      this.dateFormat = dateFormat;
   }

   /**
    * Returns the value of the ExportModel instance's 
    * numberOfLargestEntries property.
    *
    * @return 
    *   The value of numberOfLargestEntries
    */
   public int getNumberOfLargestEntries()
   {
      return this.numberOfLargestEntries;
   }

   /**
    * Sets the value of the ExportModel instance's 
    * numberOfLargestEntries property.
    *
    * @param numberOfLargestEntries 
    *   The value to set within the instance's 
    *   numberOfLargestEntries property
    */
   public void setNumberOfLargestEntries(int numberOfLargestEntries)
   {
      this.numberOfLargestEntries = numberOfLargestEntries;
   }

   /**
    * Returns the value of the ExportModel instance's 
    * directories property.
    *
    * @return 
    *   The value of directories
    */
   public List<ExportDirectoryModel> getDirectories()
   {
      return this.directories;
   }

   /**
    * Sets the value of the ExportModel instance's 
    * directories property.
    *
    * @param directories 
    *   The value to set within the instance's 
    *   directories property
    */
   public void setDirectories(List<ExportDirectoryModel> directories)
   {
      this.directories = directories;
   }

   /**
    * Returns the XML string for the export data contained in this 
    * export model. 
    * 
    * @return
    *    The XML String of the data in the export model.
    */
   public String toXMLString()
   {
      String result = "";
      try
      {
         // Create the JAXB context and marshaller for this class, and
         // set its formatted output property to true.
         JAXBContext context = JAXBContext.newInstance(this.getClass());
         Marshaller m = context.createMarshaller();
         m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

         // Create a depot for the XML content to be written to by the 
         // marshaller, and marshall the current data and return the 
         // string retrieved from the depot the marshaller saved it too.
         StringWriter stringWriter = new StringWriter();
         m.marshal(this, stringWriter);
         result = stringWriter.toString();
      }
      catch (Exception ex) { ex.printStackTrace(); }
      
      return result;
   }
}
