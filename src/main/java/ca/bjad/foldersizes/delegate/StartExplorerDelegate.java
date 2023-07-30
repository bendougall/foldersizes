package ca.bjad.foldersizes.delegate;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ca.bjad.foldersizes.events.AppEventListener;
import ca.bjad.foldersizes.events.EventType;
import ca.bjad.foldersizes.events.FileDirectoryEventBean;

/**
 * Delegate class used to start the windows explorer
 * process to the specific file or folder.
 *
 * @author 
 *   Ben Dougall
 */
public class StartExplorerDelegate implements AppEventListener
{
   private static final String SELECTION_MARKER = "<selection>";
   
   private static final String[] SHOW_FILE_COMMAND_PARTS = new String[] 
         {
               "cmd",
               "/c",
               "start",
               "explorer",
               "/select,\"" + SELECTION_MARKER + "\""
         };
   private static final String[] SHOW_DIR_COMMAND_PARTS = new String[] 
         {
               "cmd",
               "/c",
               "start",
               "explorer",
               SELECTION_MARKER
         };
   
   /**
    * Starts the explorer process to the file or directory passed 
    * to the function. 
    * 
    * @param selectedFile
    *    The file/directory to show in a new Windows Explorer 
    *    window to the user.
    */
   public static void showWindowsExplorer(File selectedFile)
   {
      List<String> commandParts = new ArrayList<String>();
      String[] partsToUse = selectedFile.isFile() ? SHOW_FILE_COMMAND_PARTS : SHOW_DIR_COMMAND_PARTS;
      
      for (String part : partsToUse)
      {
         commandParts.add(part.replace(SELECTION_MARKER, selectedFile.getAbsolutePath()));
      }
      
      ProcessBuilder pb = new ProcessBuilder(commandParts);
      try
      {
         pb.start();
      } 
      catch (IOException ex)
      {
        
         ex.printStackTrace();
      }
   }

   @Override
   public void appEventOccurred(EventType eventType, Object source, Object eventBean)
   {
      if (EventType.OPEN_IN_WINDOWS_EXPLORER.equals(eventType))
      {
         if (eventBean != null && eventBean instanceof FileDirectoryEventBean)
         {
            FileDirectoryEventBean bean = (FileDirectoryEventBean)eventBean;
            if (bean.getTarget() != null)
            {
               showWindowsExplorer(bean.getTarget());
            }
         }
      }
   }
}
