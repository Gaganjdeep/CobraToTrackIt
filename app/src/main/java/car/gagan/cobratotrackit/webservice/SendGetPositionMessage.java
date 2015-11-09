package car.gagan.cobratotrackit.webservice;

import android.os.AsyncTask;

import car.gagan.cobratotrackit.utills.Global_Constants;

/**
 * Created by gagandeep on 3/11/15.
 */
public class SendGetPositionMessage extends AsyncTask<Void, Void, String>
{


//


    String UnitID;

    public SendGetPositionMessage(String unitID)
    {
        UnitID = unitID;
    }

    @Override
    protected String doInBackground(Void... voids)
    {

        String url = Global_Constants.URL + "Gateway/SendGetPositionMessage?UnitID=" + UnitID;

        return new WebServiceHelper().performGetCall(url);
    }
}
