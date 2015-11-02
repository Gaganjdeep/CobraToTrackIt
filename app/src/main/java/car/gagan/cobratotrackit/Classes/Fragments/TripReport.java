package car.gagan.cobratotrackit.Classes.Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;


import car.gagan.cobratotrackit.R;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import car.gagan.cobratotrackit.model.EventsModel;
import car.gagan.cobratotrackit.model.TripHistoryModel;
import car.gagan.cobratotrackit.utills.BaseFragmentHome;
import car.gagan.cobratotrackit.utills.CallBackWebService;
import car.gagan.cobratotrackit.utills.Global_Constants;
import car.gagan.cobratotrackit.utills.Utills_G;
import car.gagan.cobratotrackit.webservice.SuperWebServiceG;

public class TripReport extends BaseFragmentHome
{
    private final int[] theme1 = {R.color.lt_grey, R.color.black, R.color.red};
    private final int[] theme2 = {R.color.lt_grey, R.color.black, R.color.red};


    //    private Dialog dialog;
    private final int[] theme3 = {R.color.grey, R.color.red, R.color.black};
    private LinearLayout LayoutNoti;
    SharedPreferences shrdPref;


    private static List<TripHistoryModel> listData;

    public TripReport()
    {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState
    )
    {

        View v = inflater.inflate(R.layout.fragment_trip_report, container, false);

        setupToolbar(v);

        shrdPref = getActivity().getSharedPreferences(Global_Constants.shared_pref_name, Context.MODE_PRIVATE);

        LayoutNoti = (LinearLayout) v.findViewById(R.id.LayoutNoti);


        if (listData != null && listData.size() > 0)
        {
            for (TripHistoryModel data : listData)
            {
                ShowTripReportList(LayoutNoti, data, 1);
            }

        }


        Calendar c = Calendar.getInstance();
        Date date = c.getTime();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");


        gethistory("2000-01-01", sdf.format(date), getVehicleID());

        return v;
    }

    private void setupToolbar(View V)
    {
        Toolbar toolbar = (Toolbar) V.findViewById(R.id.toolbar);

        ((TextView) toolbar.findViewById(R.id.txtvHeading)).setText(R.string.trip_report);

    }

    private void gethistory(String startDate, String endDate, String vehicleID)
    {
        new SuperWebServiceG(urlToTripHistory(startDate, endDate, vehicleID), new HashMap<String, String>(), new CallBackWebService()
        {
            @Override
            public void webOnFinish(String output)
            {

                try
                {

//                    if (dialog.isShowing())
//                    {
//                        dialog.dismiss();
//                    }

                    if (listData == null)
                    {
                        listData = new ArrayList<TripHistoryModel>();
                    }
                    listData.clear();
                    LayoutNoti.removeAllViews();

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

                            ShowTripReportList(LayoutNoti, data, i);


                        }


                    }


                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }


            }
        }).execute();

    }

    private void ShowTripReportList(LinearLayout layoutContainer, final TripHistoryModel data, int position)
    {


        final LinearLayout layoutMsgContainer = new LinearLayout(getActivity());


        View viewOther = LayoutInflater.from(getActivity()).inflate(R.layout.inflator_trip_report, layoutMsgContainer);


        CardView layoutbackgroudTripReport = (CardView) viewOther.findViewById(R.id.cardV);


        TextView txtv_speed_Report = (TextView) viewOther.findViewById(R.id.txtv_speed_Report);
        TextView txtv_Topspeed_Report = (TextView) viewOther.findViewById(R.id.txtv_Topspeed_Report);
        TextView txtv_km_Report = (TextView) viewOther.findViewById(R.id.txtv_km_Report);
        TextView txtv_time_Report = (TextView) viewOther.findViewById(R.id.txtv_time_Report);
        TextView txtv_date_Report = (TextView) viewOther.findViewById(R.id.txtv_date_Report);

        TextView txtv_StartAddress = (TextView) viewOther.findViewById(R.id.txtv_StartAddress);
        TextView txtv_EndAddress = (TextView) viewOther.findViewById(R.id.txtv_EndAddress);


        changeColor(layoutbackgroudTripReport, txtv_speed_Report, txtv_Topspeed_Report, position);


        txtv_speed_Report.setText(data.getAverageSpeed());
        txtv_Topspeed_Report.setText(data.getTopSpeed());


        String km = data.getKilometer().contains(".") ? (data.getKilometer().substring(0, data.getKilometer().indexOf("."))) : data.getKilometer();

        txtv_km_Report.setText(Html.fromHtml(km + "<small><font color=black> km</font></small>"));


        txtv_time_Report.setText(Html.fromHtml(data.getTripDuration() + "<small><font color=black> min</font></small>"));

        txtv_date_Report.setText(Utills_G.format_date(data.getEndTime(), Global_Constants.SERVERTIME_FORMAT, Global_Constants.TIMEFORMAT_HISTORY));

        txtv_StartAddress.setText(data.getAddressFrom().isEmpty() ? "" : String.format("START : %s", data.getAddressFrom()));
        txtv_EndAddress.setText(data.getAddressTo().isEmpty() ? "" : String.format("END : %s", data.getAddressTo()));

        layoutContainer.addView(layoutMsgContainer);


        layoutContainer.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startGoogleMaps(data.getLatLngTo(), data.getLatlngFrom());
            }
        });


    }

    private void changeColor(CardView layout, TextView tvAvgSpeed, TextView tvTopSpeed, int position)
    {
//
//        if (position % 3 == 0)
//        {
        layout.setCardBackgroundColor(getResources().getColor(theme1[0]));
        tvAvgSpeed.setBackgroundColor(getResources().getColor(theme1[1]));
        tvTopSpeed.setBackgroundColor(getResources().getColor(theme1[2]));

//        }
//        else if (position % 2 == 0)
//        {
//
//            layout.setCardBackgroundColor(getResources().getColor(theme2[0]));
//            tvAvgSpeed.setBackgroundColor(getResources().getColor(theme2[1]));
//            tvTopSpeed.setBackgroundColor(getResources().getColor(theme2[2]));
//
//        }
//        else
//        {
//
//            layout.setCardBackgroundColor(getResources().getColor(theme3[0]));
//            tvAvgSpeed.setBackgroundColor(getResources().getColor(theme3[1]));
//            tvTopSpeed.setBackgroundColor(getResources().getColor(theme3[2]));
//
//        }


    }

    private String urlToTripHistory(String startDate, String endDate, String vehicleID)
    {
        return Global_Constants.URL + "Customer/GetTripHistory?StartDate=" + startDate + "&EndDate=" + endDate + "&VehicleId=" + vehicleID;
    }

    private void startGoogleMaps(LatLng ltlngDestination, LatLng ltlngSource)
    {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://maps.google.com/maps?daddr=" + ltlngDestination.latitude + "," + ltlngDestination.longitude + "&saddr=" + ltlngSource.latitude + "," + ltlngSource.longitude));
        getActivity().startActivity(intent);
    }

}
