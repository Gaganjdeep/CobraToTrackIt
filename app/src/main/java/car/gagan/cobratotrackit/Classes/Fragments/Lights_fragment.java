package car.gagan.cobratotrackit.Classes.Fragments;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import org.json.JSONObject;

import java.util.HashMap;

import car.gagan.cobratotrackit.R;
import car.gagan.cobratotrackit.utills.BaseFragmentHome;
import car.gagan.cobratotrackit.utills.CallBackWebService;
import car.gagan.cobratotrackit.utills.Global_Constants;
import car.gagan.cobratotrackit.utills.Utills_G;
import car.gagan.cobratotrackit.webservice.SuperWebServiceG;

/**
 * Created by gagandeep on 19/10/15.
 */
public class Lights_fragment extends BaseFragmentHome implements View.OnClickListener
{


    private final String LIGHTS_ON_URL = Global_Constants.URL + "Gateway/SendBlinkingLightsMessage?UnitID=";
    private final String LIGHTS_OFF_URL = Global_Constants.URL + "Gateway/SendUnblinkingLightsMessage?UnitID=";


    private Button btnLightsOn, btnLightsOff;
    private Dialog dialog;

    public Lights_fragment()
    {
        // Required empty public constructor
    }

    public static View vLights = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        if (vLights == null)
            vLights = inflater.inflate(R.layout.fragment_light, container, false);


        btnLightsOn = (Button) vLights.findViewById(R.id.btnLightsOn);
        btnLightsOff = (Button) vLights.findViewById(R.id.btnLightsOff);
        btnLightsOn.setOnClickListener(this);
        btnLightsOff.setOnClickListener(this);

        attachFragmentVehicleInfo();

        return vLights;

    }


    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onClick(View view)
    {


        dialog = initDialogG();
        dialog.show();


        switch (view.getId())
        {
            case R.id.btnLightsOn:


                SuperWebServiceG.cancelPrevious();


                new SuperWebServiceG(LIGHTS_ON_URL + getUnitID(), new HashMap<String, String>(), new CallBackWebService()
                {
                    @Override
                    public void webOnFinish(String output)
                    {

                        if (dialog != null && dialog.isShowing())
                            dialog.dismiss();


                        try
                        {
                            JSONObject jObj = new JSONObject(output);

                            Utills_G.show_dialog_msg(getActivity(), jObj.getString(Global_Constants.Message), null);


                        }
                        catch (Exception e)
                        {
                            e.printStackTrace();
                        }


                    }
                }).execute();

                break;

            case R.id.btnLightsOff:

                SuperWebServiceG.cancelPrevious();
                new SuperWebServiceG(LIGHTS_OFF_URL + getUnitID(), new HashMap<String, String>(), new CallBackWebService()
                {
                    @Override
                    public void webOnFinish(String output)
                    {

                        if (dialog != null && dialog.isShowing())
                            dialog.dismiss();


                        try
                        {
                            JSONObject jObj = new JSONObject(output);

                            Utills_G.show_dialog_msg(getActivity(), jObj.getString(Global_Constants.Message), null);


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


//    --For Blinking Lights
//    URL : http://112.196.34.42:8091/Gateway/SendBlinkingLightsMessage?UnitID=0355255040459043
//    Method : GET
//        OUTPUT
//    {"Status":"success","Message":"3456"}
//    OR
//    {"Status":"error","Message":"Error Message"}
//
//    --For Unblinking Lights
//    URL : http://112.196.34.42:8091/Gateway/SendUnblinkingLightsMessage?UnitID=0355255040459043
//    Method : GET
//        OUTPUT
//    {"Status":"success","Message":"3456"}
//    OR
//    {"Status":"error","Message":"Error Message"}


}
