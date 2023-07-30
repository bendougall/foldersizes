package ca.bjad.foldersizes.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

/**
 * The panel that includes the various options the user can have/set
 * within the application.
 *
 * @author 
 *   Ben Dougall
 */
public class OptionSelectionPanel extends JPanel implements ItemListener 
{
   private static final long serialVersionUID = 8775137867038397967L;
   /**
    * Property name for the panel to notify property change listeners
    * that the actual size checkbox was selected or not. 
    */
   public static final String SHOW_ACTUAL_SIZE = "ShowActualSize";
   
   /**
    * Property name for the panel to notify property change listeners
    * that the only date checkbox was selected or not. 
    */
   public static final String SHOW_ONLY_DATE = "ShowOnlyDate";
   
   /**
    * Property name for the panel to notify property change listeners
    * that the show all sub directories checkbox was selected or not. 
    */
   public static final String SHOW_ALL_SUBDIRECTORIES = "ShowAllSubdirectories";
   
   /**
    * Property name for the panel to notify property change listeners
    * that the show all sub directories checkbox was selected or not. 
    */
   public static final String SHOW_ALL_FILES = "ShowAllFiles";
   
   /**
    * Checkbox allowing for the user to select if file sizes will be 
    * shown with the actual byte size, or the user friendly option 
    * (aka 1.1 KB versus 1,100 bytes)
    */
   JCheckBox showActualSizeCheckbox = new JCheckBox("Show sizes in bytes");
   
   /**
    * Checkbox allowing for the user to select if dates will be shown with 
    * just the date string, or with the modified range as well. 
    */
   JCheckBox showOnlyDateCheckbox = new JCheckBox("Show only dates");
   
   /**
    * The checkbox allowing the user to select if they want to see all the 
    * subdirectories in the results, or just the top level results.
    */
   JCheckBox showAllSubDirectories = new JCheckBox("Show all Sub Directories");
   
   /**
    * The checkbox allowing the user to select if they want to see all the 
    * files in the results, or just the top level results.
    */
   JCheckBox showAllFiles = new JCheckBox("Show all Files");
   
   /**
    * Constructor for the panel, setting all the option controls within the
    * panel and their default settings.
    */
   public OptionSelectionPanel()
   {
      // Create the panel with the 1x1 grid layout and double buffering enabled
      super(new GridLayout(1, 1), true);
      
      TitledBorder border = new TitledBorder("Options ");
      border.setTitleFont(border.getTitleFont().deriveFont(border.getTitleFont().getSize2D() + 2.0f));
      setBorder(border);
      
      // Create the inner panel so the empty border can be used for 
      // some padding within the titled border.
      JPanel innerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5), true);
      innerPanel.setBorder(new EmptyBorder(4, 6, 6, 6));
      
      // Add in the controls
      innerPanel.add(showActualSizeCheckbox);
      innerPanel.add(showOnlyDateCheckbox);
      innerPanel.add(showAllSubDirectories);
      innerPanel.add(showAllFiles);
      innerPanel.add(new JLabel());
      
      // Add the inner panel with the padding tp the overall panel.
      add(innerPanel, BorderLayout.CENTER);
      
      // Bind the button to showing the file chooser window to select
      // a directory visually instead of typing out the path manually.
      showActualSizeCheckbox.addItemListener(this);
      showOnlyDateCheckbox.addItemListener(this);
      showAllSubDirectories.addItemListener(this);
      showAllFiles.addItemListener(this);
      
      // Check off the checkbox by default.
      showActualSizeCheckbox.setSelected(true);
      showOnlyDateCheckbox.setSelected(true);
      showAllSubDirectories.setSelected(true);
      showAllFiles.setSelected(true);
   }

   @Override
   public void itemStateChanged(ItemEvent e)
   {
      // Fire the property change listener for the actual size property so
      // other controls can be notified the actual size checkbox has been 
      // checked on unchecked.
      if (e.getSource().equals(showActualSizeCheckbox))
      {
         firePropertyChange(SHOW_ACTUAL_SIZE, !showActualSizeCheckbox.isSelected(), showActualSizeCheckbox.isSelected());
      }
      // Fire the property change listener for the actual size property so
      // other controls can be notified the actual size checkbox has been 
      // checked on unchecked.
      else if (e.getSource().equals(showOnlyDateCheckbox))
      {
         firePropertyChange(SHOW_ONLY_DATE, !showOnlyDateCheckbox.isSelected(), showOnlyDateCheckbox.isSelected());
      }
      // Fire the property change listener for the show all sub directories property so other
      // controls can be notified the actual size checkbox has been checked on unchecked.
      else if (e.getSource().equals(showAllSubDirectories))
      {
         firePropertyChange(SHOW_ALL_SUBDIRECTORIES, !showAllSubDirectories.isSelected(), showAllSubDirectories.isSelected());
      }
      // Fire the property change listener for the show all sub directories property so other
      // controls can be notified the actual size checkbox has been checked on unchecked.
      else if (e.getSource().equals(showAllFiles))
      {
         firePropertyChange(SHOW_ALL_FILES, !showAllFiles.isSelected(), showAllFiles.isSelected());
      }
   }
   
}
