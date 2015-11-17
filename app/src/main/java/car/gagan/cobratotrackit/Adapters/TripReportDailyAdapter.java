package car.gagan.cobratotrackit.Adapters;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

import car.gagan.cobratotrackit.Classes.TripReportActivity;
import car.gagan.cobratotrackit.R;
import car.gagan.cobratotrackit.model.TripHistoryDailyModel;
import car.gagan.cobratotrackit.utills.Global_Constants;
import car.gagan.cobratotrackit.utills.Utills_G;

/**
 * Created by gagandeep on 16/11/15.
 */
public class TripReportDailyAdapter extends BaseAdapter
{
    Context con;

    List<TripHistoryDailyModel> DataList;


    public TripReportDailyAdapter(Context con, List<TripHistoryDailyModel> dataList)
    {
        this.con = con;
        DataList = dataList;

        try
        {
            Collections.reverse(DataList);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    @Override
    public int getCount()
    {
        return DataList.size();
    }

    @Override
    public TripHistoryDailyModel getItem(int i)
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

        final TripHistoryDailyModel data = getItem(i);

        if (viewOther == null)
        {
            LayoutInflater inflater = (LayoutInflater) con.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            viewOther = inflater.inflate(R.layout.trip_report_daily_inflator, viewGroup, false);
        }


        ShowTripReportList(viewOther, data);

        return viewOther;
    }


    private void ShowTripReportList(View viewOther, final TripHistoryDailyModel data)
    {

        try
        {
            TextView txtvMaxSpeed = (TextView) viewOther.findViewById(R.id.txtvMaxSpeed);
            TextView txtvAverageSpeed = (TextView) viewOther.findViewById(R.id.txtvAverageSpeed);
            TextView txtvParkingTime = (TextView) viewOther.findViewById(R.id.txtvParkingTime);
            TextView txtvDrivingTime = (TextView) viewOther.findViewById(R.id.txtvDrivingTime);
            final TextView txtvDate = (TextView) viewOther.findViewById(R.id.txtvDate);
            TextView txtvCount = (TextView) viewOther.findViewById(R.id.txtvCount);


            txtvMaxSpeed.setText(String.format("%s %s", con.getResources().getString(R.string.top_speed), data.getMaxSpeed()));
            txtvAverageSpeed.setText(String.format("%s %s", con.getResources().getString(R.string.average_speed), data.getAverageSpeed()));
            txtvParkingTime.setText(String.format("%s%s", con.getResources().getString(R.string.driving_time), data.getTotalParkingTime()));
            txtvDrivingTime.setText(String.format("%s%s", con.getResources().getString(R.string.parking_time), data.getTotalDrivingTime()));
            txtvCount.setText(data.getTotalTripCount());

            txtvDate.setText(Utills_G.format_date(con, data.getDate(), Global_Constants.SERVERTIME_FORMAT, Global_Constants.TIMEFORMAT_TRIP_DAILY));


            CardView layoutbackgroudTripReport = (CardView) viewOther.findViewById(R.id.cardV);
            layoutbackgroudTripReport.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
//                    Intent gotoTripReport = new Intent(con, TripReportActivity.class);
//                    gotoTripReport.putExtra("date", data.getDate());
//                    con.startActivity(gotoTripReport);

                    transitionToActivity((Activity) con, TripReportActivity.class, txtvDate, data.getDate());

                }
            });


        }
        catch (Exception e)
        {
            e.printStackTrace();
        }


    }


    private void transitionToActivity(Activity activity, Class target, View date, String dateString)
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            Intent i = new Intent(activity, target);
            i.putExtra("date", dateString);
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(activity, android.util.Pair.create(date, "date"));
            activity.startActivity(i, options.toBundle());

        }
        else
        {
            Intent gotoTripReport = new Intent(activity, target);
            gotoTripReport.putExtra("date", dateString);
            activity.startActivity(gotoTripReport);
        }
    }


}