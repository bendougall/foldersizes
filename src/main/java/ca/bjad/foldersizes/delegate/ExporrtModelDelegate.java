package ca.bjad.foldersizes.delegate;

import java.io.File;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import ca.bjad.foldersizes.model.DirectoryInfo;
import ca.bjad.foldersizes.model.ExportDateFormat;
import ca.bjad.foldersizes.model.ExportDirectoryModel;
import ca.bjad.foldersizes.model.ExportExtensionModel;
import ca.bjad.foldersizes.model.ExportFileModel;
import ca.bjad.foldersizes.model.ExportModel;
import ca.bjad.foldersizes.model.ExportOptionsModel;
import ca.bjad.foldersizes.model.ExportSizeFormatType;

/**
 * Delegate used to create the export model's "top" 
 * results. 
 *
 * @author 
 *   Ben Dougall
 */
public class ExporrtModelDelegate
{
   /**
    * Creates the export model based on the directory information 
    * provided to it. 
    * 
    * @param directory
    *    The directory information to make the report from.
    * @param options
    *    The various options to apply to the exported report.
    * @return
    *    The generated export data model to generate XML from.
    */
   public static ExportModel createExportModel(DirectoryInfo directory, ExportOptionsModel options)
   {
      SimpleDateFormat sdf = new SimpleDateFormat(options.getDateFormat().getFormatString());
      
      ExportModel model = new ExportModel();
      model.setDirectoryForResult(new ExportDirectoryModel(directory));
      model.setDateOfReport(new SimpleDateFormat(ExportDateFormat.MONTH_DAY_YEAR_HOUR_MM_AMPM.getFormatString()).format(new Date()));
      model.setNumberOfLargestEntries(options.getNumberOfEntriesForLargestList());
      
      // Find all the sibdirectories in the results, and get the largest subdirectories. 
      List<ExportDirectoryModel> subdirectories = getAllDirectories(directory, false);
      List<ExportFileModel> files = getFilesFromDirectory(new File(directory.getPath()));
      for (ExportDirectoryModel dir : subdirectories)
      {
         files.addAll(getFilesFromDirectory(new File(dir.getPath())));
      }
      
      model = sortAndSetLargestFileAndDirDataPoints(model, subdirectories, files, options.getNumberOfEntriesForLargestList());
      model = gatherAndSetExtensionData(model, files, options.getNumberOfEntriesForLargestList());
      
      // Format the dates within the file results
      for (ExportFileModel fileModel : model.getLargestFiles())
      {
         fileModel.setLastModifiedDate(sdf.format(new Date(fileModel.getModifiedDate())));
      }
      
      // Strip the base directory from the file paths in the report.
      model = stripBaseDirFromPathsInReport(model);
      
      // Format the sizes in the objects
      model = formatAmounts(model, options.getSizeFormatter());
      
      return model;
   }
   
   private static List<ExportDirectoryModel> getAllDirectories(DirectoryInfo directory, boolean sorted)
   {
      List<ExportDirectoryModel> directories = new ArrayList<ExportDirectoryModel>();
      for (DirectoryInfo info : directory.getSubdirectories())
      {
         directories.add(new ExportDirectoryModel(info));
         directories.addAll(getAllDirectories(info, false));
      }
      
      if (sorted)
      {
         Collections.sort(directories, new Comparator<ExportDirectoryModel>()
         {
            @Override
            public int compare(ExportDirectoryModel o1, ExportDirectoryModel o2)
            {
               return o2.getTotalSize().compareTo(o1.getTotalSize());
            }
         });
      }
      
      return directories;
   }
   
   private static List<ExportFileModel> getFilesFromDirectory(File directory)
   {
      List<ExportFileModel> files = new ArrayList<ExportFileModel>();
      for (File f : directory.listFiles())
      {
         if (f.isFile())
         {
            files.add(new ExportFileModel(f));
         }
      }
      return files;
   }
   
   private static ExportModel sortAndSetLargestFileAndDirDataPoints(ExportModel model, List<ExportDirectoryModel> subdirectories, List<ExportFileModel> files, int topResultCount)
   {
      List<ExportDirectoryModel> sortedSubDirectories = new ArrayList<ExportDirectoryModel>(subdirectories);
      Collections.sort(sortedSubDirectories, new Comparator<ExportDirectoryModel>()
      {
         @Override
         public int compare(ExportDirectoryModel o1, ExportDirectoryModel o2)
         {
            return o2.getTotalSize().compareTo(o1.getTotalSize());
         }
      });
      for (int index = 0; index < sortedSubDirectories.size(); ++index)
      {
         if (index < topResultCount)
         {
            model.getLargestDirectories().add(sortedSubDirectories.get(index));
         }
         model.getDirectories().add(sortedSubDirectories.get(index));
      }
      
      List<ExportFileModel> sortedFiles = new ArrayList<ExportFileModel>(files);
      Collections.sort(sortedFiles, new Comparator<ExportFileModel>()
      {
         @Override
         public int compare(ExportFileModel o1, ExportFileModel o2)
         {
            return Long.compare(o2.getFileSize(), o1.getFileSize());
         }
      });
      for (int index = 0; index < topResultCount && index < sortedFiles.size(); ++index)
      {
         model.getLargestFiles().add(sortedFiles.get(index));
      }
      
      return model;
   }
   
