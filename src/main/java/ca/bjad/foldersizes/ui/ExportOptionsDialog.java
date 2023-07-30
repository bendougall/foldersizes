package ca.bjad.foldersizes.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileFilter;
import javax.swing.text.PlainDocument;

import ca.bjad.foldersizes.model.ExportDateFormat;
import ca.bjad.foldersizes.model.ExportFormatType;
import ca.bjad.foldersizes.model.ExportOptionsModel;
import ca.bjad.foldersizes.model.ExportSizeFormatType;

/**
 * Dialog window for the export options.
 *
 *
 * @author 
 *   Ben Dougall
 */
public class ExportOptionsDialog extends JDialog implements ActionListener, ItemListener
{
   private static final long serialVersionUID = -7821722207335631603L;
   
   JComboBox<ExportFormatType> formatDropdown = new JComboBox<ExportFormatType>(ExportFormatType.values());
   JTextField filepathField = new JTextField();
   JButton browseButton = new JButton("Browse");
   JTextField largestEntryCountField = new JTextField("10");
   JComboBox<ExportDateFormat> dateDropdown = new JComboBox<ExportDateFormat>(ExportDateFormat.values());
   JButton exportButton = new JButton("Export");
   JButton cancelButton = new JButton("Cancel");
   JComboBox<ExportSizeFormatType> sizeFormatDropdown = new JComboBox<ExportSizeFormatType>(ExportSizeFormatType.values());
   
   private boolean okPressed = false;
 
   /**
    * Constructor, builds the dialog, the controls, and 
    * wires it all together.
    */
   public ExportOptionsDialog()
   {
      super();
      setTitle("Folder Size Report Options");
      setModal(true);
      setContentPane(createContentPanel());
      setupControls();
      getRootPane().setDefaultButton(exportButton);
      pack();
      setSize(500, getHeight());
   }
   
   /**
    * Returns the selected options the user made within the 
    * constructed ExportOptionsModel, or null if cancel was 
    * pressed on the window. 
    * 
    * @return
    *    The ExportOptionsModel set with the user's selections if 
    *    Export was pressed, or null if cancel was pressed.
    */
   public ExportOptionsModel getOptions()
   {
      if (okPressed)
      {
         ExportOptionsModel optionsModel = new ExportOptionsModel();
         optionsModel.setFormat(formatDropdown.getItemAt(formatDropdown.getSelectedIndex()));
         optionsModel.setSaveToPath(filepathField.getText());
         optionsModel.setDateFormat(dateDropdown.getItemAt(dateDropdown.getSelectedIndex()));
         optionsModel.setNumberOfEntriesForLargestList(Integer.parseInt(largestEntryCountField.getText()));
         optionsModel.setSizeFormatter(sizeFormatDropdown.getItemAt(sizeFormatDropdown.getSelectedIndex()));
         
         return optionsModel;
      }
      else
      {
         return null;
      }
   }

   @Override
   public void actionPerformed(ActionEvent e)
   {
      if (e.getSource().equals(cancelButton))
      {
         okPressed = false;
         setVisible(false);
      }
      else if (e.getSource().equals(exportButton))
      {
         if (verifyInput())
         {
            okPressed = true;
            setVisible(false);
         }
      }
      else if (e.getSource().equals(browseButton))
      {
         browse();
      }

   }
   
