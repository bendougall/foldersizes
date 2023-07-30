package ca.bjad.foldersizes.model;

/**
 * The various formats available to display file sizes
 * on reports. 
 *
 * @author 
 *   Ben Dougall
 */
public enum ExportSizeFormatType
{
   /**
    * Option for no formatting.
    */
   PLAIN, 
   /**
    * Option for formatting with thousands 
    * seperator
    */
   WITH_SEPERATORS,
   /**
    * Option for friendly string (1.111 KB).
    */
   FRIENDLY_STRING
}
