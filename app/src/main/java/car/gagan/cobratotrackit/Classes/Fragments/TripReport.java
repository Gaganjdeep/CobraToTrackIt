package car.gagan.cobratotrackit.Classes.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import car.gagan.cobratotrackit.Adapters.TripReportAdapter;
import car.gagan.cobratotrackit.R;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import car.gagan.cobratotrackit.model.TripHistoryModel;
import car.gagan.cobratotrackit.utills.BaseFragmentHome;
import car.gagan.cobratotrackit.utills.CallBackWebService;
import car.gagan.cobratotrackit.utills.DateUtilsG;
import car.gagan.cobratotrackit.utills.Global_Constants;
import car.gagan.cobratotrackit.webservice.SuperWebServiceG;

public class TripReport extends BaseFragmentHome
{

    private ListView listVTripReport;
    SharedPreferences shrdPref;


    private List<TripHistoryModel> listData;
    private TripReportAdapter adapter;


    ProgressBar progressBarTripReport;

    private static int daysBack = 4;

    String dateToStart = "";

    public TripReport()
    {
    }

    public TripReport(String date)
    {
        dateToStart = date;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {

        View v = inflater.inflate(R.layout.fragment_trip_report, container, false);

        setupToolbar(v);

        shrdPref = getActivity().getSharedPreferences(Global_Constants.shared_pref_name, Context.MODE_PRIVATE);

//        listVTripReport=new EndlessListview(getActivity());
        listVTripReport = (ListView) v.findViewById(R.id.listVTripReport);
        progressBarTripReport = (ProgressBar) v.findViewById(R.id.progressBarTripReport);
        progressBarTripReport.getIndeterminateDrawable().setColorFilter(Color.RED, PorterDuff.Mode.MULTIPLY);

        progressBarTripReport.setVisibility(View.VISIBLE);

//
//        if (listData != null && listData.size() > 0)
//        {
//            progressBarTripReport.setVisibility(View.GONE);
//
//            adapter = new TripReportAdapter(getActivity(), listData);
//            listVTripReport.setAdapter(adapter);
////            listVTripReport.setListener(TripReport.this);
//        }


        gethistory(DateUtilsG.dateToString(dateToStart), DateUtilsG.dateToStringAddOneDay(dateToStart), getVehicleID(), selectedLanguage());

        return v;
    }


    private void setupToolbar(View V)
    {
        Toolbar toolbar = (Toolbar) V.findViewById(R.id.toolbar);
        toolbar.setVisibility(View.GONE);
//        ((TextView) toolbar.findViewById(R.id.txtvHeading)).setText(R.string.trip_report);


//        try
//        {
//            ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
//
//            ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//            setHasOptionsMenu(true);
//        }
//        catch (Exception e)
//        {
//            e.printStackTrace();
//        }

    }




    private void gethistory(String startDate, String endDate, String vehicleID, String language)
    {
        new SuperWebServiceG(urlToTripHistory(startDate, endDate, vehicleID, language), new HashMap<String, String>(), new CallBackWebService()
        {
            @Override
            public void webOnFinish(String output)
            {

                try
                {

                    if (listData == null)
                    {
                        listData = new ArrayList<>();
                    }
                    listData.clear();
//                    listVTripReport.removeAllViews();

                    JSONObject jObj = new JSONObject(output);

                    if (jObj.getString(Global_Constants.Status).equals(Global_Constants.success))
                    {

                        JSONArray jobjinnerArray = new JSONArray(jObj.getString(Global_Constants.Message));


                        for (int i = 0; i < jobjinnerArray.length(); i++)
                        {

                            JSONObject jobjInner = jobjinnerArray.getJSONObject(i);
                            TripHistoryModel data = new TripHistoryModel();


                            data.setAddressFrom(jobjInner.optString("AddressFrom"));
                            data.setAddressTo(jobjInner.optString("AddressTo"));
                            data.setAverageSpeed(jobjInner.optString("AverageSpeed"));
                            data.setEndTime(jobjInner.optString("EndTime"));
                            data.setKilometer(jobjInner.optString("Kilometer"));
                            data.setStartTime(jobjInner.optString("StartTime"));
                            data.setTopSpeed(jobjInner.optString("TopSpeed"));
                            data.setTripDuration(jobjInner.optString("TripDuration"));

                            try
                            {
                                LatLng latlng = new LatLng(Double.parseDouble(jobjInner.optString("LatitudeTo")), Double.parseDouble(jobjInner.optString("LongitudeTo")));

                                data.setLatLngTo(latlng);

                                LatLng latlngFrom = new LatLng(Double.parseDouble(jobjInner.optString("LatitudeFrom")), Double.parseDouble(jobjInner.optString("LongitudeFrom")));

                                data.setLatlngFrom(latlngFrom);


                            }
                            catch (NumberFormatException e)
                            {
                                e.printStackTrace();
                            }

                            listData.add(data);

//                            ShowTripReportList(LayoutNoti, data, i);


                        }


                    }

                    if (adapter == null)
                    {
                        adapter = new TripReportAdapter(getActivity(), listData);
                        listVTripReport.setAdapter(adapter);
//                        listVTripReport.setListener(TripReport.this);
                    }
                    else
                    {
                        adapter.notifyDataSetChanged();
                    }


                    progressBarTripReport.setVisibility(View.GONE);

                }
                catch (Exception e)
                {
                    progressBarTripReport.setVisibility(View.GONE);
                    e.printStackTrace();
                }


            }
        }).execute();

    }


    private String urlToTripHistory(String startDate, String endDate, String vehicleID, String lanuage)
    {
        return Global_Constants.URL + "Customer/GetTripHistory?StartDate=" + startDate + "&EndDate=" + endDate + "&VehicleId=" + vehicleID + "&Language=" + lanuage;
    }


}
