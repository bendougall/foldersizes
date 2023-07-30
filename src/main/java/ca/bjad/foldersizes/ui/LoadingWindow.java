package ca.bjad.foldersizes.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Frame;
import java.awt.GridLayout;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JWindow;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

/**
 * Loading Window to display while operationgs in the 
 * app are executing (such as directory information being 
 * retrieved from the file system)
 *
 * @author 
 *   Ben Dougall
 */
public class LoadingWindow extends JWindow
{
   private static final long serialVersionUID = 6160557082650935329L;
   
   /**
    * The label showing the text to the user.
    */
   protected JLabel textLabel = new JLabel();
   /**
    * The label showing the loading GIF to the user.
    */
   protected JLabel imageLabel = new JLabel();
   
   /**
    * Constructor, building the controls on the window and 
    * setting the size/location relative to the component provided.
    * 
    * @param owner
    *    The owner of the window
    * @param text
    *    The text to show in the window along with the loading 
    *    graphic.
    */
   public LoadingWindow(Frame owner, String text)
   {
      super(owner);
      
      // Set up the text label.
      textLabel.setText(text);
      textLabel.setHorizontalAlignment(SwingConstants.CENTER);
      textLabel.setFont(textLabel.getFont().deriveFont(18.0f));
      
      // Setup the image label.
      imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
      imageLabel.setHorizontalTextPosition(SwingConstants.CENTER);
      imageLabel.setIcon(new ImageIcon(getClass().getResource("/resources/black-circle-loading.gif")));
      
      // Build the control panel.
      JPanel controlPane = new JPanel(new GridLayout(2, 1, 5, 5), true);
      controlPane.setBorder(new EmptyBorder(7, 10, 7, 10));
      controlPane.add(textLabel);
      controlPane.add(imageLabel);
      
      // Build the content panel
      JPanel contentPane = new JPanel(new BorderLayout(), true);
      contentPane.add(controlPane, BorderLayout.CENTER);
      contentPane.setBorder(new LineBorder(Color.BLACK, 3));
      setContentPane(contentPane);
      
      // Set the size based on the text displayed in the window.
      FontMetrics fm = textLabel.getFontMetrics(textLabel.getFont());
      setSize(fm.stringWidth(textLabel.getText()) + 140, 125);
   }
}
