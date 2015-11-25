package car.gagan.cobratotrackit.Classes.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import car.gagan.cobratotrackit.Adapters.TripReportDailyAdapter;
import car.gagan.cobratotrackit.R;
import car.gagan.cobratotrackit.model.TripHistoryDailyModel;
import car.gagan.cobratotrackit.utills.BaseFragmentHome;
import car.gagan.cobratotrackit.utills.CallBackWebService;
import car.gagan.cobratotrackit.utills.DateUtilsG;
import car.gagan.cobratotrackit.utills.Global_Constants;
import car.gagan.cobratotrackit.webservice.SuperWebServiceG;

/**
 * Created by gagandeep on 17 Nov 2015.
 */
public class TripReportDaily extends BaseFragmentHome
{

    private ListView listVTripReport;
    SharedPreferences shrdPref;


    private static List<TripHistoryDailyModel> listData;
    private TripReportDailyAdapter adapter;


    ProgressBar progressBarTripReport;

    private static int daysBack = 7;


    public TripReportDaily()
    {
        // Required empty public constructor
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

        listVTripReport = (ListView) v.findViewById(R.id.listVTripReport);
        progressBarTripReport = (ProgressBar) v.findViewById(R.id.progressBarTripReport);
        progressBarTripReport.getIndeterminateDrawable().setColorFilter(Color.RED, PorterDuff.Mode.MULTIPLY);

        progressBarTripReport.setVisibility(View.VISIBLE);


        if (listData != null && listData.size() > 0)
        {
            progressBarTripReport.setVisibility(View.GONE);

            adapter = new TripReportDailyAdapter(getActivity(), listData);
            listVTripReport.setAdapter(adapter);

        }


        gethistory(DateUtilsG.getStartDate(daysBack), DateUtilsG.getCurrentDateWithAddedDays(1), getVehicleID(), selectedLanguage());

        return v;
    }


    private void setupToolbar(View V)
    {
        Toolbar toolbar = (Toolbar) V.findViewById(R.id.toolbar);

        ((TextView) toolbar.findViewById(R.id.txtvHeading)).setText(R.string.trip_report);

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

                    JSONObject jObj = new JSONObject(output);

                    if (jObj.getString(Global_Constants.Status).equals(Global_Constants.success))
                    {

                        JSONArray jobjinnerArray = new JSONArray(jObj.getString(Global_Constants.Message));


                        for (int i = 0; i < jobjinnerArray.length(); i++)
                        {

                            JSONObject jobjInner = jobjinnerArray.getJSONObject(i);
                            TripHistoryDailyModel data = new TripHistoryDailyModel();

//    {"VehicleId":1379,"Date":"1/1/2000 12:00:00 AM","TotalTripCount":"0","TotalDrivingTime":"00:00:00",
//            "TotalParkingTime":"00:00:00","MaxSpeed":"0","AverageSpeed":""}

                            data.setVehicleId(jobjInner.optString("VehicleId"));
                            data.setAverageSpeed(jobjInner.optString("AverageSpeed"));
                            data.setMaxSpeed(jobjInner.optString("MaxSpeed"));
                            data.setDate(jobjInner.optString("Date"));
                            data.setTotalTripCount(jobjInner.optString("TotalTripCount"));
                            data.setTotalDrivingTime(jobjInner.optString("TotalDrivingTime"));
                            data.setTotalParkingTime(jobjInner.optString("TotalParkingTime"));


                            listData.add(data);

//                            ShowTripReportList(LayoutNoti, data, i);


                        }


                    }

                    if (adapter == null)
                    {

                        Collections.reverse(listData);

                        adapter = new TripReportDailyAdapter(getActivity(), listData);
                        listVTripReport.setAdapter(adapter);

                    }
                    else
                    {
                        Collections.reverse(listData);
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
        return Global_Constants.URL + "Customer/GetTripHistoryDaily?StartDate=" + startDate + "&EndDate=" + endDate + "&VehicleId=" + vehicleID + "&Language=" + lanuage;
    }


}
