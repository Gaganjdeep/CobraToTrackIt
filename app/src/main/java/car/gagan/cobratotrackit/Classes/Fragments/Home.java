package car.gagan.cobratotrackit.Classes.Fragments;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.transition.ChangeBounds;
import android.transition.Scene;
import android.transition.TransitionInflater;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;


import org.json.JSONObject;

import java.util.HashMap;

import car.gagan.cobratotrackit.Classes.Register_Screen;
import car.gagan.cobratotrackit.Classes.Verfication_Screen;
import car.gagan.cobratotrackit.R;

import car.gagan.cobratotrackit.Classes.MainActivity;
import car.gagan.cobratotrackit.model.VehicleInfo;
import car.gagan.cobratotrackit.utills.BaseFragmentHome;
import car.gagan.cobratotrackit.utills.CallBackDialogOption;
import car.gagan.cobratotrackit.utills.CallBackWebService;
import car.gagan.cobratotrackit.utills.DialogReveal;
import car.gagan.cobratotrackit.utills.Global_Constants;
import car.gagan.cobratotrackit.utills.ImageDownloader;
import car.gagan.cobratotrackit.utills.SharedPrefHelper;
import car.gagan.cobratotrackit.utills.StoreData;
import car.gagan.cobratotrackit.utills.Utills_G;
import car.gagan.cobratotrackit.webservice.SuperWebServiceG;

/**
 * A simple {@link Fragment} subclass.
 */
public class Home extends BaseFragmentHome
{


    public Home()
    {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {

        View v = inflater.inflate(R.layout.fragment_home, container, false);


        parentlayout = (FrameLayout) v.findViewById(R.id.parentlayout);

        settingSwipeToRefresh(v);


        displayView(new Lock_fragment());


        return v;

    }


    FrameLayout parentlayout;
    CallBackDialogOption callBack = new CallBackDialogOption()
    {
        @Override
        public void optionSelected(android.support.v4.app.Fragment fragmnt)
        {

            displayView(fragmnt);
        }
    };


    private void onRefreshG(final SwipeRefreshLayout swipeLayout)
    {
        Utills_G.showToast(getActivity().getResources().getString(R.string.updating_info), getActivity(), true);

//        swipeLayout.setRefreshing(true);

        String url = String.format("%sGateway/GetVehicleInfo?VehicleId=%s", Global_Constants.URL, getVehicleID());

        new SuperWebServiceG(url, new HashMap<String, String>(), new CallBackWebService()
        {
            @Override
            public void webOnFinish(String output)
            {

                try
                {


                    JSONObject jObj = new JSONObject(output);

                    if (jObj.getString(Global_Constants.Status).equals(Global_Constants.success))
                    {

                        JSONObject jobjinner = new JSONObject(jObj.getString(Global_Constants.Message));

                        StoreData.write(getActivity(), new VehicleInfo(jobjinner.optString("ModelName"), jobjinner.optString("ManufactorName"), jobjinner.optString("LastUpdatedOn"), jobjinner.optString("VehicleImageURL"), jobjinner.optString("LicensePlate")));


                        SharedPrefHelper.editPref(getActivity(), jobjinner.optString("LastUpdatedOn"));

                    }

                }
                catch (Exception | Error e)
                {

                    e.printStackTrace();
                }

                swipeLayout.setRefreshing(false);


            }
        }).execute();
    }

    private void settingSwipeToRefresh(View v)
    {

        final SwipeRefreshLayout swipeLayout = (SwipeRefreshLayout) v.findViewById(R.id.swipeLayout);
        swipeLayout.setColorSchemeResources(
                R.color.red,
                R.color.grey,
                R.color.red);

        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener()
        {
            @Override
            public void onRefresh()
            {


                onRefreshG(swipeLayout);

            }
        });

    }


//    private void settingToolBar(View v)
//    {
//
//        Toolbar toolbar = (Toolbar) v.findViewById(R.id.toolbar);
//        ImageButton btnOptions = (ImageButton) toolbar.findViewById(R.id.btnRefresh);
//        btnOptions.setVisibility(View.VISIBLE);
//        btnOptions.setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View view)
//            {
//
//                Utills_G.showToast(getActivity().getResources().getString(R.string.updating_info), getActivity(), true);
//
//
//                String url = String.format("%sGateway/GetVehicleInfo?VehicleId=%s", Global_Constants.URL, getVehicleID());
//
//                new SuperWebServiceG(url, new HashMap<String, String>(), new CallBackWebService()
//                {
//                    @Override
//                    public void webOnFinish(String output)
//                    {
//
//                        try
//                        {
//
//
//                            JSONObject jObj = new JSONObject(output);
//
//                            if (jObj.getString(Global_Constants.Status).equals(Global_Constants.success))
//                            {
//
//                                JSONObject jobjinner = new JSONObject(jObj.getString(Global_Constants.Message));
//
//                                StoreData.write(getActivity(), new VehicleInfo(jobjinner.optString("ModelName"), jobjinner.optString("ManufactorName"), jobjinner.optString("LastUpdatedOn"), jobjinner.optString("VehicleImageURL"), jobjinner.optString("LicensePlate")));
//
//
//                                SharedPrefHelper.editPref(getActivity(), jobjinner.optString("LastUpdatedOn"));
//
//                            }
//
//                        }
//                        catch (Exception | Error e)
//                        {
//
//                            e.printStackTrace();
//                        }
//
//
//                    }
//                }).execute();
//            }
//        });
//    }


    private void displayView(android.support.v4.app.Fragment fragment)
    {


        if (fragment != null)
        {
            FragmentManager fragmentManager = getChildFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.parentlayout, fragment).commit();


        }

    }


    @Override
    public void onResume()
    {
        super.onResume();

        if (!mIsReceiverRegistered)
        {
            if (mReceiver == null)
                mReceiver = new OpenDialogOptionBroadcastReceiverG();
            getActivity().registerReceiver(mReceiver, new IntentFilter(BROADCAST_OPENDIALOG));
            mIsReceiverRegistered = true;
        }


    }


    public static final String BROADCAST_OPENDIALOG = "gagan.open.dialog";


    @Override
    public void onPause()
    {
        super.onPause();
        if (mIsReceiverRegistered)
        {
            getActivity().unregisterReceiver(mReceiver);
            mReceiver = null;
            mIsReceiverRegistered = false;
        }
    }


    OpenDialogOptionBroadcastReceiverG mReceiver;
    private boolean mIsReceiverRegistered = false;


    private class OpenDialogOptionBroadcastReceiverG extends BroadcastReceiver
    {

        @Override
        public void onReceive(Context context, Intent intent)
        {
            DialogReveal dialogReveal = new DialogReveal(getActivity(), callBack);
            dialogReveal.show();
            dialogReveal.setOnDismissListener(dialogReveal.dismissListener);


        }
    }


    public static void reStartFragments()
    {

        Lock_fragment.vLock = null;

        Lights_fragment.vLights = null;

        Engine_fragment.vEngine = null;

        Tracking_fragment.vTracking = null;
    }


}
