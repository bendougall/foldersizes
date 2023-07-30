package ca.bjad.foldersizes.events;

/**
 * Enumeration of the app wide events that can be triggered
 * by the various elements within the application.
 *
 * @author 
 *   Ben Dougall
 */
public enum EventType
{
   /**
    * Event fired within the app when the directory
    * table's selection is updated.
    */
   DIRECTORY_TABLE_SELECTION_CHANGED,
   /**
    * Event fired within the app when the file
    * table's selection is updated.
    */
   FILE_TABLE_SELECTION_CHANGED,
   /**
    * Event when the user wants to open a
    * selected file/directory in windows explorer.
    */
   OPEN_IN_WINDOWS_EXPLORER,
   /**
    * Event fired within the app when the user
    * chooses to search for directory information
    * from a selected directory.
    */
   SEARCH_DIRECTORY,
   /**
    * Event fired when the user wants to export data
    * to a file.
    */
   EXPORT_RESULTS
}
