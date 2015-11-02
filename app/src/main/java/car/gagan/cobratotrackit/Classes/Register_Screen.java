package car.gagan.cobratotrackit.Classes;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;


import car.gagan.cobratotrackit.R;

import com.google.android.gms.gcm.GoogleCloudMessaging;

import org.json.JSONObject;

import java.util.HashMap;

import car.gagan.cobratotrackit.model.VehicleInfo;
import car.gagan.cobratotrackit.utills.CallBackWebService;
import car.gagan.cobratotrackit.utills.Global_Constants;
import car.gagan.cobratotrackit.utills.ImageDownloader;
import car.gagan.cobratotrackit.utills.StoreData;
import car.gagan.cobratotrackit.utills.Utills_G;
import car.gagan.cobratotrackit.webservice.SuperWebServiceG;

public class Register_Screen extends AppCompatActivity implements View.OnClickListener
{


    public static final String SENDER_ID = "355163309006";
    FrameLayout layoutActionBar;
    String DeviceID = "";
    private EditText edLicencePlate, edPhoneNumber/*, edUnitIMEI,edEmail*/;
    private Button btnRegister;
    private GoogleCloudMessaging gcm;

    private final String REG_URL = Global_Constants.URL + "Customer/SaveCustomer";


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register__screen);


//		ACTION BAR START
        layoutActionBar = (FrameLayout) findViewById(R.id.toolbar);

        ImageView img_back = (ImageView) layoutActionBar.findViewById(R.id.back_actionbar);
        img_back.setOnClickListener(this);

//		ACTION BAR END
        getRegisterationID();

