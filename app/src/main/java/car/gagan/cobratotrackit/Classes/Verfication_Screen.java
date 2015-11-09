package car.gagan.cobratotrackit.Classes;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import car.gagan.cobratotrackit.R;

import org.json.JSONObject;

import java.util.HashMap;

import car.gagan.cobratotrackit.utills.CallBackWebService;
import car.gagan.cobratotrackit.utills.Global_Constants;
import car.gagan.cobratotrackit.utills.Utills_G;
import car.gagan.cobratotrackit.webservice.SuperWebServiceG;

public class Verfication_Screen extends AppCompatActivity
{
    static EditText txt1;
    static EditText txt2;
    static EditText txt3;
    static EditText txt4;

    Button btnDone;
    SharedPreferences shrd;
    Utills_G utilss;

    private final String VERIFICATION_URL = Global_Constants.URL + "Customer/ValidateMobileCode";

    boolean isMobileVerification;
    private Dialog dialog;

    public static String ISMOBILE_VERIFICATION = "ismobile";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verfication__screen);


        Intent intn = getIntent();

        isMobileVerification = intn.getBooleanExtra(ISMOBILE_VERIFICATION, false);


        shrd = getSharedPreferences(Global_Constants.shared_pref_name, Context.MODE_PRIVATE);
        utilss = new Utills_G();

        txt1 = (EditText) findViewById(R.id.txtCode1);
        txt2 = (EditText) findViewById(R.id.txtCode2);
        txt3 = (EditText) findViewById(R.id.txtCode3);
        txt4 = (EditText) findViewById(R.id.txtCode4);

        btnDone = (Button) findViewById(R.id.btnDone);


        txt1.addTextChangedListener(new text(txt1));
        txt2.addTextChangedListener(new text(txt2));
        txt3.addTextChangedListener(new text(txt3));

        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(txt1, InputMethodManager.SHOW_IMPLICIT);

        txt4.addTextChangedListener(new TextWatcher()
        {

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3)
            {
                if (txt4.getText().toString().trim().equals(""))
                {
//                    txt4.setBackgroundResource(R.drawable.round_notfill);
                    txt3.requestFocus();
                }
                else
                {
//                    txt4.setBackgroundResource(car.gagan.cobratotrackit.R.drawable.round_fill);

                }

            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3)
            {


            }

            @Override
            public void afterTextChanged(Editable arg0)
            {


            }
        });


    }


    public void doNe(View v)
    {


        if (validateCode())
        {

            dialog = new Dialog(Verfication_Screen.this, R.style.Theme_Dialog);
            dialog.setContentView(R.layout.progress_dialog);
            dialog.show();


            if (isMobileVerification)
            {
                HashMap<String, String> dataHashMap = new HashMap<>();
                dataHashMap.put("CustomerId", shrd.getString("CustomerId", ""));
                dataHashMap.put("MobileVerifyCode", txt1.getText().toString() + txt2.getText().toString() + txt3.getText().toString() + txt4.getText().toString());

                fetchData(VERIFICATION_URL, dataHashMap);


            }
            else
            {

                SharedPreferences shrdPref = getSharedPreferences(Global_Constants.shared_pref_name, Context.MODE_PRIVATE);

                String url_ = Global_Constants.URL + "Customer/ValidateImmobilizerCode?UnitId=" + shrdPref.getString(Global_Constants.DEVICEIMEI, "") + "&Code=" + txt1.getText().toString() + txt2.getText().toString() + txt3.getText().toString() + txt4.getText().toString();


                fetchData(url_, new HashMap<String, String>());
            }


        }
//        else
//        {
//            startActivity(new Intent(Verfication_Screen.this, MainActivity.class));
//        }

    }


    private void fetchData(String url, HashMap<String, String> datahashMap)
    {
        new SuperWebServiceG(url, datahashMap, new CallBackWebService()
        {
            @Override
            public void webOnFinish(String output)
            {
                onFinishWebService(output);
            }
        }).execute();
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

                startActivity(new Intent(Verfication_Screen.this, MainActivity.class));
                finish();
            }
            else
            {
                new Utills_G().show_dialog_msg(Verfication_Screen.this, jObj.getString(Global_Constants.Message), null);
            }


        }
        catch (Exception e)
        {
            new Utills_G().show_dialog_msg(Verfication_Screen.this, getString(R.string.please_try_again), null);
            e.printStackTrace();
        }


    }


    private boolean validateCode()
    {
        if (txt1.getText().toString().isEmpty())
        {
            utilss.PlayANim(txt1);
            return false;
        }
        else if (txt2.getText().toString().isEmpty())
        {
            utilss.PlayANim(txt2);
            return false;
        }
        else if (txt2.getText().toString().isEmpty())
        {
            utilss.PlayANim(txt3);
            return false;
        }
        else if (txt2.getText().toString().isEmpty())
        {
            utilss.PlayANim(txt4);
            return false;
        }
        else
        {
            return true;
        }


    }


    public static class text implements TextWatcher
    {
        EditText ed;

        public text(EditText ed)
        {
            this.ed = ed;
        }

        @Override
        public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3)
        {
            if (ed.getId() == R.id.txtCode1)
            {
                if (ed.getText().toString().trim().equals(""))
                {
//

                }
                else
                {
                    txt2.requestFocus();
                }

            }
            else if (ed.getId() == R.id.txtCode2)
            {

                if (ed.getText().toString().trim().equals(""))
                {
                    txt1.requestFocus();
                }
                else
                {
                    txt3.requestFocus();
                }
            }
            else if (ed.getId() == R.id.txtCode3)
            {
                if (ed.getText().toString().trim().equals(""))
                {
                    txt2.requestFocus();
                }
                else
                {
                    txt4.requestFocus();
                }
            }

        }

        @Override
        public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3)
        {

        }

        @Override
        public void afterTextChanged(Editable arg0)
        {

        }
    }


}
