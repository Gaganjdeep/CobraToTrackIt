package car.gagan.cobratotrackit.utills;

import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;

import car.gagan.cobratotrackit.model.VehicleInfo;

/**
 * Created by gagandeep on 20/10/15.
 */
public class StoreData
{

    public static void write(Context context, Object VehicleInfo)
    {
        File directory = new File(context.getFilesDir().getAbsolutePath()
                + File.separator + "serlization");
        if (!directory.exists())
        {
            directory.mkdirs();
        }

        String filename = "G_Info.srl";
        ObjectOutput out = null;

        try
        {
            out = new ObjectOutputStream(new FileOutputStream(directory
                    + File.separator + filename));
            out.writeObject(VehicleInfo);
            out.close();
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }


    public static VehicleInfo read(Context context)
    {

        ObjectInputStream input = null;
        VehicleInfo ReturnClass = null;
        String filename = "G_Info.srl";
        File directory = new File(context.getFilesDir().getAbsolutePath() + File.separator + "serlization");
        try
        {

            input = new ObjectInputStream(new FileInputStream(directory + File.separator + filename));
            ReturnClass = (VehicleInfo) input.readObject();
            input.close();

        }
        catch (Exception e)
        {

            e.printStackTrace();
            return null;
        }
        return ReturnClass;
    }


}