   private static ExportModel gatherAndSetExtensionData(ExportModel model, List<ExportFileModel> files, int topResultCount)
   {
      // Sort all the file entries by extension to make the extension summary
      // determination as quick as possible by dealing with one extension at a time
      List<ExportFileModel> sortedFiles = new ArrayList<ExportFileModel>(files);
      Collections.sort(sortedFiles, new Comparator<ExportFileModel>()
      {
         @Override
         public int compare(ExportFileModel o1, ExportFileModel o2)
         {
            return o1.getExtension().compareTo(o2.getExtension());
         }
      });
      
      // Store the last extension so we know when we have to make a new 
      // bean for a different one.
      String lastExtension = null;
      // Store a extension model external so we can create, update, and save to
      // the list when extensions are processed, and reset when a new extension is 
      // found.
      ExportExtensionModel exModel = null;
      List<ExportExtensionModel> extensionsList = new ArrayList<ExportExtensionModel>();
      
      for (ExportFileModel fileModel : sortedFiles)
      {
         // New extension? If so, if the model is created, save it off to the 
         // list and create a new model for the new extension.
         if (!fileModel.getExtension().equalsIgnoreCase(lastExtension))
         {
            if (exModel != null)
            {
               extensionsList.add(exModel);
            }
            exModel = new ExportExtensionModel(fileModel);
            lastExtension = fileModel.getExtension();
         }
         // Same extension as list time, add to the filesize in the model built 
         // when the extension was found. 
         else
         {
            exModel.addToTotalSize(fileModel);
         }
      }
      // Add the final extension model to the list.
      if (exModel != null)
      {
         extensionsList.add(exModel);
      }
      
      // Sort the extensions by size
      List<ExportExtensionModel> sortedExtensions = new ArrayList<ExportExtensionModel>(extensionsList);
      Collections.sort(sortedExtensions, new Comparator<ExportExtensionModel>()
      {
         @Override
         public int compare(ExportExtensionModel o1, ExportExtensionModel o2)
         {
            return o2.getTotalSize().compareTo(o1.getTotalSize());
         }
      });
      for (int index = 0; index < topResultCount && index < sortedExtensions.size(); ++index)
      {
         model.getLargestExtensions().add(sortedExtensions.get(index));
      }
      
      return model;
   }
   
   private static ExportModel stripBaseDirFromPathsInReport(ExportModel model)
   {
      for (ExportDirectoryModel dirModel : model.getLargestDirectories())
      {
         dirModel.setPath(dirModel.getPath().replace(model.getDirectoryForResult().getPath(), ""));
      }
      for (ExportFileModel fileModel : model.getLargestFiles())
      {
         fileModel.setPath(fileModel.getPath().replace(model.getDirectoryForResult().getPath(), ""));
      }
      return model;
   }
   
   
   private static ExportModel formatAmounts(ExportModel model, ExportSizeFormatType format)
   {
      model.getDirectoryForResult().setSize(formatSize(model.getDirectoryForResult().getTotalSize(), format));
      
      for (ExportDirectoryModel dir : model.getLargestDirectories())
      {
         dir.setSize(formatSize(dir.getTotalSize(), format));
      }
      
      for (ExportFileModel f : model.getLargestFiles())
      {
         BigInteger bi = new BigInteger(String.valueOf(f.getFileSize()));
         f.setSizeStr(formatSize(bi, format));
      }
      
      for (ExportExtensionModel x : model.getLargestExtensions())
      {
         x.setSize(formatSize(x.getTotalSize(), format));
      }
      
      for (ExportDirectoryModel dir : model.getDirectories())
      {
         dir.setSize(formatSize(dir.getTotalSize(), format));
      }
      
      return model;
   }
   
   private static String formatSize(BigInteger size, ExportSizeFormatType format)
   {
      switch (format)
      {
      case FRIENDLY_STRING:
         return SizeLabelDelegate.getUserFriendlyString(size);
      case WITH_SEPERATORS:
         return SizeLabelDelegate.getCommaedString(size);
      default:
         return size.toString();
      }
   }
}
