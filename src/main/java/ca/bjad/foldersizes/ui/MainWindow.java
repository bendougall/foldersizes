package ca.bjad.foldersizes.ui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingWorker;
import javax.swing.border.EmptyBorder;

import ca.bjad.foldersizes.delegate.ExportDelegate;
import ca.bjad.foldersizes.delegate.StartExplorerDelegate;
import ca.bjad.foldersizes.events.AppEventListener;
import ca.bjad.foldersizes.events.AppWideEventManager;
import ca.bjad.foldersizes.events.EventType;
import ca.bjad.foldersizes.events.FileDirectoryEventBean;
import ca.bjad.foldersizes.model.DirectoryInfo;

/**
 * The Frame implementation for the Folder Sizes application.
 *
 * @author 
 *   Ben Dougall
 */
public class MainWindow extends JFrame implements ActionListener, PropertyChangeListener, AppEventListener
{
   private static final long serialVersionUID = 3269352378198235061L;
   
   private DirectorySelectionPanel directoryPanel;
   private OptionSelectionPanel optionPanel;
   private JButton analyzeButton = new JButton("Analyze");
   private ResultsPanel resultsPanel;
   
   private LoadingWindow loadingWindow = null;
   
   private File directoryToAnalyze;
   
   /**
    * Default constructor setting the directory to the current
    * working directory. 
    */
   public MainWindow()
   {
      this(new File("."));
   }
   
   /**
    * Constructor, setting the path to show within the directory
    * to analyze field. 
    * 
    * @param directoryToAnalyze
    *    The directory to show in the field.
    */
   public MainWindow(File directoryToAnalyze)
   {
      super("BJAD Folder Sizes Application");
      
      this.directoryToAnalyze = directoryToAnalyze;
      
      // Setup the window properties.
      this.setSize(1024, 768);
      this.setContentPane(createContentPane());
      this.addWindowListener(new WindowAdapter()
      {
         @Override
         public void windowClosing(WindowEvent e)
         {
            onWindowClosing(e);
         }
      });
      
      // Wire Swing listeners
      analyzeButton.addActionListener(this);
      optionPanel.addPropertyChangeListener(OptionSelectionPanel.SHOW_ACTUAL_SIZE, this);
      optionPanel.addPropertyChangeListener(OptionSelectionPanel.SHOW_ONLY_DATE, this);
      optionPanel.addPropertyChangeListener(OptionSelectionPanel.SHOW_ALL_SUBDIRECTORIES, this);
      optionPanel.addPropertyChangeListener(OptionSelectionPanel.SHOW_ALL_FILES, this);
      
      // Wire app listeners
      AppWideEventManager.instance().registerEventListener(EventType.OPEN_IN_WINDOWS_EXPLORER, new StartExplorerDelegate());
      AppWideEventManager.instance().registerEventListener(EventType.SEARCH_DIRECTORY, this);
      AppWideEventManager.instance().registerEventListener(EventType.EXPORT_RESULTS, new ExportDelegate());
      
      // Initialize the loading window for future use. 
      loadingWindow = new LoadingWindow(this, "Gathering Directory Data");
   }
   
   @Override
   public void propertyChange(PropertyChangeEvent evt)
   {
      // Option Panel updates.
      if (evt.getSource().equals(optionPanel))
      {
         switch (evt.getPropertyName())
         {
         case OptionSelectionPanel.SHOW_ACTUAL_SIZE:
            resultsPanel.setShowActualSize((Boolean)evt.getNewValue());
            break;
         case OptionSelectionPanel.SHOW_ONLY_DATE:
            resultsPanel.setShowOnlyDate((Boolean)evt.getNewValue());
            break;
         case OptionSelectionPanel.SHOW_ALL_SUBDIRECTORIES:
            resultsPanel.setShowAllSubdirectories((Boolean)evt.getNewValue());
            break;
         case OptionSelectionPanel.SHOW_ALL_FILES:
            resultsPanel.setShowAllFiles((Boolean)evt.getNewValue());
            break;
         }
      }
      else if (evt.getSource() instanceof GatherDirectoryInfoWorker)
      {
         GatherDirectoryInfoWorker worker = (GatherDirectoryInfoWorker)evt.getSource();
         if (SwingWorker.StateValue.STARTED.equals(evt.getNewValue()))
         {
            // Center the window to the screen's activate location.
            loadingWindow.setLocationRelativeTo(this);
            // Show the loading window.
            loadingWindow.setVisible(true);
         }
         else if (SwingWorker.StateValue.DONE.equals(evt.getNewValue()))
         {
            // Get the results and show and/or update the results panel.
            try
            {
               DirectoryInfo results = worker.get();
               resultsPanel.setVisible(true);
               resultsPanel.setDirectory(results);
            }
            // Should never happen, but needed for the Background worker contract
            catch (Exception ex)
            {
               ex.printStackTrace();
            }
            
            // Hide the loading window.
            loadingWindow.setVisible(false);
         }
      }
         
   }
   
   /**
    * Triggered when the window is about to be closed using the 
    * close icon on the window. 
    * 
    * @param e
    *    The event provided by swing when the window is 
    *    about to be closed.
    */
   protected void onWindowClosing(WindowEvent e)
   {
      // Exit the application.
      System.exit(0);
   }
   
   private JPanel createContentPane()
   {  
      directoryPanel = new DirectorySelectionPanel(directoryToAnalyze);
      optionPanel = new OptionSelectionPanel();
      resultsPanel = new ResultsPanel();
      
      // Hide the results panel until there are results to show.
      resultsPanel.setVisible(false);
            
      JPanel analyzePanel = new JPanel(new BorderLayout(5, 5), true);
      analyzePanel.add(new JLabel(), BorderLayout.CENTER);
      analyzePanel.add(analyzeButton, BorderLayout.EAST);
      analyzePanel.setBorder(new EmptyBorder(4, 1, 1, 10));
      
      JPanel headingPanel = new JPanel(true);
      headingPanel.setLayout(new BoxLayout(headingPanel, BoxLayout.Y_AXIS));
      headingPanel.add(directoryPanel);
      headingPanel.add(optionPanel);
      headingPanel.add(analyzePanel);
      
      JPanel mainPanel = new JPanel(true);
      mainPanel.setLayout(new BorderLayout(5, 5));
      mainPanel.setBorder(new EmptyBorder(6, 0, 6, 0));
      mainPanel.add(headingPanel, BorderLayout.NORTH);
      mainPanel.add(resultsPanel, BorderLayout.CENTER);
      
      return mainPanel;
   }

   @Override
   public void actionPerformed(ActionEvent e)
   {
      // If the analyze button is activated, start the background 
      // worker to get the directory information for the directory
      // set in the selection panel.
      if (e.getSource().equals(analyzeButton))
      {
         GatherDirectoryInfoWorker worker = new GatherDirectoryInfoWorker(new File(directoryPanel.directoryField.getText()));
         worker.addPropertyChangeListener(this);
         worker.execute();
      }
   }
   
   @Override
   public void appEventOccurred(EventType eventType, Object source, Object eventBean)
   {
      if (EventType.SEARCH_DIRECTORY.equals(eventType))
      {
         if (eventBean instanceof FileDirectoryEventBean)
         {
            FileDirectoryEventBean args = (FileDirectoryEventBean)eventBean;
            // Hide the results from the last search
            resultsPanel.setVisible(false);
            // Update the directory to search text field with the targetted path. 
            directoryPanel.directoryField.setText(args.getTarget().getAbsolutePath());
            // Press the Analyze button for the user.
            analyzeButton.doClick();
         }
      }
   }
}
