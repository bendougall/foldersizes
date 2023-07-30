package ca.bjad.foldersizes.model;

/**
 * The various formats the application can export to. 
 *
 * @author 
 *   Ben Dougall
 */
public enum ExportFormatType
{
   /**
    * The XML format option. 
    */
   XML(".xml", "XML Document"),
   /**
    * The web page format option.
    */
   HTML(".html", "Web Page"),
   /**
    * The web page format option.
    */
   CSV(".csv", "CSV of all folders");
   
   private String extension; 
   private String displayText; 
   
   private ExportFormatType(String extension, String displayText)
   {
      this.extension = extension;
      this.displayText = displayText;
   }

   /**
    * Returns the value of the ExportFormatType instance's 
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
    * Returns the value of the ExportFormatType instance's 
    * displayText property.
    *
    * @return 
    *   The value of displayText
    */
   public String getDisplayText()
   {
      return this.displayText;
   }
   
   public String toString()
   {
      return String.format("%s (%s)", this.displayText, this.extension);
   }
}
