package ca.bjad.foldersizes.model;

import java.io.File;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

/**
 * Model object for the File information 
 * within the export report.
 *
 * @author 
 *   Ben Dougall
 */
@XmlRootElement(name = "file")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { 
   "path", 
   "fileSize",
   "lastModifiedDate",
   "extension",
   "sizeStr"})
public class ExportFileModel
{
   /**
    * The path of the directory this object is representing. 
    */
   protected String path;
  
   /**
    * The file size of the represented file.
    */
   protected long fileSize = 0L;
   
   /**
    * The modified date of the represented file.
    */
   @XmlTransient
   protected long modifiedDate = 0L;
   
   /**
    * The extension of the represented file.
    */
   protected String extension = "-";
   
   /**
    * The last modified date string to display on the report.
    */
   protected String lastModifiedDate = "";
   /**
    * The file size string to display on the report.
    */
   protected String sizeStr = "";

   /**
    * Default constructor
    */
   public ExportFileModel()
   {
      
   }
   
   /**
    * Contructor, builds the object from the directory
    * info object passed. 
    * 
    * @param file
    *    The file to extract the information from.
    */
   public ExportFileModel(File file)
   {
      this.setPath(file.getAbsolutePath());
      this.setFileSize(file.length());
      this.setModifiedDate(file.lastModified());
      
      int lastDotPos = file.getName().lastIndexOf('.');
      if (lastDotPos > -1)
      {
         this.setExtension(file.getName().substring(lastDotPos).toUpperCase());
      }
   }
   
   /**
    * Returns the value of the ExportDirectoryModel instance's 
    * path property.
    *
    * @return 
    *   The value of path
    */
   public String getPath()
   {
      return this.path;
   }
   
   /**
    * Sets the value of the ExportDirectoryModel instance's 
    * path property.
    *
    * @param path 
    *   The value to set within the instance's 
    *   path property
    */
   public void setPath(String path)
   {
      this.path = path;
   }

   /**
    * Returns the value of the ExportFileModel instance's 
    * fileSize property.
    *
    * @return 
    *   The value of fileSize
    */
   public long getFileSize()
   {
      return this.fileSize;
   }

   /**
    * Sets the value of the ExportFileModel instance's 
    * fileSize property.
    *
    * @param fileSize 
    *   The value to set within the instance's 
    *   fileSize property
    */
   public void setFileSize(long fileSize)
   {
      this.fileSize = fileSize;
   }

   /**
    * Returns the value of the ExportFileModel instance's 
    * modifiedDate property.
    *
    * @return 
    *   The value of modifiedDate
    */
   public long getModifiedDate()
   {
      return this.modifiedDate;
   }

   /**
    * Sets the value of the ExportFileModel instance's 
    * modifiedDate property.
    *
    * @param modifiedDate 
    *   The value to set within the instance's 
    *   modifiedDate property
    */
   public void setModifiedDate(long modifiedDate)
   {
      this.modifiedDate = modifiedDate;
   }

   /**
    * Returns the value of the ExportFileModel instance's 
    * extension property.
    *
    * @return 
    *   The value of extension
    */
   public String getExtension()
   {
      return this.extension;
   }
   
   /**
    * Sets the value of the ExportFileModel instance's 
    * extension property.
    *
    * @param extension 
    *   The value to set within the instance's 
    *   extension property
    */
   public void setExtension(String extension)
   {
      this.extension = extension;
   }

   /**
    * Returns the value of the ExportFileModel instance's 
    * lastModifiedDate property.
    *
    * @return 
    *   The value of lastModifiedDate
    */
   public String getLastModifiedDate()
   {
      return this.lastModifiedDate;
   }

   /**
    * Sets the value of the ExportFileModel instance's 
    * lastModifiedDate property.
    *
    * @param lastModifiedDate 
    *   The value to set within the instance's 
    *   lastModifiedDate property
    */
   public void setLastModifiedDate(String lastModifiedDate)
   {
      this.lastModifiedDate = lastModifiedDate;
   }

   /**
    * Returns the value of the ExportFileModel instance's 
    * size property.
    *
    * @return 
    *   The value of size
    */
   public String getSizeStr()
   {
      return this.sizeStr;
   }

   /**
    * Sets the value of the ExportFileModel instance's 
    * size property.
    *
    * @param size 
    *   The value to set within the instance's 
    *   size property
    */
   public void setSizeStr(String size)
   {
      this.sizeStr = size;
   }   
}
