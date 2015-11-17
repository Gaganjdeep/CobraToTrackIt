package car.gagan.cobratotrackit.model;

/**
 * Created by gagandeep on 16/11/15.
 */
public class TripHistoryDailyModel
{
    private String VehicleId, Date, TotalTripCount, TotalDrivingTime, TotalParkingTime, MaxSpeed, AverageSpeed;

//    {"VehicleId":1379,"Date":"1/1/2000 12:00:00 AM","TotalTripCount":"0","TotalDrivingTime":"00:00:00",
//            "TotalParkingTime":"00:00:00","MaxSpeed":"0","AverageSpeed":""}


    public String getVehicleId()
    {
        return VehicleId;
    }

    public void setVehicleId(String vehicleId)
    {
        VehicleId = vehicleId;
    }

    public String getDate()
    {
        return Date;
    }

    public void setDate(String date)
    {
        Date = date;
    }

    public String getTotalTripCount()
    {
        return TotalTripCount;
    }

    public void setTotalTripCount(String totalTripCount)
    {
        TotalTripCount = totalTripCount;
    }

    public String getTotalDrivingTime()
    {
        return TotalDrivingTime;
    }

    public void setTotalDrivingTime(String totalDrivingTime)
    {
        TotalDrivingTime = totalDrivingTime;
    }

    public String getTotalParkingTime()
    {
        return TotalParkingTime;
    }

    public void setTotalParkingTime(String totalParkingTime)
    {
        TotalParkingTime = totalParkingTime;
    }

    public String getMaxSpeed()
    {
        return MaxSpeed;
    }

    public void setMaxSpeed(String maxSpeed)
    {
        MaxSpeed = maxSpeed;
    }

    public String getAverageSpeed()
    {
        return AverageSpeed;
    }

    public void setAverageSpeed(String averageSpeed)
    {
        AverageSpeed = averageSpeed;
    }
}