   /**
    * 
    */
   private void browse()
   {
      JFileChooser fileChooser = null;
      
      // Make the file chooser open the file in the text box 
      // if the textbox contains a path to a file, or to the
      // parent directory if a file path is in the text box.
      if (!filepathField.getText().trim().isEmpty())
      {
         File f = new File(filepathField.getText().trim());
         // Make the file chooser setting the default path to 
         // the directory found with the above logic
         if (f.isFile())
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
            
      // Change the Approve option text from the default "open" to 
      // Select for better user experience.
      fileChooser.setApproveButtonText("Select");
      
      fileChooser.setFileFilter(new FileFilter()
      {
         @Override
         public String getDescription()
         {
            ExportFormatType format = getSelectedFormatType();
            return String.format("%s (%s)", format.getDisplayText(), format.getExtension());
         }
         
         @Override
         public boolean accept(File f)
         {
            String extension = getSelectedFormatType().getExtension().toUpperCase();
            return f.isDirectory() || f.getAbsolutePath().toUpperCase().endsWith(extension);
         }
         
         private ExportFormatType getSelectedFormatType()
         {
            if (formatDropdown.getSelectedIndex() > -1)
            {
               return (ExportFormatType)formatDropdown.getSelectedItem();
            }
            return ExportFormatType.HTML;
         }
      });
      
      // Show the dialog, and store the result of what button they selected
      int option = fileChooser.showSaveDialog(this.getParent());
      
      // Select/OK pressed, set the text in the text field to the selected
      // directory from the dialog.
      if(option == JFileChooser.APPROVE_OPTION)
      {
         filepathField.setText(fileChooser.getSelectedFile().getAbsolutePath());
      }
   }

   private boolean verifyInput()
   {
      if (formatDropdown.getSelectedIndex() < 0)
      {
         formatDropdown.requestFocusInWindow();
         return false;
      }
      
      if (filepathField.getText().trim().length() == 0)
      {
         filepathField.requestFocusInWindow();
         return false;
      }
      
      if (dateDropdown.getSelectedIndex() < 0)
      {
         dateDropdown.requestFocusInWindow();
         return false;
      }
      
      if (largestEntryCountField.isEnabled() && largestEntryCountField.getText().trim().length() == 0)
      {
         largestEntryCountField.requestFocusInWindow();
         return false;
      }
      
      if (sizeFormatDropdown.isEnabled() && sizeFormatDropdown.getSelectedIndex() < 0)
      {
         sizeFormatDropdown.requestFocusInWindow();
         return false;
      }
      
      return true;
   }
   
   private void setupControls()
   {
      browseButton.addActionListener(this);
      exportButton.addActionListener(this);
      cancelButton.addActionListener(this);
      
      PlainDocument numDoc = new PlainDocument() {
         private static final long serialVersionUID = -2360845120272116043L;

         @Override public void insertString(int offs, String str, javax.swing.text.AttributeSet a) throws javax.swing.text.BadLocationException 
         {
            try
            {
               Integer.parseInt(str);
               super.insertString(offs, str, a);
            }
            catch (Exception ex)
            {;}
         };
      };
      largestEntryCountField.setDocument(numDoc);
      largestEntryCountField.setText("10");
      
      formatDropdown.setSelectedItem(ExportFormatType.HTML);
      dateDropdown.setSelectedItem(ExportDateFormat.MONTH_DAY_YEAR);
      sizeFormatDropdown.setSelectedItem(ExportSizeFormatType.WITH_SEPERATORS);
      
   }
   
   private JPanel createContentPanel()
   {
      JPanel pane = new JPanel(true);
      pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));
      
      pane.add(createRowPanel("Report Format:", formatDropdown));
      formatDropdown.addItemListener(this);
      
      JPanel pathPanel = new JPanel(new BorderLayout(5, 5));
      pathPanel.add(filepathField, BorderLayout.CENTER);
      pathPanel.add(browseButton, BorderLayout.EAST);      
      pane.add(createRowPanel("Save Location", pathPanel));
      pane.add(" ", new JLabel(" "));
      pane.add(createRowPanel("Date Format:", dateDropdown));
      pane.add(createRowPanel("Size Format", sizeFormatDropdown));
      pane.add(createRowPanel("# of Largest Items to List", largestEntryCountField));
      
      JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 5));
      buttonPanel.add(exportButton);
      buttonPanel.add(cancelButton);      
      pane.add(createRowPanel("", buttonPanel));
      pane.setBorder(new EmptyBorder(5, 0, 0, 0));
      return pane;
   }
   
   private JPanel createRowPanel(String labelText, JComponent control)
   {
      JPanel pane = new JPanel(new BorderLayout(5, 5));
      pane.setBorder(new EmptyBorder(2, 5, 2, 5));
      JLabel lbl = new JLabel(labelText);
      lbl.setPreferredSize(new Dimension(150, lbl.getFontMetrics(lbl.getFont()).getHeight() + 10));
      pane.add(lbl, BorderLayout.WEST);
      
      pane.add(control, BorderLayout.CENTER);
      return pane;
   }

   @Override
   public void itemStateChanged(ItemEvent e)
   {
      if (formatDropdown.equals(e.getSource()))
      {
         boolean enableStuff = !ExportFormatType.CSV.equals(formatDropdown.getSelectedItem());
         largestEntryCountField.setEnabled(enableStuff);
         sizeFormatDropdown.setEnabled(enableStuff);
         
         if (!enableStuff)
         {
            largestEntryCountField.setText("");
            sizeFormatDropdown.setSelectedItem(ExportSizeFormatType.PLAIN);
         }
      }
      
   }
   
   
}
