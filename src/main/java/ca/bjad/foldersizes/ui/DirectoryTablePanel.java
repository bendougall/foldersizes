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
import ca.bjad.foldersizes.model.DirectoryTableModel;

/**
 * Panel containing the table showing the subdirectories
 * for the results obtained from the directory search. 
 *
 * @author 
 *   Ben Dougall
 */
public class DirectoryTablePanel extends JPanel implements Runnable, PropertyChangeListener, ListSelectionListener
{
   private static final long serialVersionUID = -8515486112532910648L;

   private DirectoryTableModel tableModel = new DirectoryTableModel();
   private JTable table;
   private JScrollPane scrollPane;
   private TableContextMenu contextMenu = new TableContextMenu(false);
   
   private DirectoryInfo directoryInfo = null;
   
   private boolean showAllSubdirectories = true;
   
   /**
    * Constructor, building the UI components in the panel.
    */
   public DirectoryTablePanel()
   {
      super(new GridLayout(1, 1), true);
      setBorder(new TitledBorder("Subdirectory Information"));
      
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
      this.directoryInfo = info;
      if (info == null || BigInteger.ZERO.compareTo(info.getTotalDirCount()) == 0)
      {
         tableModel.setDirectories(new LinkedList<>());
         scrollPane.setVisible(false);
         
         // Size the columns for content.
         sizeColumnsForContent();
      }
      else
      {         
         scrollPane.setVisible(true);
         setDataInModel();
      }
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
    * Sets the state of showing all sub directories in the results 
    * or not.
    * 
    * @param state
    *    True to show all the sub directories, false to show 
    *    only the folders in the root folder of the search. 
    */
   public void setShowAllSubdirectories(boolean state)
   {
      showAllSubdirectories = state;
      setDataInModel();
   }
   
   /**
    * Refreshes the data in the table based on the all subdirectory option
    * being selected or unselected.
    */
   private void setDataInModel()
   {
      // Add the root subdirectories if the all option is disabled.
      if (!showAllSubdirectories)
      {
         tableModel.setDirectories(directoryInfo.getSubdirectories());
      }
      // All option, get the recursive list of sub directories from the results.
      else
      {
         tableModel.setDirectories(getAllSubDirectoryList(directoryInfo));
      }
      
      // size the columns for the updated content.
      sizeColumnsForContent();
   }
   
   /**
    * Creates the list of directory info objects from the results and the 
    * subdirectories of the directory passed so all the directories within 
    * the search are displayed. 
    * 
    * @param dirInfo
    *    The directory to add and then add the sub directories for.
    * @return
    *    The list of all the subdirectories nested in the directory info passed.
    */
   private List<DirectoryInfo> getAllSubDirectoryList(DirectoryInfo dirInfo)
   {
      List<DirectoryInfo> allDirs = new LinkedList<DirectoryInfo>();
      for (DirectoryInfo sub : dirInfo.getSubdirectories())
      {
         allDirs.add(sub);
         allDirs.addAll(getAllSubDirectoryList(sub));
      }
      return allDirs;
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
      int totalWidth = 0;
      for (int column = 0; column < table.getColumnCount(); column++)
      {
         int width = 100; // Min width
         for (int row = 0; row < table.getRowCount(); row++)
         {
            TableCellRenderer renderer = table.getCellRenderer(row, column);
            Component comp = table.prepareRenderer(renderer, row, column);
            width = Math.max(comp.getPreferredSize().width + 1, width);
         }
         columnModel.getColumn(column).setPreferredWidth(width);
         if (column != 0)
         {
            totalWidth += width;
         }
      }

      int parentWidth = table.getParent().getWidth();
      columnModel.getColumn(0).setPreferredWidth(parentWidth - totalWidth);
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
         else if (TableContextMenu.UPDATE_SEARCH_TO_DIRECTORY.equals(evt.getPropertyName()))
         {
            AppWideEventManager.instance().fireEvent(EventType.SEARCH_DIRECTORY, this, new FileDirectoryEventBean(selectedFile));
         }
      }
   }

   @Override
   public void valueChanged(ListSelectionEvent e)
   {
      AppWideEventManager.instance().fireEvent(EventType.DIRECTORY_TABLE_SELECTION_CHANGED, this, Integer.valueOf(e.getFirstIndex()));
   }
}
