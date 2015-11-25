package car.gagan.cobratotrackit.Classes.Fragments;

import android.app.Dialog;
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
public class Engine_fragment extends BaseFragmentHome implements View.OnClickListener
{
    private Button btnEngineOn, btnENgineOff;


    private final String ENGINE_START_URL = Global_Constants.URL + "Gateway/SendStartEngineMessage?UnitID=";
    private final String ENGINE_STOP_URL = Global_Constants.URL + "Gateway/SendStopEngineMessage?UnitID=";


    private Dialog dialog;

    public Engine_fragment()
    {
        // Required empty public constructor
    }

    public static View vEngine = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {

        if (vEngine == null)
            vEngine = inflater.inflate(R.layout.fragment_alarm, container, false);


        btnEngineOn = (Button) vEngine.findViewById(R.id.btnEngineOn);
        btnENgineOff = (Button) vEngine.findViewById(R.id.btnENgineOff);
        btnEngineOn.setOnClickListener(this);
        btnENgineOff.setOnClickListener(this);

        attachFragmentVehicleInfo();
        return vEngine;

    }

    @Override
    public void onClick(View view)
    {
        dialog = initDialogG();
        dialog.show();


        switch (view.getId())
        {
            case R.id.btnEngineOn:
                SuperWebServiceG.cancelPrevious();
                new SuperWebServiceG(ENGINE_START_URL + getUnitID(), new HashMap<String, String>(), new CallBackWebService()
                {
                    @Override
                    public void webOnFinish(String output)
                    {

                        if (dialog != null && dialog.isShowing())
                            dialog.dismiss();

                        showResponse(output);



                    }
                }).execute();

                break;

            case R.id.btnENgineOff:
                SuperWebServiceG.cancelPrevious();
                new SuperWebServiceG(ENGINE_STOP_URL + getUnitID(), new HashMap<String, String>(), new CallBackWebService()
                {
                    @Override
                    public void webOnFinish(String output)
                    {


                        if (dialog != null && dialog.isShowing())
                            dialog.dismiss();


                        showResponse(output);


                    }
                }).execute();

                break;


        }
    }
}
