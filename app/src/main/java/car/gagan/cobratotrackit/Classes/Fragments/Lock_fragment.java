package car.gagan.cobratotrackit.Classes.Fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


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
public class Lock_fragment extends BaseFragmentHome implements View.OnClickListener
{

    private final String LOCK_URL = Global_Constants.URL + "Gateway/SendLockDoorsMessage?UnitID=";
    private final String UNLOCK_URL = Global_Constants.URL + "Gateway/SendUnlockDoorsMessage?UnitID=";

    private final String DISARM_URL = Global_Constants.URL + "Gateway/SendDisarmImmobilizer?UnitID=";

    private Button btnLock, btnUnLock, btnDisArm;

    private Dialog dialog;

    public Lock_fragment()
    {
        // Required empty public constructor
    }

    public static View vLock = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        if (vLock == null)
            vLock = inflater.inflate(R.layout.fragment_lock, container, false);

        btnDisArm = (Button) vLock.findViewById(R.id.btnDisArm);
        btnLock = (Button) vLock.findViewById(R.id.btnLock);
        btnUnLock = (Button) vLock.findViewById(R.id.btnUnLock);
        btnLock.setOnClickListener(this);
        btnUnLock.setOnClickListener(this);
        btnDisArm.setOnClickListener(this);

        attachFragmentVehicleInfo();
        return vLock;

    }

    @Override
    public void onClick(View view)
    {

        dialog = initDialogG();
        dialog.show();

        switch (view.getId())
        {
            case R.id.btnLock:

                new SuperWebServiceG(LOCK_URL + getUnitID(), new HashMap<String, String>(), new CallBackWebService()
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

            case R.id.btnUnLock:

                new SuperWebServiceG(UNLOCK_URL + getUnitID(), new HashMap<String, String>(), new CallBackWebService()
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
            case R.id.btnDisArm:

//                http://112.196.34.42:8091/Gateway/SendDisarmImmobilizer?UnitID=0355255040459043


                new SuperWebServiceG(DISARM_URL + getUnitID(), new HashMap<String, String>(), new CallBackWebService()
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

    @Override
    public void onResume()
    {



        super.onResume();
    }


    //    --For Door Lock
//    URL : http://112.196.34.42:8091/Gateway/SendLockDoorsMessage?UnitID=0355255040459043
//    Method : GET
//        OUTPUT
//    {"Status":"success","Message":"3456"}
//    OR
//    {"Status":"error","Message":"Error Message"}
//
//    --For Door Unlock
//    URL : http://112.196.34.42:8091/Gateway/SendUnlockDoorsMessage?UnitID=0355255040459043
//    Method : GET
//        OUTPUT
//    {"Status":"success","Message":"2356"}
//    OR
//    {"Status":"error","Message":"Error Message"}


}