//        edFirstName = (EditText) findViewById(R.id.edFirstName);
//        edLastName = (EditText) findViewById(R.id.edLastName);
//		edEmail = (EditText) findViewById(R.id.edEmail);
        edLicencePlate = (EditText) findViewById(R.id.edLicencePlate);
        edPhoneNumber = (EditText) findViewById(R.id.edPhoneNumber);
        btnRegister = (Button) findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(this);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case android.R.id.home:
                // app icon in action bar clicked; goto parent activity.
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onClick(View v)
    {


        switch (v.getId())
        {
            case R.id.btnRegister:


                if (ValidityCheck())
                {

//                    dialog = ProgressDialog.show(Register_Screen.this, "", "");

                    dialog = new Dialog(Register_Screen.this, R.style.Theme_Dialog);
                    dialog.setContentView(R.layout.progress_dialog);
                    dialog.show();


                    HashMap<String, String> data = new HashMap<>();
                    data.put("FirstName", edPhoneNumber.getText().toString());
                    /*data.put("LastName", edLastName.getText().toString());*/
                    data.put("MobileNo", edPhoneNumber.getText().toString());
                    data.put("LicensePlate", edLicencePlate.getText().toString());
                    data.put("ApplicationId", DeviceID);
                    data.put("Latitude", "");
                    data.put("Longitude", "");
                    data.put("DeviceType", "andriod");

                    new SuperWebServiceG(REG_URL, data, new CallBackWebService()
                    {
                        @Override
                        public void webOnFinish(String output)
                        {

                            onFinishWebService(output);

                        }
                    }).execute();


                }
//                else
//                {
//                    startActivity(new Intent(Register_Screen.this, Verfication_Screen.class));
//                }

                break;
            case R.id.back_actionbar:

                finish();
                break;


        }

    }

    private Dialog dialog;

    private boolean ValidityCheck()
    {
        Utills_G util_ = new Utills_G();


        if (edLicencePlate.getText().toString().trim().isEmpty())
        {
            util_.PlayANim(edLicencePlate);
            return false;
        }
        else if (edPhoneNumber.getText().toString().trim().isEmpty())
        {
            util_.PlayANim(edPhoneNumber);
            return false;
        }
        else
        {
            return true;
        }


    }

    public void getRegisterationID()
    {

        new AsyncTask<Object, Object, Object>()
        {
            @Override
            protected Object doInBackground(Object... params)
            {

                String msg = "";
                try
                {
                    if (gcm == null)
                    {
                        gcm = GoogleCloudMessaging.getInstance(Register_Screen.this);
                    }
                    DeviceID = gcm.register(SENDER_ID);

                    msg = "Device registered, registration ID=" + DeviceID;

                }
                catch (Exception ex)
                {
                    DeviceID = "";
                    msg = "Error :" + ex.getMessage();

                }
                return msg;
            }

            protected void onPostExecute(Object result)
            {
                if (!DeviceID.equals(""))
                {
                    SharedPreferences sharedPref = getSharedPreferences(Global_Constants.shared_pref_name, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editPref = sharedPref.edit();
                    editPref.putString("ApplicationID", DeviceID);
                    editPref.apply();
                }
                else
                {
                    getRegisterationID();
                }

            }


        }.execute(null, null, null);

    }


    private void onFinishWebService(String response)
    {
        try
        {

            if (dialog.isShowing())
            {
                dialog.dismiss();
            }

            JSONObject jObj = new JSONObject(response);

            if (jObj.getString(Global_Constants.Status).equals(Global_Constants.success))
            {


                JSONObject jobjinner = new JSONObject(jObj.getString(Global_Constants.Message));

//                :{"CustomerId":53,"MobileVerifyCode":"4502","IsMobileVerified":false,"MobileNo":"0546330409","Latitude":0.0,"Longitude":0.0,
//                        "LicensePlate":"6734754","VechileId":150,"DeviceIMEI":"6734754"}}
                SharedPreferences shrdprf = getSharedPreferences(Global_Constants.shared_pref_name, Context.MODE_PRIVATE);
                SharedPreferences.Editor edit = shrdprf.edit();
//                edit.putString(Global_Constants.FIRSTNAME, edFirstName.getText().toString());
//                edit.putString(Global_Constants.LASTNAME, edLastName.getText().toString());
                edit.putString(Global_Constants.MOBILENO, edPhoneNumber.getText().toString());

                edit.putString(Global_Constants.CUSTOMERID, jobjinner.optString("CustomerId"));
                edit.putString(Global_Constants.LICENSEPLATE, jobjinner.optString("LicensePlate"));
                edit.putString(Global_Constants.VEHICLEID, jobjinner.optString("VehicleId"));
                edit.putString(Global_Constants.DEVICEIMEI, jobjinner.optString("DeviceIMEI"));

                edit.apply();


                SharedPreferences preference = getSharedPreferences("Preference", Context.MODE_PRIVATE);

                preference.edit().putString("language", "en").commit();


                final Dialog dialog = new Dialog(Register_Screen.this, R.style.Theme_Dialog);
                dialog.setContentView(R.layout.progress_dialog);
                dialog.show();

                new SuperWebServiceG(String.format("%sGateway/GetVehicleInfo?VehicleId=%s", Global_Constants.URL, jobjinner.optString("VehicleId")), new HashMap<String, String>(), new CallBackWebService()
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

                                StoreData.write(Register_Screen.this, new VehicleInfo(jobjinner.optString("ModelName"), jobjinner.optString("ManufactorName"), jobjinner.optString("LastUpdatedOn"), jobjinner.optString("VehicleImageURL"), jobjinner.optString("LicensePlate")));


                                new ImageDownloader(jobjinner.optString("VehicleImageURL"), new CallBackWebService()
                                {
                                    @Override
                                    public void webOnFinish(String output)
                                    {


                                        if (dialog.isShowing())
                                        {
                                            dialog.dismiss();
                                        }
                                        Intent intnt = new Intent(Register_Screen.this, Verfication_Screen.class);
                                        intnt.putExtra(Verfication_Screen.ISMOBILE_VERIFICATION, true);
                                        startActivity(intnt);
                                        finish();

                                    }
                                }, Register_Screen.this).execute();


                            }
                            else
                            {

                                if (dialog.isShowing())
                                {
                                    dialog.dismiss();
                                }

                            }


                        }
                        catch (Exception | Error e)
                        {
                            if (dialog.isShowing())
                            {
                                dialog.dismiss();
                            }


                            e.printStackTrace();
                        }


                    }
                }).execute();






















            }
            else
            {
                new Utills_G().show_dialog_msg(Register_Screen.this, jObj.getString(Global_Constants.Message), null);
            }


        }
        catch (Exception e)
        {
            new Utills_G().show_dialog_msg(Register_Screen.this, getString(R.string.please_try_again), null);
            e.printStackTrace();
        }


    }


}
