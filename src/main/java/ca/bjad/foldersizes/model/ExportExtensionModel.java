package ca.bjad.foldersizes.model;

import java.math.BigInteger;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Bean representing extension information.
 *
 * @author 
 *   Ben Dougall
 */
@XmlRootElement(name = "fileExtension")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { 
   "extension", 
   "totalSize",
   "fileCount",
   "size"})
public class ExportExtensionModel
{
   /**
    * The extension the model represents 
    */
   protected String extension = "-";
   /**
    * The total size the files with the extension takes up
    */
   protected BigInteger totalSize = BigInteger.ZERO;
   /**
    * The total number of files with the matching extension.
    */
   protected long fileCount = 0;
   /**
    * The size as a string to match the formatting option selected by the user.
    */
   protected String size= "";
   
   /**
    * Default constructor
    */
   public ExportExtensionModel()
   {
   }
   
   /**
    * Constructor, setting the properties.
    *  
    * @param fileModel
    *    The file model to get the extension and filesiaze from.
    */
   public ExportExtensionModel(ExportFileModel fileModel)
   {
      this.extension = fileModel.getExtension();
      this.totalSize = new BigInteger(String.valueOf(fileModel.getFileSize()));
      this.fileCount = 1;
   }
   
   /**
    * Returns the value of the ExportExtensionModel instance's 
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
    * Sets the value of the ExportExtensionModel instance's 
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
    * Returns the value of the ExportExtensionModel instance's 
    * totalSize property.
    *
    * @return 
    *   The value of totalSize
    */
   public BigInteger getTotalSize()
   {
      return this.totalSize;
   }
   /**
    * Sets the value of the ExportExtensionModel instance's 
    * totalSize property.
    *
    * @param totalSize 
    *   The value to set within the instance's 
    *   totalSize property
    */
   public void setTotalSize(BigInteger totalSize)
   {
      this.totalSize = totalSize;
   }
   
   /**
    * Returns the value of the ExportExtensionModel instance's 
    * fileCount property.
    *
    * @return 
    *   The value of fileCount
    */
   public long getFileCount()
   {
      return this.fileCount;
   }

   /**
    * Sets the value of the ExportExtensionModel instance's 
    * fileCount property.
    *
    * @param fileCount 
    *   The value to set within the instance's 
    *   fileCount property
    */
   public void setFileCount(long fileCount)
   {
      this.fileCount = fileCount;
   }

   /**
    * Adds to the size of being held in the model.
    * @param fileModel
    *    The file model to get the size from.
    */
   public void addToTotalSize(ExportFileModel fileModel)
   {
      this.totalSize = this.totalSize.add(new BigInteger(String.valueOf(fileModel.getFileSize())));
      this.fileCount++;
   }

   /**
    * Returns the value of the ExportExtensionModel instance's 
    * size property.
    *
    * @return 
    *   The value of size
    */
   public String getSize()
   {
      return this.size;
   }

   /**
    * Sets the value of the ExportExtensionModel instance's 
    * size property.
    *
    * @param size 
    *   The value to set within the instance's 
    *   size property
    */
   public void setSize(String size)
   {
      this.size = size;
   }
}
