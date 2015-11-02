package car.gagan.cobratotrackit.Classes;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;


import car.gagan.cobratotrackit.R;

import org.json.JSONObject;

import java.util.HashMap;

import car.gagan.cobratotrackit.model.VehicleInfo;
import car.gagan.cobratotrackit.utills.CallBackWebService;
import car.gagan.cobratotrackit.utills.Global_Constants;
import car.gagan.cobratotrackit.utills.ImageDownloader;
import car.gagan.cobratotrackit.utills.StoreData;
import car.gagan.cobratotrackit.utills.Utills_G;
import car.gagan.cobratotrackit.webservice.SuperWebServiceG;

public class Splash_Cobra extends AppCompatActivity
{


    SharedPreferences shrdPref;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash__cobra);


        SharedPreferences preference = getSharedPreferences("Preference", Context.MODE_PRIVATE);

        Utills_G.change_language(preference.getString("language", "en"), Splash_Cobra.this);


        shrdPref = getSharedPreferences(Global_Constants.shared_pref_name, Context.MODE_PRIVATE);


        if (shrdPref.contains(Global_Constants.VEHICLEID))
        {
            updaVehicleInfo(String.format("%sGateway/GetVehicleInfo?VehicleId=%s", Global_Constants.URL, getVehicleID()));
        }
        else
        {
            startActivity(new Intent(Splash_Cobra.this, Register_Screen.class));
            finish();
        }


    }

    public String getVehicleID()
    {
        SharedPreferences shrdPref = getSharedPreferences(Global_Constants.shared_pref_name, Context.MODE_PRIVATE);

        return shrdPref.getString(Global_Constants.VEHICLEID, "");
    }


    public void updaVehicleInfo(String url)
    {

        final Dialog dialog = new Dialog(Splash_Cobra.this, R.style.Theme_Dialog);
        dialog.setContentView(R.layout.progress_dialog);
        dialog.show();

        new SuperWebServiceG(url, new HashMap<String, String>(), new CallBackWebService()
        {
            @Override
            public void webOnFinish(String output)
            {

                try
                {


                    JSONObject jObj = new JSONObject(output);

                    if (jObj.getString(Global_Constants.Status).equals(Global_Constants.success))
                    {

                        JSONObject jobjinner = new JSONObject(jObj.getString(Global_Constants.Message));

                        StoreData.write(Splash_Cobra.this, new VehicleInfo(jobjinner.optString("ModelName"), jobjinner.optString("ManufactorName"), jobjinner.optString("LastUpdatedOn"), jobjinner.optString("VehicleImageURL"), jobjinner.optString("LicensePlate")));


                        new ImageDownloader(jobjinner.optString("VehicleImageURL"), new CallBackWebService()
                        {
                            @Override
                            public void webOnFinish(String output)
                            {


                                if (dialog.isShowing())
                                {
                                    dialog.dismiss();
                                }

                                if (shrdPref.contains("LicensePlate"))
                                {

                                    Intent intnt = new Intent(Splash_Cobra.this, Verfication_Screen.class);
                                    intnt.putExtra(Verfication_Screen.ISMOBILE_VERIFICATION, false);
                                    startActivity(intnt);
                                }
                                else
                                {
                                    startActivity(new Intent(Splash_Cobra.this, Register_Screen.class));
                                    finish();
                                }


                                finish();
                            }
                        }, Splash_Cobra.this).execute();


                    }
                    else
                    {

                        if (dialog.isShowing())
                        {
                            dialog.dismiss();
                        }
                        tryAgain();
                    }


                }
                catch (Exception | Error e)
                {
                    if (dialog.isShowing())
                    {
                        dialog.dismiss();
                    }


                    tryAgain();

                    e.printStackTrace();
                }


            }
        }).execute();

    }


    private void tryAgain()
    {
        Utills_G.show_dialog_msg(Splash_Cobra.this, "Some error has occurred ,Please try again.", new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Utills_G.global_dialog.dismiss();
                updaVehicleInfo(String.format("%sGateway/GetVehicleInfo?VehicleId=%s", Global_Constants.URL, getVehicleID()));
            }
        });
    }


}
