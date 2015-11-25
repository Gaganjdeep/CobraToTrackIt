package car.gagan.cobratotrackit.Classes.Fragments;


import android.content.res.ColorStateList;
import android.os.Bundle;
import android.app.Fragment;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.HashMap;

import car.gagan.cobratotrackit.Classes.MainActivity;
import car.gagan.cobratotrackit.R;
import car.gagan.cobratotrackit.utills.BaseFragmentHome;
import car.gagan.cobratotrackit.utills.CallBackWebService;
import car.gagan.cobratotrackit.utills.Global_Constants;
import car.gagan.cobratotrackit.utills.Utills_G;
import car.gagan.cobratotrackit.webservice.SendGetPositionMessage;
import car.gagan.cobratotrackit.webservice.SuperWebServiceG;

/**
 * A simple {@link Fragment} subclass.
 */
public class LocationFragment extends BaseFragmentHome
{

    private static View rootView;
    private MapFragment mapFragment;


    private TextView txtv_addressLocation;

    private LinearLayout layoutShowRoute;
    FloatingActionButton btnEngineStatus, btnbatteryStatus;


    public LocationFragment()
    {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        if (rootView != null)
        {
            ViewGroup parent = (ViewGroup) rootView.getParent();
            if (parent != null)
                parent.removeView(rootView);
        }
        try
        {
            rootView = inflater.inflate(R.layout.fragment_location, container, false);
        }
        catch (Exception | Error e)
        {
        }


        findVIewById(rootView);


        initializeMapFragment();


        return rootView;
    }


    private void findVIewById(View view)
    {
        txtv_addressLocation = (TextView) view.findViewById(R.id.txtv_addressLocation);
        layoutShowRoute = (LinearLayout) view.findViewById(R.id.layoutShowRoute);
        btnEngineStatus = (FloatingActionButton) view.findViewById(R.id.btnEngineStatus);
        btnbatteryStatus = (FloatingActionButton) view.findViewById(R.id.btnbatteryStatus);

        settingSwipeToRefresh(view);

    }


    @Override
    public void onResume()
    {


        new SendGetPositionMessage(getUnitID()).execute();


        super.onResume();
    }

    private void initializeMapFragment()
    {

        try
        {
//            mapFragment = (MapFragment) getChildFragmentManager().findFragmentById(R.id.map);
            mapFragment = (MapFragment) getActivity().getFragmentManager().findFragmentById(R.id.map);
        }
        catch (Exception | Error e)
        {
            e.printStackTrace();
            if (mapFragment == null)
            {
                mapFragment = (MapFragment) getActivity().getFragmentManager().findFragmentById(R.id.map);
            }
        }

        if (mapFragment == null)
        {
            mapFragment = (MapFragment) getActivity().getFragmentManager().findFragmentById(R.id.map);
        }
    }

    GoogleMap mapG;

    private void setUpMapIfNeeded()
    {

        mapFragment.getMapAsync(new OnMapReadyCallback()
        {
            @Override
            public void onMapReady(GoogleMap gMap)
            {
                mapG = gMap;
                getData(gMap);
            }
        });
    }

    SwipeRefreshLayout swipeLayout;

    private void settingSwipeToRefresh(View v)
    {

        swipeLayout = (SwipeRefreshLayout) v.findViewById(R.id.swipeLayout);
        swipeLayout.setColorSchemeResources(
                R.color.red,
                R.color.grey,
                R.color.red);

        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener()
        {
            @Override
            public void onRefresh()
            {


                if (mapG != null)
                {
                    getData(mapG);
                }
                else
                {
                    if (mapFragment == null)
                    {
                        initializeMapFragment();
                    }

                    setUpMapIfNeeded();
                }

            }
        });

    }


    private void getData(final GoogleMap gMap)
    {


        new SuperWebServiceG(Global_Constants.URL + "Gateway/GetStatus?VehicleId=" + getVehicleID() + "&DeviceIMEI=" + getUnitID(), new HashMap<String, String>(), new CallBackWebService()
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

                        txtv_addressLocation.setText(jobjinner.getString("Address"));

                        final LatLng latlng = new LatLng(Double.parseDouble(jobjinner.getString("Latitude")), Double.parseDouble(jobjinner.getString("Longitude")));

                        layoutShowRoute.setOnClickListener(new View.OnClickListener()
                        {
                            @Override
                            public void onClick(View view)
                            {

                                Utills_G.startGoogleMaps(getActivity(), latlng);
                            }
                        });


                        Marker markr = gMap.addMarker(new MarkerOptions().position(latlng).draggable(false).title("").snippet("").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE)));
                        markr.showInfoWindow();

                        gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latlng, 12.0f));

                        ((TextView) rootView.findViewById(R.id.txtv_odometer)).setText(jobjinner.optString("Odometer"));
//                        "EngineOnOff":"True",

                        btnEngineStatus.setBackgroundTintList(ColorStateList.valueOf(jobjinner.getString("EngineOnOff").equals("True") ? getResources().getColor(R.color.green) : getResources().getColor(R.color.red)));

//                        double batteryPercentage = Double.parseDouble(jobjinner.getString("BatteryHealth"));
//                        btnbatteryStatus.setBackgroundTintList(ColorStateList.valueOf(batteryPercentage > 50.0 ? getResources().getColor(R.color.green) : (batteryPercentage > 20.0 ? getResources().getColor(R.color.orange) : getResources().getColor(R.color.red))));
                        btnbatteryStatus.setBackgroundTintList(ColorStateList.valueOf(jobjinner.getString("VehicleBattery").equals("True") ? getResources().getColor(R.color.red) : getResources().getColor(R.color.green)));


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


}



