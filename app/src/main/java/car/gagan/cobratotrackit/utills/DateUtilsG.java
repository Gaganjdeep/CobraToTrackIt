package car.gagan.cobratotrackit.utills;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * Created by gagandeep on 17 Nov 2015.
 */
public class DateUtilsG
{


    public static String getStartDate(int daysBack)
    {
        Calendar cal = GregorianCalendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.DAY_OF_YEAR, -daysBack);
        return sdf.format(cal.getTime());

    }

    public static String getCurrentDateWithAddedDays(int daysBack)
    {
        Calendar cal = GregorianCalendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.DAY_OF_YEAR, +daysBack);
        return sdf.format(cal.getTime());

    }

    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    public static String getCurrentDate()
    {
        Calendar c = Calendar.getInstance();

        return sdf.format(c.getTime());

    }


    public static String dateToString(String date)
    {

        SimpleDateFormat sdfs = new SimpleDateFormat(Global_Constants.SERVERTIME_FORMAT, Locale.US);


        Date date_ = null;
        try
        {
            date_ = sdfs.parse(date);


            return sdf.format(date_);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return "";
        }
    }


    public static String dateToStringAddOneDay(String date)
    {
        SimpleDateFormat sdfs = new SimpleDateFormat(Global_Constants.SERVERTIME_FORMAT, Locale.US);

        Date date_ = null;
        try
        {
            date_ = sdfs.parse(date);

            Calendar cal = GregorianCalendar.getInstance();
            cal.setTime(date_);
            cal.add(Calendar.DAY_OF_YEAR, +1);
            return sdf.format(cal.getTime());

        }
        catch (Exception e)
        {
            e.printStackTrace();
            return "";
        }
    }


}
