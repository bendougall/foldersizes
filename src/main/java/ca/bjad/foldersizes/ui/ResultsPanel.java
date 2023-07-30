package ca.bjad.foldersizes.ui;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import ca.bjad.foldersizes.model.DirectoryInfo;

/**
 * Panel containing the results from the directory analysis
 *
 * @author 
 *   Ben Dougall
 */
public class ResultsPanel extends JPanel
{
   private static final long serialVersionUID = 8775137987038397967L;
   
   ResultSummaryPanel summaryPanel = new ResultSummaryPanel();
   DirectoryTablePanel directoryPanel = new DirectoryTablePanel();
   FileTablePanel filePanel = new FileTablePanel();
   
   TitledBorder border = new TitledBorder("Results");
   
   /**
    * Constructor creating all the subpanels and controls
    * within the panel.
    */
   public ResultsPanel()
   {
      // Create the panel with the 1x1 grid layout and double buffering enabled
      super(new BorderLayout(5, 5), true);
      
      border.setTitleFont(border.getTitleFont().deriveFont(border.getTitleFont().getSize2D() + 2.0f));
      setBorder(border);
      
      this.add(summaryPanel, BorderLayout.NORTH);
      
      JPanel tablesPanel = new JPanel(new GridLayout(2, 1, 5, 5), true);
      tablesPanel.add(directoryPanel);
      tablesPanel.add(filePanel);
      
      this.add(tablesPanel, BorderLayout.CENTER);
   }

   /**
    * Sets the value of the ResultsPanel instance's 
    * directory property.
    *
    * @param directory 
    *   The value to set within the instance's 
    *   directory property
    */
   public void setDirectory(DirectoryInfo directory)
   {
      this.invalidate();
      border.setTitle("Results for " + directory.getPath());
      summaryPanel.setDirectoryInfo(directory);
      directoryPanel.setDirectoryInfo(directory);
      filePanel.setDirectoryInfo(directory);
      this.validate();
      this.repaint();
   }

   /**
    * Sets the value of the ResultsPanel instance's 
    * showActualSize property.
    *
    * @param showActualSize 
    *   The value to set within the instance's 
    *   showActualSize property
    */
   public void setShowActualSize(boolean showActualSize)
   {
      summaryPanel.setShowActiveSize(showActualSize);
      directoryPanel.setShowActualSize(showActualSize);
      filePanel.setShowActualSize(showActualSize);
   }
   
   /**
    * Sets the value of the ResultsPanel instance's 
    * showOnlyDate property.
    *
    * @param showOnlyDate
    *   The value to set within the instance's 
    *   showOnlyDate property
    */
   public void setShowOnlyDate(boolean showOnlyDate)
   {
      filePanel.setShowOnlyDate(showOnlyDate);
   }
   
   /**
    * Sets the state of showing all sub directories in the results 
    * or not.
    * 
    * @param showAllSubDirectories
    *    True to show all the sub directories, false to show 
    *    only the folders in the root folder of the search. 
    */
   public void setShowAllSubdirectories(boolean showAllSubDirectories)
   {
      directoryPanel.setShowAllSubdirectories(showAllSubDirectories);
   }
   
   /**
    * Sets the state of showing all the sub files in the results, or 
    * just the files in the immidiately searched in directory.
    * 
    * @param showAllFiles
    *    True to show all the files, false to show 
    *    only the files in the root folder of the search.
    */
   public void setShowAllFiles(boolean showAllFiles)
   {
      filePanel.setShowAllFiles(showAllFiles);
   }
}
