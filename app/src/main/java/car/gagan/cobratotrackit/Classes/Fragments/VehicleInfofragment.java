package car.gagan.cobratotrackit.Classes.Fragments;


import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;

import car.gagan.cobratotrackit.R;
import car.gagan.cobratotrackit.model.VehicleInfo;
import car.gagan.cobratotrackit.utills.Global_Constants;
import car.gagan.cobratotrackit.utills.SharedPrefHelper;
import car.gagan.cobratotrackit.utills.StoreData;
import car.gagan.cobratotrackit.utills.Utills_G;

/**
 * A simple {@link Fragment} subclass.
 */
public class VehicleInfofragment extends android.support.v4.app.Fragment
{


    private TextView txtvModelName, txtvManufactorName, txtvUpdatedOn, txtvLicence;
    private ImageView imgvCarImg;


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

                txtvModelName = (TextView) v.findViewById(R.id.txtvModelName);
                txtvManufactorName = (TextView) v.findViewById(R.id.txtvManufactorName);
                txtvUpdatedOn = (TextView) v.findViewById(R.id.txtvUpdatedOn);
                txtvLicence = (TextView) v.findViewById(R.id.txtvLicence);

                imgvCarImg = (ImageView) v.findViewById(R.id.imgvCarImg);


                updateInfo();
            }
        });


        return v;
    }


    private void updateInfo()
    {

        if (isAdded())
        {
            try
            {
                if (vehicleInfo == null)
                {
                    vehicleInfo = StoreData.read(getActivity());
                }


                txtvModelName.setText(vehicleInfo.getModelName());
                txtvManufactorName.setText(vehicleInfo.getManufactorName());

                String lastUPdatedOn = Utills_G.format_date(getActivity(), vehicleInfo.getLastUpdatedOn(), Global_Constants.SERVERTIME_FORMAT, Global_Constants.LAST_UPDATED_ON);

                txtvUpdatedOn.setText(String.format(getResources().getString(R.string.last_updated_on) + " : %s", lastUPdatedOn));


                txtvLicence.setText(String.format(getResources().getString(R.string.licence_plate) + " : %s", vehicleInfo.getLicencePlate()));


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
                    imgvCarImg.setImageBitmap(bitmap);

                }

            }
            catch (Exception | Error e)
            {
                e.printStackTrace();
            }
        }

    }


    @Override
    public void onPause()
    {
        super.onPause();
        SharedPrefHelper.getPrefs(getActivity()).unregisterOnSharedPreferenceChangeListener(listener);
    }

    @Override
    public void onResume()
    {
        super.onResume();

        SharedPrefHelper.getPrefs(getActivity()).registerOnSharedPreferenceChangeListener(listener);
    }

    private final SharedPreferences.OnSharedPreferenceChangeListener listener =
            new SharedPreferences.OnSharedPreferenceChangeListener()
            {
                @Override
                public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key)
                {
                    if (SharedPrefHelper.updateDate.equals(key))
                    {
                        Runnable runnable = new Runnable()
                        {
                            public void run()
                            {
                                vehicleInfo = null;
                                updateInfo();
                            }
                        };

                        new Handler().post(runnable);

                    }

                }

            };


    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
    }
}
