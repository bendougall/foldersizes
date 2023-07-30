package ca.bjad.foldersizes.model;

/**
 * Enumeration of the various date formats
 * available for use within the exported 
 * results. 
 *
 * @author 
 *   Ben Dougall
 */
public enum ExportDateFormat
{
   /**
    * Month day year format without time
    */
   MONTH_DAY_YEAR("MM/dd/YYYY", "Month/Day/Year"),
   /**
    * Month day year format with time.
    */
   MONTH_DAY_YEAR_HOUR_MM_AMPM("MM/dd/YYYY hh:mm a", "Month/Day/Year with Time"),
   /**
    * Timestamp format with formatting
    */
   TIMESTAMP("yyyy/MM/dd HH:mm:ss", "Timestamp"),
   /**
    * Timestamp format without formatting.
    */
   TIMESTAMP_NO_SPACE("yyyyMMddHHmmss", "Timestamp w/o Spaces");
   
   private String formatString;
   private String displayString;
   
   private ExportDateFormat(String formatString, String displayString)
   {
      this.displayString = displayString;
      this.formatString = formatString;
   }

   /**
    * Returns the value of the ExportDateFormat instance's 
    * formatString property.
    *
    * @return 
    *   The value of formatString
    */
   public String getFormatString()
   {
      return this.formatString;
   }

   /**
    * 
    */
   /**
    * Sets the value of the ExportDateFormat instance's 
    * formatString property.
    *
    * @param formatString 
    *   The value to set within the instance's 
    *   formatString property
    */
   public void setFormatString(String formatString)
   {
      this.formatString = formatString;
   }

   /**
    * Returns the value of the ExportDateFormat instance's 
    * displayString property.
    *
    * @return 
    *   The value of displayString
    */
   public String getDisplayString()
   {
      return this.displayString;
   }

   /**
    * 
    */
   /**
    * Sets the value of the ExportDateFormat instance's 
    * displayString property.
    *
    * @param displayString 
    *   The value to set within the instance's 
    *   displayString property
    */
   public void setDisplayString(String displayString)
   {
      this.displayString = displayString;
   }
}
