package car.gagan.cobratotrackit.utills;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by gagandeep on 5/11/15.
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

    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    public static String getCurrentDate()
    {
        Calendar c = Calendar.getInstance();

        return sdf.format(c.getTime());

    }


}
