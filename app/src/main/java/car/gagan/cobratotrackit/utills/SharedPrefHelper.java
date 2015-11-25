package car.gagan.cobratotrackit.utills;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by gagandeep on 19 Nov 2015.
 */
public class SharedPrefHelper
{


    public static String updateDate = "updated_date";


    public static SharedPreferences getPrefs(Context context)
    {
        return context.getSharedPreferences(Global_Constants.SHAREDPREF_VEHICLE_INFO, Context.MODE_PRIVATE);
    }


    public static void editPref(Context context, String date)
    {
        SharedPreferences shrdPref = context.getSharedPreferences(Global_Constants.SHAREDPREF_VEHICLE_INFO, Context.MODE_PRIVATE);
        SharedPreferences.Editor edt = shrdPref.edit();
        edt.putString(updateDate, date);
        edt.apply();
    }


}
