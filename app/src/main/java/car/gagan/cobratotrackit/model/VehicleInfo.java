package car.gagan.cobratotrackit.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import java.io.Serializable;

/**
 * Created by gagandeep on 20/10/15.
 */
public class VehicleInfo implements Serializable
{

    private String ModelName, ManufactorName, LastUpdatedOn, ImageURL, LicencePlate;


    public VehicleInfo(String modelName, String manufactorName, String lastUpdatedOn, String imageURL, String licencePlate)
    {
        ModelName = modelName;
        ManufactorName = manufactorName;
        LastUpdatedOn = lastUpdatedOn;
        ImageURL = imageURL;
        LicencePlate = licencePlate;
    }

    public String getModelName()
    {
        return ModelName;
    }

    public String getManufactorName()
    {
        return ManufactorName;
    }

    public String getLastUpdatedOn()
    {
        return LastUpdatedOn;
    }

    public String getImageURL()
    {
        return ImageURL;
    }

    public String getLicencePlate()
    {
        return LicencePlate;
    }
}
