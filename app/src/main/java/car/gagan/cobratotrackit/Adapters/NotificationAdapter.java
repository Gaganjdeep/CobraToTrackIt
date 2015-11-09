package car.gagan.cobratotrackit.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import car.gagan.cobratotrackit.R;
import car.gagan.cobratotrackit.model.EventsModel;
import car.gagan.cobratotrackit.utills.Global_Constants;
import car.gagan.cobratotrackit.utills.Utills_G;

/**
 * Created by gagandeep on 28/10/15.
 */
public class NotificationAdapter extends BaseAdapter
{
    Context con;

    List<EventsModel> DataList;


    public NotificationAdapter(Context con, List<EventsModel> dataList)
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
    public EventsModel getItem(int i)
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

        final EventsModel data = getItem(i);

        if (viewOther == null)
        {
            LayoutInflater inflater = (LayoutInflater) con.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            viewOther = inflater.inflate(R.layout.inflator_notification, viewGroup, false);
        }


        TextView txtv_title = (TextView) viewOther.findViewById(R.id.txtv_title);
        TextView txtv_date_time = (TextView) viewOther.findViewById(R.id.txtv_date_time);
        TextView txtv_speed = (TextView) viewOther.findViewById(R.id.txtv_speed);


        txtv_title.setText(data.getEventName());


        txtv_speed.setText(data.getAddress());


        txtv_date_time.setText(Utills_G.format_date(data.getMessageTime(), Global_Constants.SERVERTIME_FORMAT, Global_Constants.TIMEFORMAT_NOTIFICATION));


//        View v = new View(con);
//        v.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 2));
//        v.setBackgroundColor(getResources().getColor(R.color.lt_grey));


        return viewOther;
    }
}