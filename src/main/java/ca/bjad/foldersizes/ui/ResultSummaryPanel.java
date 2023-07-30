package ca.bjad.foldersizes.ui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import ca.bjad.foldersizes.delegate.SizeLabelDelegate;
import ca.bjad.foldersizes.events.AppWideEventManager;
import ca.bjad.foldersizes.events.EventType;
import ca.bjad.foldersizes.events.ExportEventBean;
import ca.bjad.foldersizes.model.DirectoryInfo;

/**
 * The panel containing the overall summary of the directory
 * analysis results (total file size, file count, and directory
 * count) as well as the option to go to the parent directory
 * in the case the user dived into a sub folder.
 *
 * @author 
 *   Ben Dougall
 */
public class ResultSummaryPanel extends JPanel implements ActionListener
{
   private static final long serialVersionUID = -9008225701049060411L;

   JLabel totalSizeValueLabel = new JLabel();
   JLabel fileCountValueLabel = new JLabel();
   JLabel dirCountValueLabel = new JLabel();
   JButton exportButton = new JButton("Export");
   
   private DirectoryInfo directory;
   private boolean showActualSize = true;
   
   /**
    * Constructor setting up the controls.
    */
   public ResultSummaryPanel()
   {
      super(new GridLayout(1, 4, 5, 5), true);
      setBorder(new EmptyBorder(0, 5, 0, 5));
      add(totalSizeValueLabel);
      add(fileCountValueLabel);
      add(dirCountValueLabel);
      add(exportButton);
      
      exportButton.addActionListener(this);
   }
   
   /**
    * Sets the directory information within the panel and 
    * refreshes the display for it. 
    * 
    * @param directoryInfo
    *    The directory information to show the summary for.
    */
   public void setDirectoryInfo(DirectoryInfo directoryInfo)
   {
      this.directory = directoryInfo;
      refreshValues();
   }
   
   /**
    * Sets the show actual size flag in the panel and refreshes 
    * the summary displayed. 
    * 
    * @param showActualSize
    *    True to show the byte count, false to show the user friendly label.
    */
   public void setShowActiveSize(boolean showActualSize)
   {
      this.showActualSize = showActualSize;
      refreshValues();
   }
      
   private void refreshValues()
   {
      if (directory != null)
      {
         totalSizeValueLabel.setText("Total Size: " + (showActualSize ? 
               SizeLabelDelegate.getCommaedString(directory.getTotalSize()) : SizeLabelDelegate.getUserFriendlyString(directory.getTotalSize())));
         fileCountValueLabel.setText("File Count: " + directory.getTotalFileCount().toString());
         dirCountValueLabel.setText("Directory Count: " + directory.getTotalDirCount().toString());
      }
      else
      {
         totalSizeValueLabel.setText("");
         fileCountValueLabel.setText("");
         dirCountValueLabel.setText("");
      }
   }

   @Override
   public void actionPerformed(ActionEvent e)
   {
      if (exportButton.equals(e.getSource()))
      {
         AppWideEventManager.instance().fireEvent(EventType.EXPORT_RESULTS, this, new ExportEventBean(this.directory, this));
      }
   }
}
