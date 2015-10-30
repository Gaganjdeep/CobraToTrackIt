package car.gagan.cobratotrackit.Classes.Fragments;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


import org.json.JSONException;
import org.json.JSONObject;

import car.gagan.cobratotrackit.R;

import java.util.HashMap;

import car.gagan.cobratotrackit.utills.BaseFragmentHome;
import car.gagan.cobratotrackit.utills.CallBackWebService;
import car.gagan.cobratotrackit.utills.Global_Constants;
import car.gagan.cobratotrackit.utills.Utills_G;
import car.gagan.cobratotrackit.webservice.SuperWebServiceG;

/**
 * Created by gagandeep on 19/10/15.
 */
public class Tracking_fragment extends BaseFragmentHome implements View.OnClickListener
{


    private final String START_TRACKING_URL = Global_Constants.URL + "Gateway/SendStartTrackingMessage?UnitID=";
    private final String STOP_TRACKING_URL = Global_Constants.URL + "Gateway/SendStopTrackingMessage?UnitID=";


    private Button btnStoptracking, btnStartTracking;

    private Dialog dialog;

    private static View v = null;

    public Tracking_fragment()
    {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        if (v == null)
            v = inflater.inflate(R.layout.fragment_tracking, container, false);


        btnStoptracking = (Button) v.findViewById(R.id.btnStoptracking);
        btnStartTracking = (Button) v.findViewById(R.id.btnStartTracking);
        btnStartTracking.setOnClickListener(this);
        btnStoptracking.setOnClickListener(this);


        return v;

    }


    @Override
    public void onClick(View view)
    {

        dialog = initDialogG();
        dialog.show();


        switch (view.getId())
        {
            case R.id.btnStartTracking:

                new SuperWebServiceG(START_TRACKING_URL + getUnitID(), new HashMap<String, String>(), new CallBackWebService()
                {
                    @Override
                    public void webOnFinish(String output)
                    {


                        if (dialog != null && dialog.isShowing())
                            dialog.dismiss();


                        try
                        {
                            JSONObject jObj = new JSONObject(output);

                            new Utills_G().show_dialog_msg(getActivity(), jObj.getString(Global_Constants.Message), null);


                        }
                        catch (Exception e)
                        {
                            e.printStackTrace();
                        }



                    }
                }).execute();

                break;

            case R.id.btnStoptracking:

                new SuperWebServiceG(STOP_TRACKING_URL + getUnitID(), new HashMap<String, String>(), new CallBackWebService()
                {
                    @Override
                    public void webOnFinish(String output)
                    {
                        if (dialog != null && dialog.isShowing())
                            dialog.dismiss();


                        try
                        {
                            JSONObject jObj = new JSONObject(output);

                            new Utills_G().show_dialog_msg(getActivity(), jObj.getString(Global_Constants.Message), null);


                        }
                        catch (Exception e)
                        {
                            e.printStackTrace();
                        }


                    }
                }).execute();

                break;


        }
    }


//    --For Start Tracking
//    URL : http://112.196.34.42:8091/Gateway/SendStartTrackingMessage?UnitID=0355255040459043
//    Method : GET
//        OUTPUT
//    {"Status":"success","Message":"3456"}
//    OR
//    {"Status":"error","Message":"Error Message"}
//
//    --For Stop Tracking
//    URL : http://112.196.34.42:8091/Gateway/SendStopTrackingMessage?UnitID=0355255040459043
//    Method : GET
//        OUTPUT
//    {"Status":"success","Message":"3456"}
//    OR
//    {"Status":"error","Message":"Error Message"}


}
