package ca.bjad.foldersizes.events;

import java.io.File;

/**
 * The event bean for EventType.OPEN_IN_WINDOWS_EXPLORER
 * events, providing the file or direcotry to show in explorer. 
 *
 * @author 
 *   Ben Dougall
 */
public class FileDirectoryEventBean
{
   /**
    * The file or directory to open in explorer. 
    */
   protected File target;

   /**
    * Default constructor, setting the file/directory 
    * to null.
    */
   public FileDirectoryEventBean()
   {
      this(null);
   }
   
   /**
    * Constructor, setting the file/directory to open 
    * within the Explorer delegate.
    * 
    * @param target
    *    The file/directory to show.
    */
   public FileDirectoryEventBean(File target)
   {
      this.target = target;
   }

   /**
    * Returns the value of the ExplorerEventBean instance's 
    * target property.
    *
    * @return 
    *   The value of target
    */
   public File getTarget()
   {
      return this.target;
   }

   /**
    * 
    */
   /**
    * Sets the value of the ExplorerEventBean instance's 
    * target property.
    *
    * @param target 
    *   The value to set within the instance's 
    *   target property
    */
   public void setTarget(File target)
   {
      this.target = target;
   }
}
