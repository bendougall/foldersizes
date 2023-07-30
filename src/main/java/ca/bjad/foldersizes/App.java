package ca.bjad.foldersizes;

import java.io.File;

import javax.swing.SwingUtilities;

import ca.bjad.foldersizes.ui.MainWindow;

/**
 * Entry point to the application. 
 *
 * @author 
 *   Ben Dougall
 */
public class App implements Runnable
{
   private File startingDirectory = null;
   
   /**
    * Launch point for the application.
    * 
    * @param args
    *    First argument can be the directory the application will 
    *    examine if its a path that represents a directory the application 
    *    can read.
    */
   public static void main(String[] args)
   {       
      // Build the app class, and attempt to use the 
      // first argument (if present) as the starting 
      // directory for the application. 
      App a = new App();
      if (args.length > 0)
      {
         File f = new File(args[0]);
         if (f.isDirectory())
         {
            a.startingDirectory = f;
         }
      }
      
      // Start the application's UI.
      SwingUtilities.invokeLater(a);
   }

   @Override
   public void run()
   {
      MainWindow window;
      // Define the starting directory property for the window
      // if its set.
      if (startingDirectory != null)
      {
         window = new MainWindow(startingDirectory);
      }
      else
      {
         window = new MainWindow();
      }
      
      // Show the window to the user. 
      window.setVisible(true);
   }
}
