package ca.bjad.foldersizes.delegate;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Delegate class used to provide the date string based on the 
 * current date and the date passed. 
 *
 * @author 
 *   Ben Dougall
 */
public class DateLabelDelegate
{
   private static final DateFormat FORMATTER = 
         new SimpleDateFormat("MMM dd, YYYY HH:mm a");
   
   /**
    * Number of milliseconds within an hour. 
    */
   private static final long HOUR_RANGE = 1000 * 60 * 60;
   
   /**
    * Number of milliseconds within a day
    */
   private static final long DAY_RANGE = HOUR_RANGE * 24;
   
   /**
    * Number of milliseconds within a week
    */
   private static final long WEEK_RANGE = DAY_RANGE * 7;
   
   /**
    * Number of milliseconds within a 30 day month
    */
   private static final long MONTH_30_DAY_RANGE = DAY_RANGE * 30;
   
   /**
    * Number of milliseconds within a year
    */ 
   private static final long YEAR_RANGE = DAY_RANGE * 365;
   
   /**
    * Returns the formatted string of the epoch date value passed 
    * to the function. 
    * 
    * @param epochTime
    *    The epoch date value. 
    * @param showDateOnly
    *    True to show the formatted date string only, false to show 
    *    the modified time range as well as the formatted date.
    * @return
    *    The string of the epoch date passed.
    */
   public static String getDateLabel(long epochTime, boolean showDateOnly)
   {
      return getDateLabel(new Date(epochTime), showDateOnly);
   }
   
   /**
    * Returns the formatted string of the date passed to the function.
    * 
    * @param date
    *    The date to format.
    * @param showDateOnly
    *    True to show the formatted date string only, false to show 
    *    the modified time range as well as the formatted date.
    * @return
    *    The string of the date passed.
    */
   public static String getDateLabel(Date date, boolean showDateOnly)
   {
      String formattedDate = FORMATTER.format(date); 
      String retValue = "";
      if (!showDateOnly)
      {
         long millisSinceModification = System.currentTimeMillis() - date.getTime();
         if (millisSinceModification <= HOUR_RANGE)
         {
            retValue = "Within the past hour";
         }
         else if (millisSinceModification <= DAY_RANGE)
         {
            retValue = "Within the past day";
         }
         else if (millisSinceModification <= WEEK_RANGE)
         {
            retValue = "Within the past week";
         }
         else if (millisSinceModification <= MONTH_30_DAY_RANGE)
         {
            retValue = "Within the past month";
         }
         else if (millisSinceModification <= YEAR_RANGE)
         {
            retValue = "Within the past year";
         }
         else 
         {
            retValue = "Over a year ago";
         }
      }
      return showDateOnly ? 
            formattedDate : String.format("%s (%s)", retValue, formattedDate);
   }
}
