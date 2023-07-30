package ca.bjad.foldersizes.events;

import java.awt.Component;

import ca.bjad.foldersizes.model.DirectoryInfo;

/**
 * Bean used for when the user chooses to export
 * the results of the directory search.
 *
 * @author 
 *   Ben Dougall
 */
public class ExportEventBean
{
   private DirectoryInfo directoryInfo;
   private Component parent;

   /**
    * @param directoryInfo
    *    The directory info object generated by the search.
    * @param parent
    *    The parent component for the export options dialog. 
    */
   public ExportEventBean(DirectoryInfo directoryInfo, Component parent)
   {
      this.directoryInfo = directoryInfo;
      this.parent = parent;
   }

   /**
    * Returns the value of the ExportEventBean instance's 
    * directoryInfo property.
    *
    * @return 
    *   The value of directoryInfo
    */
   public DirectoryInfo getDirectoryInfo()
   {
      return this.directoryInfo;
   }

   /**
    * Sets the value of the ExportEventBean instance's 
    * directoryInfo property.
    *
    * @param directoryInfo 
    *   The value to set within the instance's 
    *   directoryInfo property
    */
   public void setDirectoryInfo(DirectoryInfo directoryInfo)
   {
      this.directoryInfo = directoryInfo;
   }

   /**
    * Returns the value of the ExportEventBean instance's 
    * parent property.
    *
    * @return 
    *   The value of parent
    */
   public Component getParent()
   {
      return this.parent;
   }

   /**
    * Sets the value of the ExportEventBean instance's 
    * parent property.
    *
    * @param parent 
    *   The value to set within the instance's 
    *   parent property
    */
   public void setParent(Component parent)
   {
      this.parent = parent;
   }
   
}
