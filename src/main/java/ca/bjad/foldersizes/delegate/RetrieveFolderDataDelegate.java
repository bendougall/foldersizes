package ca.bjad.foldersizes.delegate;

import java.io.File;
import java.math.BigInteger;

import ca.bjad.foldersizes.model.DirectoryInfo;

/**
 * Delegate used to retrieve directory and file information
 * for a directory to report the sizes of.
 *
 * @author 
 *   Ben Dougall
 */
public class RetrieveFolderDataDelegate
{
   /**
    * Retrieves the directory information for the folder passed. 
    * 
    * @param directoryToReportOn
    *    The folder to get the size information for. 
    * @return
    *    The directory information for the folder passed.
    */
   public DirectoryInfo getDirectoryInformation(File directoryToReportOn)
   {  
      return doDirectoryInformationDetermination(directoryToReportOn, null);
   }
   
   private DirectoryInfo doDirectoryInformationDetermination(File directoryToReportOn, DirectoryInfo parent)
   {
      DirectoryInfo directoryInformation = new DirectoryInfo();
      directoryInformation.setPath(directoryToReportOn.getAbsolutePath());
      directoryInformation.setParent(parent);
      
      for (File f : directoryToReportOn.listFiles())
      {
         // If the file is a file, we will add the file to the model which will
         // also add to the file size of the directory.
         if (f.isFile())
         {
            directoryInformation.addFileInfo(f);
         }
         // If the file is a directory, we need to recursively execute this 
         // function to get the sub directory information and add to the directory
         // data being built. 
         else if (f.isDirectory())
         {
            directoryInformation.getSubdirectories().add(doDirectoryInformationDetermination(f, directoryInformation));
            directoryInformation.setTotalDirCount(directoryInformation.getTotalDirCount().add(BigInteger.ONE));
            
            DirectoryInfo p = directoryInformation.getParent();
            while (p != null)
            {
               p.setTotalDirCount(p.getTotalDirCount().add(BigInteger.ONE));
               p = p.getParent();
            }
         }
      }
      
      return directoryInformation;
   }
}
