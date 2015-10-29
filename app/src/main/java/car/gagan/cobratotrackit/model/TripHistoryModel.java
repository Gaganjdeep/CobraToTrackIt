package car.gagan.cobratotrackit.model;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by gagandeep on 29/9/15.
 */
public class TripHistoryModel
{


    private String StartTime, EndTime, AddressFrom, AddressTo, Kilometer, TripDuration, AverageSpeed, TopSpeed;

    private LatLng latLngTo, latlngFrom;

    public LatLng getLatlngFrom()
    {
        return latlngFrom;
    }

    public void setLatlngFrom(LatLng latlngFrom)
    {
        this.latlngFrom = latlngFrom;
    }

    public LatLng getLatLngTo()
    {
        return latLngTo;
    }

    public void setLatLngTo(LatLng latLngTo)
    {
        this.latLngTo = latLngTo;
    }


    public String getStartTime()
    {
        return StartTime;
    }

    public void setStartTime(String startTime)
    {
        StartTime = startTime;
    }

    public String getEndTime()
    {
        return EndTime;
    }

    public void setEndTime(String endTime)
    {
        EndTime = endTime;
    }

    public String getAddressFrom()
    {
        return AddressFrom;
    }

    public void setAddressFrom(String addressFrom)
    {
        AddressFrom = addressFrom;
    }

    public String getAddressTo()
    {
        return AddressTo;
    }

    public void setAddressTo(String addressTo)
    {
        AddressTo = addressTo;
    }

    public String getKilometer()
    {
        return Kilometer;
    }

    public void setKilometer(String kilometer)
    {
        Kilometer = kilometer;
    }

    public String getTripDuration()
    {
        return TripDuration;
    }

    public void setTripDuration(String tripDuration)
    {
        TripDuration = tripDuration;
    }

    public String getAverageSpeed()
    {
        return AverageSpeed;
    }

    public void setAverageSpeed(String averageSpeed)
    {
        AverageSpeed = averageSpeed;
    }

    public String getTopSpeed()
    {
        return TopSpeed;
    }

    public void setTopSpeed(String topSpeed)
    {
        TopSpeed = topSpeed;
    }

//              "VehicleId": 454,
//            "StartTime": "01-09-2015 14:23:01",
//            "EndTime": "08-09-2015 13:49:50",
//            "GpsPositionFrom": "POINT (34.7978316666667 32.0723483333333)",
//            "GpsPositionTo": "POINT (34.75841 31.996485)",
//            "LatitudeFrom": 32.0723483333333,
//            "LongitudeFrom": 34.7978316666667,
//            "LatitudeTo": 31.996485,
//            "LongitudeTo": 34.75841,
//            "AddressFrom": "Totseret ha-Arets St 11, Tel Aviv-Yafo, Israel",
//            "AddressTo": "Ayalon Hwy, Holon, Israel",
//            "Kilometer": "9.22578825104442",
//            "TripDuration": "23:26:49",
//            "AverageSpeed": "42",
//            "TopSpeed": "73",
//            "FuelConsumed": "0"


}
