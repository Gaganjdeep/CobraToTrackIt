package car.gagan.cobratotrackit.Adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;

import java.util.List;

import car.gagan.cobratotrackit.R;
import car.gagan.cobratotrackit.model.EventsModel;
import car.gagan.cobratotrackit.model.TripHistoryModel;
import car.gagan.cobratotrackit.utills.Global_Constants;
import car.gagan.cobratotrackit.utills.Utills_G;

/**
 * Created by gagandeep on 5/11/15.
 */
public class TripReportAdapter extends BaseAdapter
{
    Context con;

    List<TripHistoryModel> DataList;


    public TripReportAdapter(Context con, List<TripHistoryModel> dataList)
    {
        this.con = con;
        DataList = dataList;
    }

    @Override
    public int getCount()
    {
        return DataList.size();
    }

    @Override
    public TripHistoryModel getItem(int i)
    {
        return DataList.get(i);
    }

    @Override
    public long getItemId(int i)
    {
        return 0;
    }

    @Override
    public View getView(int i, View viewOther, ViewGroup viewGroup)
    {

        final TripHistoryModel data = getItem(i);

        if (viewOther == null)
        {
            LayoutInflater inflater = (LayoutInflater) con.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            viewOther = inflater.inflate(R.layout.inflator_trip_report, viewGroup, false);
        }


        ShowTripReportList(viewOther, data, i);


//        View v = new View(con);
//        v.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 2));
//        v.setBackgroundColor(getResources().getColor(R.color.lt_grey));


        return viewOther;
    }


    private void ShowTripReportList(View viewOther, final TripHistoryModel data, int position)
    {

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

        txtv_km_Report.setText(Html.fromHtml(km + " " + "<small>" + con.getResources().getString(R.string.distance) + "</small>"));
        txtv_time_Report.setText(Html.fromHtml(data.getTripDuration() + " " + "<small>" + con.getResources().getString(R.string.time) + "</small>"));


        txtv_date_Report.setText(Utills_G.format_date(con, data.getEndTime(), Global_Constants.SERVERTIME_FORMAT, Global_Constants.TIMEFORMAT_HISTORY));

        txtv_StartAddress.setText(data.getAddressFrom().isEmpty() ? "" : String.format("%s : %s", con.getString(R.string.start), data.getAddressFrom()));
        txtv_EndAddress.setText(data.getAddressTo().isEmpty() ? "" : String.format("%s : %s", con.getString(R.string.end), data.getAddressTo()));

        txtv_StartAddress.setOnClickListener(new View.OnClickListener()
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
        layout.setCardBackgroundColor(con.getResources().getColor(theme1[0]));
        tvAvgSpeed.setBackgroundColor(con.getResources().getColor(theme1[1]));
        tvTopSpeed.setBackgroundColor(con.getResources().getColor(theme1[2]));
    }

    private final int[] theme1 = {R.color.lt_grey, R.color.black, R.color.red};

    private void startGoogleMaps(LatLng ltlngDestination, LatLng ltlngSource)
    {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://maps.google.com/maps?daddr=" + ltlngDestination.latitude + "," + ltlngDestination.longitude + "&saddr=" + ltlngSource.latitude + "," + ltlngSource.longitude));
        con.startActivity(intent);
    }
}