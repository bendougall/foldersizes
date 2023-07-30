package ca.bjad.foldersizes.model;

import java.math.BigInteger;
import java.util.LinkedList;
import java.util.List;

import javax.swing.table.DefaultTableModel;

import ca.bjad.foldersizes.delegate.SizeLabelDelegate;

/**
 * Table model for the sub directory information within 
 * the UI once results are obtained. 
 *
 * @author 
 *   Ben Dougall
 */
public class DirectoryTableModel extends DefaultTableModel
{
   private static final long serialVersionUID = 5532322187572918824L;
   
   /**
    * The list of directories to display in the table. 
    */
   protected List<DirectoryInfo> directories;
   
   /**
    * The column names to show in the table.
    */
   private static final String[] COLUMN_NAMES = new String[] 
         {
               "Directory Path",
               "Total Size",
               "Sub Dir Count",
               "File Count"
         };
   
   /** 
    * Flag used to show the actual file size versus the user 
    * friendly size. 
    */
   protected boolean showActualSize = true;
   
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
      return getDirectories().size();
   }
   
   @Override
   public boolean isCellEditable(int row, int column)
   {
      return false;
   }
   
   @Override
   public Object getValueAt(int row, int column)
   {
      DirectoryInfo dir = getDirectories().get(row);
      switch (column)
      {
      case 0:
         return dir.getPath();
      case 1:
         if (dir.getTotalSize().compareTo(BigInteger.ZERO) > 0)
         {
            return showActualSize ? 
               SizeLabelDelegate.getCommaedString(dir.getTotalSize()) : 
               SizeLabelDelegate.getUserFriendlyString(dir.getTotalSize());
         }
         else
         {
            return "-";
         }
      case 2:
         return dir.getTotalDirCount().compareTo(BigInteger.ZERO) == 0 ? "-" : dir.getTotalDirCount().toString();
      case 3:
         return dir.getTotalFileCount().compareTo(BigInteger.ZERO) == 0 ? "-" : dir.getTotalFileCount().toString();
      }
      return "";
   }

   /**
    * Returns the value of the DirectoryTableModel instance's 
    * directories property.
    *
    * @return 
    *   The value of directories
    */
   public List<DirectoryInfo> getDirectories()
   {
      if (this.directories == null)
      {
         this.directories = new LinkedList<>();
      }
      return this.directories;
   }

   /**
    * Sets the value of the DirectoryTableModel instance's 
    * directories property.
    *
    * @param directories 
    *   The value to set within the instance's 
    *   directories property
    */
   public void setDirectories(List<DirectoryInfo> directories)
   {
      this.directories = directories;
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
    * Sets the value of the DirectoryTableModel instance's 
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
         for (int index = 0; index < getDirectories().size(); ++index)
         {
            this.fireTableCellUpdated(index, 1);
         }
      }
   }
}
