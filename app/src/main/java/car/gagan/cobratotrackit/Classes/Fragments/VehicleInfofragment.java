package car.gagan.cobratotrackit.Classes.Fragments;


import android.app.Fragment;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;

import car.gagan.cobratotrackit.R;
import car.gagan.cobratotrackit.model.VehicleInfo;
import car.gagan.cobratotrackit.utills.StoreData;

/**
 * A simple {@link Fragment} subclass.
 */
public class VehicleInfofragment extends android.support.v4.app.Fragment
{


//    private TextView txtvModelName, txtvManufactorName, txtvUpdatedOn;


    public VehicleInfofragment()
    {
        // Required empty public constructor
    }

    private static VehicleInfo vehicleInfo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {

        final View v = inflater.inflate(R.layout.fragment_vehicle_infofragment, container, false);


        v.post(new Runnable()
        {
            @Override
            public void run()
            {
                try
                {

                    if (vehicleInfo == null)
                    {
                        vehicleInfo = StoreData.read(getActivity());
                    }


                    ((TextView) v.findViewById(R.id.txtvModelName)).setText(vehicleInfo.getModelName());
                    ((TextView) v.findViewById(R.id.txtvManufactorName)).setText(vehicleInfo.getManufactorName());
                    ((TextView) v.findViewById(R.id.txtvUpdatedOn)).setText(String.format(getResources().getString(R.string.last_updated_on) + " : %s", vehicleInfo.getLastUpdatedOn()));
                    ((TextView) v.findViewById(R.id.txtvLicence)).setText(String.format(getResources().getString(R.string.licence_plate) + " : %s", vehicleInfo.getLicencePlate()));


                    File myDir = new File("/sdcard/CobraCar");
                    if (!myDir.exists())
                    {
                        return;
                    }

                    String fname = "CarImage.png";
                    File file = new File(myDir, fname);
                    Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());


                    if (bitmap != null)
                    {
                        ((ImageView) v.findViewById(R.id.imgvCarImg)).setImageBitmap(bitmap);

                    }

                }
                catch (Exception | Error e)
                {
                    e.printStackTrace();
                }


            }
        });


        return v;
    }


    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
    }
}
