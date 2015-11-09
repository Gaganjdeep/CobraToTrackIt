package car.gagan.cobratotrackit.utills;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;


import car.gagan.cobratotrackit.Classes.Fragments.VehicleInfofragment;
import car.gagan.cobratotrackit.R;

/**
 * Created by gagandeep on 20/10/15.
 */
public class BaseFragmentHome extends Fragment
{


    public String getUnitID()
    {
        SharedPreferences shrdPref = getActivity().getSharedPreferences(Global_Constants.shared_pref_name, Context.MODE_PRIVATE);

        return shrdPref.getString(Global_Constants.DEVICEIMEI, "");
    }


    public String getVehicleID()
    {
        SharedPreferences shrdPref = getActivity().getSharedPreferences(Global_Constants.shared_pref_name, Context.MODE_PRIVATE);

        return shrdPref.getString(Global_Constants.VEHICLEID, "454");
    }


    public Dialog initDialogG()
    {
        Dialog dialog = new Dialog(getActivity(), R.style.Theme_Dialog);
        dialog.setContentView(R.layout.progress_dialog);

        return dialog;
    }


    public String selectedLanguage()
    {
        SharedPreferences preference = getActivity().getSharedPreferences("Preference", Context.MODE_PRIVATE);
        return preference.getString("language", "en");
    }


    public void attachFragmentVehicleInfo()
    {
        FragmentManager fragmentManager = getChildFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.layoutvehicleInfo, new VehicleInfofragment()).commit();
    }


}
