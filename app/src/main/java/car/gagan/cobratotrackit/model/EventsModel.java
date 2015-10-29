package car.gagan.cobratotrackit.model;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by gagandeep on 29/9/15.
 */
public class EventsModel
{


    private String MessageTime, Address, EventName;

    private LatLng latLng;

    public EventsModel(String messageTime, String address, String eventName, LatLng latLng)
    {
        MessageTime = messageTime;
        Address = address;
        EventName = eventName;
        this.latLng = latLng;
    }

    public String getEventName()
    {
        return EventName;
    }

    public String getMessageTime()
    {
        return MessageTime;
    }

    public String getAddress()
    {
        return Address;
    }

    public LatLng getLatLng()
    {
        return latLng;
    }
}
