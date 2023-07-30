package ca.bjad.foldersizes.ui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

/**
 * The panel that includes the directory to analyze field and the 
 * browse button. 
 *
 * @author 
 *   Ben Dougall
 */
public class DirectorySelectionPanel extends JPanel implements ActionListener
{
   private static final long serialVersionUID = 8775137867038397967L;

   /**
    * The text field containing the directory the application will
    * analyze when the user triggers it within the window.
    */
   JTextField directoryField = new JTextField();
   /**
    * Browse button, allowing the user to press to see a folder selection
    * window to set the directory without typing it out.
    */
   JButton browseButton = new JButton("Browse");
   
   /**
    * Constructor for the panel, setting the default folder to analyze 
    * based on the file passed to the constructor. 
    * 
    * @param directory
    *    The file representing the location of the directory to 
    *    use as the default location for the window to display.
    */
   public DirectorySelectionPanel(File directory)
   {
      // Create the panel with the 1x1 grid layout and double buffering enabled
      super(new GridLayout(1, 1), true);
      
      TitledBorder border = new TitledBorder("Directory to Analyze: ");
      border.setTitleFont(border.getTitleFont().deriveFont(border.getTitleFont().getSize2D() + 2.0f));
      setBorder(border);
      
      // Create the inner panel so the empty border can be used for 
      // some padding within the titled border.
      JPanel innerPanel = new JPanel(new BorderLayout(5, 5), true);
      innerPanel.setBorder(new EmptyBorder(4, 6, 6, 6));
      
      // Add in the controls
      innerPanel.add(directoryField, BorderLayout.CENTER);
      innerPanel.add(browseButton, BorderLayout.EAST);
      
      // Add the inner panel with the padding tp the overall panel.
      add(innerPanel, BorderLayout.CENTER);
      
      // If a directory was provided, set the directory field's text 
      // value depending on the type of file passed to the constructor
      if (directory != null)
      {
         String textToSet = "";
         // If the file passed was a directory, set the field's
         // value to the path of the file.
         if (directory.isDirectory())
         {         
            textToSet = directory.getAbsolutePath();      
         }
         // If the file passed was a file, get the field's value
         // to the path to that file's parent directory.
         else if (directory.isFile())
         {
            textToSet = directory.getParentFile().getAbsolutePath();
         }
         
         // Remove a trailing . that comes with the current working directory
         // from time to time. 
         if (textToSet.endsWith("."))
         {
            textToSet = textToSet.substring(0, textToSet.length() - 1);
         }
         // Remove the trailing directory slash as the absolete path from
         // the File library does not show it. 
         if (textToSet.endsWith("\\"))
         {
            textToSet = textToSet.substring(0, textToSet.length() - 1);
         }
            
         // Set the directory path within the text field
         directoryField.setText(textToSet);
      }
      
      // Bind the button to showing the file chooser window to select
      // a directory visually instead of typing out the path manually.
      browseButton.addActionListener(this);
   }

   @Override
   public void actionPerformed(ActionEvent e)
   {
      if (e.getSource().equals(browseButton))
      {
         JFileChooser fileChooser = null;
         
         // Make the file chooser open the directory in the text box 
         // if the textbox contains a path to a directory, or to the
         // parent directory if a file path is in the text box.
         if (!directoryField.getText().trim().isEmpty())
         {
            File f = new File(directoryField.getText().trim());
            // Get the parent directory if the text box represents
            // a path to a file
            if (f.isFile())
            {
               f = f.getParentFile();
            }
            // Make the file chooser setting the default path to 
            // the directory found with the above logic
            if (f.isDirectory())
            {
               fileChooser = new JFileChooser(f);
            }
         }
         // No file/directory path in the textbox, create the 
         // default file chooser.
         if (fileChooser == null)
         {
            fileChooser = new JFileChooser();
         }
         
         // Make the chooser only apply to directories, and not files.
         fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
         
         // Change the Approve option text from the default "open" to 
         // Select for better user experience.
         fileChooser.setApproveButtonText("Select");
         
         // Show the dialog, and store the result of what button they selected
         int option = fileChooser.showOpenDialog(this.getParent());
         
         // Select/OK pressed, set the text in the text field to the selected
         // directory from the dialog.
         if(option == JFileChooser.APPROVE_OPTION)
         {
            directoryField.setText(fileChooser.getSelectedFile().getAbsolutePath());
         }
      }
   }
   
}
