package car.gagan.cobratotrackit.Classes.Fragments;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import car.gagan.cobratotrackit.Classes.Register_Screen;
import car.gagan.cobratotrackit.Classes.Splash_Cobra;
import car.gagan.cobratotrackit.R;

import car.gagan.cobratotrackit.Classes.MainActivity;
import car.gagan.cobratotrackit.utills.Global_Constants;
import car.gagan.cobratotrackit.utills.Utills_G;

/**
 * A simple {@link Fragment} subclass.
 */
public class Settings extends android.support.v4.app.Fragment implements View.OnClickListener
{


    private LinearLayout layoutLogout, layoutSelectLang;


    public Settings()
    {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_settings, container, false);

        layoutLogout = (LinearLayout) v.findViewById(R.id.layoutLogout);
        layoutSelectLang = (LinearLayout) v.findViewById(R.id.layoutSelectLang);

        layoutLogout.setOnClickListener(this);
        layoutSelectLang.setOnClickListener(this);


        return v;
    }


    @Override
    public void onResume()
    {

        super.onResume();
    }

    @Override
    public void onClick(View view)
    {

        switch (view.getId())
        {

            case R.id.layoutLogout:

                Utills_G.show_dialog_msg(getActivity(), getResources().getString(R.string.do_u_want_to_logout), new View.OnClickListener()
                {
                    @Override
                    public void onClick(View view)
                    {
                        Utills_G.global_dialog.dismiss();


                        SharedPreferences shrdpref = getActivity().getSharedPreferences(Global_Constants.shared_pref_name, Context.MODE_PRIVATE);
                        SharedPreferences.Editor ed = shrdpref.edit();
                        ed.clear();
                        ed.apply();


                        startActivity(new Intent(getActivity(), Splash_Cobra.class));
                        getActivity().finish();


                    }
                });


                break;


            case R.id.layoutSelectLang:


                Utills_G.show_dialog_languageSelection(getActivity());


                break;

        }


    }
}
