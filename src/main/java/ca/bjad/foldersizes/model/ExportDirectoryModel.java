package ca.bjad.foldersizes.model;

import java.math.BigInteger;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Model object for the directory information 
 * within the export report.
 *
 *
 * @author 
 *   bendo
 */
@XmlRootElement(name = "directory")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { 
   "path", 
   "totalSize",
   "totalDirCount",
   "totalFileCount",
   "size"})
public class ExportDirectoryModel
{
   /**
    * The path of the directory this object is representing. 
    */
   protected String path;
   
   /**
    * The total size of the files within the folder (including those
    * in sub directories).
    */
   protected BigInteger totalSize = BigInteger.ZERO;
   
   /**
    * The total directory count within the folder (including those
    * in sub directories).
    */
   protected BigInteger totalDirCount = BigInteger.ZERO;
   
   /**
    * The total file count within the folder (including those
    * in sub directories).
    */
   protected BigInteger totalFileCount = BigInteger.ZERO;
   /**
    * The file size string to display on the report.
    */
   protected String size = "";

   /**
    * Default constructor
    */
   public ExportDirectoryModel()
   {
      
   }
   
   /**
    * Contructor, builds the object from the directory
    * info object passed. 
    * 
    * @param di
    *    The directory information to extract the information 
    *    from. 
    */
   public ExportDirectoryModel(DirectoryInfo di)
   {
      this.setPath(di.getPath());
      this.setTotalDirCount(di.getTotalDirCount());
      this.setTotalFileCount(di.getTotalFileCount());
      this.setTotalSize(di.getTotalSize());
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
    * Returns the value of the ExportDirectoryModel instance's 
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
    * Sets the value of the ExportDirectoryModel instance's 
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
    * Returns the value of the ExportDirectoryModel instance's 
    * totalDirCount property.
    *
    * @return 
    *   The value of totalDirCount
    */
   public BigInteger getTotalDirCount()
   {
      return this.totalDirCount;
   }

   /**
    * Sets the value of the ExportDirectoryModel instance's 
    * totalDirCount property.
    *
    * @param totalDirCount 
    *   The value to set within the instance's 
    *   totalDirCount property
    */
   public void setTotalDirCount(BigInteger totalDirCount)
   {
      this.totalDirCount = totalDirCount;
   }

   /**
    * Returns the value of the ExportDirectoryModel instance's 
    * totalFileCount property.
    *
    * @return 
    *   The value of totalFileCount
    */
   public BigInteger getTotalFileCount()
   {
      return this.totalFileCount;
   }

   /**
    * Sets the value of the ExportDirectoryModel instance's 
    * totalFileCount property.
    *
    * @param totalFileCount 
    *   The value to set within the instance's 
    *   totalFileCount property
    */
   public void setTotalFileCount(BigInteger totalFileCount)
   {
      this.totalFileCount = totalFileCount;
   }

   /**
    * Returns the value of the ExportDirectoryModel instance's 
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
    * Sets the value of the ExportDirectoryModel instance's 
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
