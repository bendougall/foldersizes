package ca.bjad.foldersizes.model;

/**
 * The model object for the all the Export Options
 * the user can select when they want to export 
 * results to file. 
 *
 *
 * @author 
 *   Ben Dougall
 */
public class ExportOptionsModel
{ 
   private ExportFormatType format = ExportFormatType.HTML;
   private ExportDateFormat dateFormat = ExportDateFormat.TIMESTAMP;
   private int numberOfEntriesForLargestList = 10;
   private String saveToPath = "";
   private ExportSizeFormatType sizeFormatter = ExportSizeFormatType.PLAIN;
   
   /**
    * Returns the value of the ExportOptionsModel instance's 
    * format property.
    *
    * @return 
    *   The value of format
    */
   public ExportFormatType getFormat()
   {
      return this.format;
   }
   /**
    * Sets the value of the ExportOptionsModel instance's 
    * format property.
    *
    * @param format 
    *   The value to set within the instance's 
    *   format property
    */
   public void setFormat(ExportFormatType format)
   {
      this.format = format;
   }
   /**
    * Returns the value of the ExportOptionsModel instance's 
    * dateFormat property.
    *
    * @return 
    *   The value of dateFormat
    */
   public ExportDateFormat getDateFormat()
   {
      return this.dateFormat;
   }
   /**
    * Sets the value of the ExportOptionsModel instance's 
    * dateFormat property.
    *
    * @param dateFormat 
    *   The value to set within the instance's 
    *   dateFormat property
    */
   public void setDateFormat(ExportDateFormat dateFormat)
   {
      this.dateFormat = dateFormat;
   }
   /**
    * Returns the value of the ExportOptionsModel instance's 
    * numberOfEntriesForLargestList property.
    *
    * @return 
    *   The value of numberOfEntriesForLargestList
    */
   public int getNumberOfEntriesForLargestList()
   {
      return this.numberOfEntriesForLargestList;
   }
   /**
    * Sets the value of the ExportOptionsModel instance's 
    * numberOfEntriesForLargestList property.
    *
    * @param numberOfEntriesForLargestList 
    *   The value to set within the instance's 
    *   numberOfEntriesForLargestList property
    */
   public void setNumberOfEntriesForLargestList(int numberOfEntriesForLargestList)
   {
      this.numberOfEntriesForLargestList = numberOfEntriesForLargestList;
   }
   /**
    * Returns the value of the ExportOptionsModel instance's 
    * saveToPath property.
    *
    * @return 
    *   The value of saveToPath
    */
   public String getSaveToPath()
   {
      return this.saveToPath;
   }
   /**
    * Sets the value of the ExportOptionsModel instance's 
    * saveToPath property.
    *
    * @param saveToPath 
    *   The value to set within the instance's 
    *   saveToPath property
    */
   public void setSaveToPath(String saveToPath)
   {
      this.saveToPath = saveToPath;
   }
   /**
    * Returns the value of the ExportOptionsModel instance's 
    * sizeFormatter property.
    *
    * @return 
    *   The value of sizeFormatter
    */
   public ExportSizeFormatType getSizeFormatter()
   {
      return this.sizeFormatter;
   }
   /**
    * Sets the value of the ExportOptionsModel instance's 
    * sizeFormatter property.
    *
    * @param sizeFormatter 
    *   The value to set within the instance's 
    *   sizeFormatter property
    */
   public void setSizeFormatter(ExportSizeFormatType sizeFormatter)
   {
      this.sizeFormatter = sizeFormatter;
   }
}
