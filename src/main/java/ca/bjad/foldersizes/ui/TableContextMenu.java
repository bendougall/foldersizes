package ca.bjad.foldersizes.ui;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import ca.bjad.foldersizes.events.AppEventListener;
import ca.bjad.foldersizes.events.AppWideEventManager;
import ca.bjad.foldersizes.events.EventType;

/**
 * The right click menu for the tables in
 * the application to let the user open the 
 * directory/file in windows explorer, or to 
 * reset the search to the selected directory
 * or parent folder.
 *
 * @author 
 *   Ben Dougall
 */
public class TableContextMenu extends JPopupMenu implements ActionListener, AppEventListener
{
   private static final long serialVersionUID = 8434367974137320981L;
   
   /**
    * The text and property text to send for the open in explorer
    * option. 
    */
   public static final String OPEN_IN_EXPLORER = "Open in Windows Explorer";
   /**
    * The text and property text to send for the search parent 
    * directory option. 
    */
   public static final String UPDATE_SEARCH_TO_PARENT = "Search parent directory";
   /**
    * The text and property text to send for the search this
    * directory option.
    */
   public static final String UPDATE_SEARCH_TO_DIRECTORY = "Search this directory";
   
   /**
    * Creates the right click menu for either the directory
    * table or the file table. 
    * 
    * @param forFile
    *    True if this instance of for the file table, false
    *    for the directory table. 
    */
   public TableContextMenu(boolean forFile)
   {
      super();
      // Create the show in explorer option. 
      JMenuItem option = new JMenuItem(OPEN_IN_EXPLORER);
      option.setActionCommand(OPEN_IN_EXPLORER);
      option.addActionListener(this);
      this.add(option);
      
      // Add a break in the menu. 
      this.addSeparator();
      
      // Add in the search parent option
      option = new JMenuItem(UPDATE_SEARCH_TO_PARENT);
      option.setActionCommand(UPDATE_SEARCH_TO_PARENT);
      option.addActionListener(this);
      this.add(option);
      
      if (!forFile)
      {
         // Add in the search this directory option if this is for the 
         // directory table. 
         option = new JMenuItem(UPDATE_SEARCH_TO_DIRECTORY);
         option.setActionCommand(UPDATE_SEARCH_TO_DIRECTORY);
         option.addActionListener(this);
         this.add(option);
         
         AppWideEventManager.instance().registerEventListener(EventType.DIRECTORY_TABLE_SELECTION_CHANGED, this);
      }
      else
      {
         AppWideEventManager.instance().registerEventListener(EventType.FILE_TABLE_SELECTION_CHANGED, this);
      }
      
      // Disable the options by default as there is no selection to 
      // begin with on the tables.
      enableOptions(false);
   }

   @Override
   public void actionPerformed(ActionEvent e)
   {
      // use the property change listener to send option's action 
      // command as the property name "changed" when selected. 
      firePropertyChange(e.getActionCommand(), false, true);
   }
   
   /**
    * Provides the property names to add to the property
    * listeners to react to menu options being selected.
    * @return
    *    Array of property names to react to.
    */
   public String[] getPropertyNamesForOptions()
   {
      return new String[] { 
            OPEN_IN_EXPLORER,
            UPDATE_SEARCH_TO_DIRECTORY,
            UPDATE_SEARCH_TO_PARENT
      };
   }
   
   private void enableOptions(boolean enable)
   {
      for (int index = 0; index < this.getComponentCount(); ++index)
      {
         Component c = this.getComponent(index);
         if (c instanceof JMenuItem)
         {
            c.setEnabled(enable);
         }
      }
   }

   @Override
   public void appEventOccurred(EventType eventType, Object source, Object eventBean)
   {
      if (eventBean instanceof Integer)
      {
         Integer index = (Integer)eventBean;
         enableOptions(index != -1);
      }
   }
}
