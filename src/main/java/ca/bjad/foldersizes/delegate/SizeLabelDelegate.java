package ca.bjad.foldersizes.delegate;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * Delegate that can be used to show file sizes in a user 
 * friendly format (aka 1.1 KB) instead of the pure number
 * format (1100).
 *
 *
 * @author 
 *   Ben Dougall
 */
public class SizeLabelDelegate
{
   private static final BigInteger KILOBYTE = new BigInteger("1024");
   private static final BigInteger MEGABYTE = KILOBYTE.multiply(KILOBYTE);
   private static final BigInteger GIGABYTE = KILOBYTE.multiply(MEGABYTE);   
   
   private static final int DECIMAL_PLACES = 3;
   
   /**
    * Provides the size text in a user friendly format ([size] [GB/MB/KB])
    * based on the value passed.
    * 
    * @param size
    *    The value to convert to the user friendly format.
    * @return
    *    The user friendly text for the size passed.
    */
   public static String getUserFriendlyString(BigInteger size)
   {
      BigInteger divisor = null;
      String suffix = "";
      
      if (size.compareTo(GIGABYTE) == 1)
      {
         divisor = GIGABYTE;
         suffix = "GB";
      }
      else if (size.compareTo(MEGABYTE) == 1)
      {
         divisor = MEGABYTE;
         suffix = "MB";
      }
      else if (size.compareTo(KILOBYTE) == 1)
      {
         divisor = KILOBYTE;
         suffix = "KB";
      }
      
      if (divisor != null)
      {
         return new BigDecimal(size).divide(new BigDecimal(divisor), DECIMAL_PLACES, RoundingMode.HALF_UP).toPlainString() + " " + suffix;
      }
      else
      {
         return size.toString() + " bytes";
      }
   }
   
   /**
    * Returns the size as a string formatted for the current locale
    * @param size
    *    The size to format.
    * @return
    *    The size as a string formatted for the current locale
    */
   public static String getCommaedString(BigInteger size)
   {
      return NumberFormat.getNumberInstance(Locale.getDefault()).format(size);
   }
}
