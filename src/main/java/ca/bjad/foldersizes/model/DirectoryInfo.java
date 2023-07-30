package ca.bjad.foldersizes.model;

import java.io.File;
import java.math.BigInteger;
import java.util.LinkedList;
import java.util.List;

/**
 * Model for the directories examined by the application, storing
 * file count, total size, and sub directory information. 
 *
 * @author 
 *   Ben Dougall
 */
public class DirectoryInfo
{
   @SuppressWarnings("unused")
   private static final long serialVersionUID = 5532322187572919924L;
   
   /**
    * The path of the directory this object is representing. 
    */
   protected String path;
   
   /**
    * The list of files (not directories) within the folder.
    */
   protected List<File> files;
   
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
    * The list of subdirectories within the current directory
    */
   protected List<DirectoryInfo> subdirectories;
   
   /**
    * The parent directory info for the directory the object represents.
    */
   protected DirectoryInfo parent;

   /**
    * Returns the value of the DirectoryInfo instance's 
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
    * 
    */
   /**
    * Sets the value of the DirectoryInfo instance's 
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
    * Returns the value of the DirectoryInfo instance's 
    * files property.
    *
    * @return 
    *   The value of files
    */
   public List<File> getFiles()
   {
      if (this.files == null)
      {
         this.files = new LinkedList<>();
      }
      return this.files;
   }

   /**
    * 
    */
   /**
    * Sets the value of the DirectoryInfo instance's 
    * files property.
    *
    * @param files 
    *   The value to set within the instance's 
    *   files property
    */
   public void setFiles(List<File> files)
   {
      this.files = files;
   }

   /**
    * Returns the value of the DirectoryInfo instance's 
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
    * 
    */
   /**
    * Sets the value of the DirectoryInfo instance's 
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
    * Returns the value of the DirectoryInfo instance's 
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
    * 
    */
   /**
    * Sets the value of the DirectoryInfo instance's 
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
    * Returns the value of the DirectoryInfo instance's 
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
    * 
    */
   /**
    * Sets the value of the DirectoryInfo instance's 
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
    * Returns the value of the DirectoryInfo instance's 
    * subdirectories property.
    *
    * @return 
    *   The value of subdirectories
    */
   public List<DirectoryInfo> getSubdirectories()
   {
      if (this.subdirectories == null)
      {
         this.subdirectories = new LinkedList<>();
      }
      return this.subdirectories;
   }

   /**
    * 
    */
   /**
    * Sets the value of the DirectoryInfo instance's 
    * subdirectories property.
    *
    * @param subdirectories 
    *   The value to set within the instance's 
    *   subdirectories property
    */
   public void setSubdirectories(List<DirectoryInfo> subdirectories)
   {
      this.subdirectories = subdirectories;
   }
   
   /**
    * Returns the value of the DirectoryInfo instance's 
    * parent property.
    *
    * @return 
    *   The value of parent
    */
   public DirectoryInfo getParent()
   {
      return this.parent;
   }

   /**
    * 
    */
   /**
    * Sets the value of the DirectoryInfo instance's 
    * parent property.
    *
    * @param parent 
    *   The value to set within the instance's 
    *   parent property
    */
   public void setParent(DirectoryInfo parent)
   {
      this.parent = parent;
   }

   /**
    * Adds the file information to the model object, including the
    * total file size of the current folder, and adding to the total
    * of the parent directories.
    *  
    * @param f
    *    The file to add to the model. 
    */
   public void addFileInfo(File f)
   {
      this.totalSize = this.totalSize.add(new BigInteger(String.valueOf(f.length())));
      this.totalFileCount = this.totalFileCount.add(BigInteger.ONE);
      this.getFiles().add(f);
      
      DirectoryInfo p = getParent();
      while (p != null)
      {
         p.totalSize = p.totalSize.add(new BigInteger(String.valueOf(f.length())));
         p.totalFileCount = p.totalFileCount.add(BigInteger.ONE);
         p = p.getParent();
      }
   }
   
   public String toString()
   {
      return String.format("%s : Size:%s FC:%s DC:%s", getPath(), getTotalSize(), getTotalFileCount(), getTotalDirCount());
   }
}
