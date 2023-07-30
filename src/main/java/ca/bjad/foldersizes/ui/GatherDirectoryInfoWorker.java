package ca.bjad.foldersizes.ui;

import java.io.File;

import javax.swing.SwingWorker;

import ca.bjad.foldersizes.delegate.RetrieveFolderDataDelegate;
import ca.bjad.foldersizes.model.DirectoryInfo;

/**
 * Background worker that does the directory search 
 * for the directory and file information from the 
 * path provided to the worker.
 *
 * @author 
 *   Ben Dougall
 */
public class GatherDirectoryInfoWorker extends SwingWorker<DirectoryInfo, Integer>
{
   private File pathToSearch = null;
   private long minimumDuration = 275L;
   
   /**
    * Constructor, setting the path to search and the minimum duration 
    * for the worker to take in order to show the loading window without
    * it looking like a flicker to the user. 
    * 
    * @param path
    *    The directory to search within
    */
   public GatherDirectoryInfoWorker(File path)
   {
      this.pathToSearch = path;
   }

   @Override
   protected DirectoryInfo doInBackground() throws Exception
   {
      // Store the start time of the operation.
      long startTime = System.currentTimeMillis();
      
      // Do the search for the directory information.
      DirectoryInfo info = new RetrieveFolderDataDelegate().getDirectoryInformation(pathToSearch);
      
      // Sleep if the time taken is under the minimum duration setting
      // to avoid displays from flickering for small directories.
      long duration = System.currentTimeMillis() - startTime; 
      while (duration < minimumDuration)
      {
         try {Thread.sleep(50L); } catch (InterruptedException ex) {;}
         duration = System.currentTimeMillis() - startTime;
      }
      
      return info;
   } 
}
