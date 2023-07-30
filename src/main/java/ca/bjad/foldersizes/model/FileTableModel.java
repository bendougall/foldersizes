package ca.bjad.foldersizes.model;

import java.io.File;
import java.math.BigInteger;
import java.util.LinkedList;
import java.util.List;

import javax.swing.table.DefaultTableModel;

import ca.bjad.foldersizes.delegate.DateLabelDelegate;
import ca.bjad.foldersizes.delegate.SizeLabelDelegate;

/**
 * Table model for the file information within 
 * the UI once results are obtained. 
 *
 * @author 
 *   Ben Dougall
 */
public class FileTableModel extends DefaultTableModel
{
   private static final long serialVersionUID = 5532322187572918824L;
   
   /**
    * The list of directories to display in the table. 
    */
   protected List<File> files;
   
   /**
    * The column names to show in the table.
    */
   private static final String[] COLUMN_NAMES = new String[] 
         {
               "Filename",
               "Extension",
               "Size",
               "Modified Date"
         };
   
   /** 
    * Flag used to show the actual file size versus the user 
    * friendly size. 
    */
   protected boolean showActualSize = true;
   
   /** 
    * Flag used to show just the date versus the range and date
    */
   protected boolean showOnlyDate = true;
   
   @Override 
   public int getColumnCount()
   {
      return COLUMN_NAMES.length;
   }
   
   @Override
   public String getColumnName(int index)
   {
      return COLUMN_NAMES[index];
   }
   
   @Override
   public Class<?> getColumnClass(int index)
   {
      return String.class;
   }
   
   @Override
   public int getRowCount()
   {
      return getFiles().size();
   }
   
   @Override
   public boolean isCellEditable(int row, int column)
   {
      return false;
   }
   
   @Override
   public Object getValueAt(int row, int column)
   {
      File file = getFiles().get(row);
      switch (column)
      {
      case 0:
         return file.getAbsolutePath();
      case 1:
         int lastDotPos = file.getName().lastIndexOf('.');
         if (lastDotPos == -1)
         {
            return "";
         }
         else
         {
            return file.getName().substring(lastDotPos).replace(".", "").toUpperCase();
         }
      case 2:
         BigInteger size = new BigInteger(String.valueOf(file.length()));
         return showActualSize ? 
               SizeLabelDelegate.getCommaedString(size) : 
               SizeLabelDelegate.getUserFriendlyString(size);
      case 3:
         return DateLabelDelegate.getDateLabel(file.lastModified(), showOnlyDate);
      }
      return "";
   }

   /**
    * Returns the value of the FileTableModel instance's 
    * files property.
    *
    * @return 
    *   The value of files
    */
   public List<File> getFiles()
   {
      if (this.files == null)
      {
         this.files = new LinkedList<>();
      }
      return this.files;
   }

   /**
    * Sets the value of the FileTableModel instance's 
    * files property.
    *
    * @param files 
    *   The value to set within the instance's 
    *   files property
    */
   public void setFiles(List<File> files)
   {
      this.files = files;
      this.fireTableDataChanged();
   }

   /**
    * Returns the value of the DirectoryTableModel instance's 
    * showActualSize property.
    *
    * @return 
    *   The value of showActualSize
    */
   public boolean isActualSizeShowing()
   {
      return this.showActualSize;
   }

   /**
    * Sets the value of the FileTableModel instance's 
    * showActualSize property.
    *
    * @param showActualSize 
    *   The value to set within the instance's 
    *   showActualSize property
    */
   public void setShowActualSize(boolean showActualSize)
   {
      // Only update if the actual size option is changed.
      if (this.showActualSize != showActualSize)
      {
         this.showActualSize = showActualSize;
         // Refresh the data for the total size column. 
         for (int index = 0; index < getFiles().size(); ++index)
         {
            this.fireTableCellUpdated(index, 1);
         }
      }
   }

   /**
    * Sets the value of the FileTableModel instance's 
    * showOnlyDate property.
    *
    * @param showOnlyDate 
    *   The value to set within the instance's 
    *   showOnlyDate property
    */
   public void setShowOnlyDate(boolean showOnlyDate)
   {
      // Only update if the show only date option is changed.
      if (this.showOnlyDate != showOnlyDate)
      {
         this.showOnlyDate = showOnlyDate;
         // Refresh the data for the modified column. 
         for (int index = 0; index < getFiles().size(); ++index)
         {
            this.fireTableCellUpdated(index, 3);
         }
      }
   }
   
}
