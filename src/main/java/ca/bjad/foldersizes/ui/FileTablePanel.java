package ca.bjad.foldersizes.ui;

import java.awt.Component;
import java.awt.GridLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.math.BigInteger;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

import ca.bjad.foldersizes.events.AppWideEventManager;
import ca.bjad.foldersizes.events.EventType;
import ca.bjad.foldersizes.events.FileDirectoryEventBean;
import ca.bjad.foldersizes.model.DirectoryInfo;
import ca.bjad.foldersizes.model.FileTableModel;

/**
 * Panel containing the table showing the subdirectories
 * for the results obtained from the directory search. 
 *
 * @author 
 *   Ben Dougall
 */
public class FileTablePanel extends JPanel implements Runnable, PropertyChangeListener, ListSelectionListener
{
   private static final long serialVersionUID = -8515486112532910648L;

   private FileTableModel tableModel = new FileTableModel();
   private JTable table;
   private JScrollPane scrollPane;
   private TableContextMenu contextMenu = new TableContextMenu(true);
   
   private TitledBorder border = new TitledBorder("Files in Directory");
   
   private boolean showOnlyDate = false;
   private boolean showAllFiles = true;
   
   private DirectoryInfo directory = null;
   
   /**
    * Constructor, building the UI components in the panel.
    */
   public FileTablePanel()
   {
      super(new GridLayout(1, 1), true);
      setBorder(border);
      
      table = new JTable(tableModel);
      table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
      table.setFillsViewportHeight(true);      
      table.setComponentPopupMenu(contextMenu);
      table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
      table.getSelectionModel().addListSelectionListener(this);
      
      scrollPane = new JScrollPane(table);      
      scrollPane.setVisible(false);
      
      add(scrollPane);
      
      // Add in the property change listener to the menu to react 
      // the menu options being selected.
      for (String propName : contextMenu.getPropertyNamesForOptions())
      {
         contextMenu.addPropertyChangeListener(propName, this);
      }
   }
   
   /**
    * Sets the directory information to display within the table view.
    * @param info
    *    The directory info gathered by the app containing the sub directories
    *    to display within the panel.
    */
   public void setDirectoryInfo(DirectoryInfo info)
   {
      this.directory = info;
      
      StringBuilder titleBuilder = new StringBuilder("File information");      
      if (info == null || BigInteger.ZERO.compareTo(info.getTotalFileCount()) == 0)
      {
         titleBuilder.append(": No files found within ");
         tableModel.setFiles(new LinkedList<>());
         scrollPane.setVisible(false);
      }
      else
      {
         titleBuilder.append(" for ").append(info.getFiles().size()).append(" files within ");
         scrollPane.setVisible(true);
         refreshFileDisplay();
         sizeColumnsForContent();
      }
      titleBuilder.append(info.getPath());
      
      border.setTitle(titleBuilder.toString());
   }
   
   /**
    * Sets the flag to show the actual size of the directories, or the 
    * user friendly string.
    * 
    * @param state
    *    True to show the actual byte size of the directory, false to 
    *    show the user friendly string.
    */
   public void setShowActualSize(boolean state)
   {
      tableModel.setShowActualSize(state);  
      sizeColumnsForContent();
   }
   
   /**
    * Sets the value of the FileTablePanel instance's 
    * showOnlyDate property.
    *
    * @param showOnlyDate 
    *   The value to set within the instance's 
    *   showOnlyDate property
    */
   public void setShowOnlyDate(boolean showOnlyDate)
   {
      this.showOnlyDate = showOnlyDate;
      tableModel.setShowOnlyDate(showOnlyDate);
      sizeColumnsForContent();
   }
   
   /**
    * Sets the flag to show all the files within the search
    * results, or just the files for the directory immediately
    * searched in. 
    * 
    * @param showAllFiles
    *    True to show all the sub files, false to show the files
    *    for the immediately searched for directory.
    */
   public void setShowAllFiles(boolean showAllFiles)
   {
      this.showAllFiles = showAllFiles;
      if (this.directory != null)
      {
         refreshFileDisplay();
      }
   }

   private void refreshFileDisplay()
   {
      if (showAllFiles)
      {
         tableModel.setFiles(getAllFiles(this.directory));
      }
      else
      {
         tableModel.setFiles(this.directory.getFiles());
      }
      
      sizeColumnsForContent();
   }
   
   private List<File> getAllFiles(DirectoryInfo directory)
   {
      List<File> allFiles = new LinkedList<>();
      allFiles.addAll(directory.getFiles());
      for (DirectoryInfo dir : directory.getSubdirectories())
      {
         allFiles.addAll(getAllFiles(dir));
      }
      return allFiles;
   }
   
   private void sizeColumnsForContent()
   {      
      // Resize the columns after all the swing events in the queue are 
      // complete so the table columns account for all the content and 
      // the size of the panel.
      SwingUtilities.invokeLater(this);
   }

   @Override
   public void run()
   {
      final TableColumnModel columnModel = table.getColumnModel();
      int parentWidth = table.getParent().getWidth();
      
      if (showOnlyDate)
      {
         int columnWidth = parentWidth / table.getColumnCount();
         int remainder = parentWidth % table.getColumnCount();
         
         for (int column = 0; column < table.getColumnCount(); column++) 
         {
            columnModel.getColumn(column).setPreferredWidth(column == 3 ? columnWidth + remainder : columnWidth);
         }
      }
      else
      {
         int totalWidth = 0;
         for (int column = 0; column < table.getColumnCount(); column++) {
             int width = 150; // Min width
             for (int row = 0; row < table.getRowCount(); row++) {
                 TableCellRenderer renderer = table.getCellRenderer(row, column);
                 Component comp = table.prepareRenderer(renderer, row, column);
                 width = Math.max(comp.getPreferredSize().width +1 , width);
             }
             columnModel.getColumn(column).setPreferredWidth(width);
             if (showOnlyDate || column != 3)
             {
                totalWidth += width;
             }
         }
         
         columnModel.getColumn(3).setPreferredWidth(parentWidth - totalWidth);
      }
   }

   @Override
   public void propertyChange(PropertyChangeEvent evt)
   {
      if (table.getSelectedRow() > -1)
      {
         File selectedFile = new File(table.getValueAt(table.getSelectedRow(), 0).toString()); 
         if (TableContextMenu.OPEN_IN_EXPLORER.equals(evt.getPropertyName()))
         {
            AppWideEventManager.instance().fireEvent(EventType.OPEN_IN_WINDOWS_EXPLORER, this, new FileDirectoryEventBean(selectedFile));
         }
         else if (TableContextMenu.UPDATE_SEARCH_TO_PARENT.equals(evt.getPropertyName()))
         {
            AppWideEventManager.instance().fireEvent(EventType.SEARCH_DIRECTORY, this, new FileDirectoryEventBean(selectedFile.getParentFile()));
         }
      }
   }
   
   @Override
   public void valueChanged(ListSelectionEvent e)
   {
      AppWideEventManager.instance().fireEvent(EventType.DIRECTORY_TABLE_SELECTION_CHANGED, this, Integer.valueOf(e.getFirstIndex()));
   }
}
