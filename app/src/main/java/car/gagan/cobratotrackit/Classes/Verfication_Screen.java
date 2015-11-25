package car.gagan.cobratotrackit.Classes;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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


        ((TextView) findViewById(R.id.txtv)).setText(isMobileVerification ? getString(R.string.enter_sms_code) : getString(R.string.enter_keypad_code));


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


        txt2.setOnKeyListener(new onKeypressEdittext(txt2));
        txt3.setOnKeyListener(new onKeypressEdittext(txt3));
        txt4.setOnKeyListener(new onKeypressEdittext(txt4));


        txt2.setOnFocusChangeListener(new onFocusG(txt2));
        txt3.setOnFocusChangeListener(new onFocusG(txt3));
        txt1.setOnFocusChangeListener(new onFocusG(txt1));


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
                Utills_G.show_dialog_msg(Verfication_Screen.this, jObj.getString(Global_Constants.Message), null);
            }


        }
        catch (Exception e)
        {
            Utills_G.show_dialog_msg(Verfication_Screen.this, getString(R.string.please_try_again), null);
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


    class onFocusG implements View.OnFocusChangeListener
    {
        EditText ed;

        public onFocusG(EditText ed)
        {
            this.ed = ed;
        }

        @Override
        public void onFocusChange(View view, boolean b)
        {
            if (!ed.getText().toString().isEmpty())
            {
                if (ed.isFocused())
                {
                    ed.setSelection(0, 1);
                }
            }
        }
    }

    class onKeypressEdittext implements View.OnKeyListener
    {
        EditText ed;

        public onKeypressEdittext(EditText ed)
        {
            this.ed = ed;
        }

        @Override
        public boolean onKey(View view, int i, KeyEvent keyEvent)
        {


            if (i == KeyEvent.KEYCODE_DEL)
            {
                if (ed.getText().toString().isEmpty())
                {
                    if (ed.getId() == R.id.txtCode2)
                    {


                        txt1.requestFocus();

                    }
                    else if (ed.getId() == R.id.txtCode3)
                    {

                        txt2.requestFocus();

                    }
                    else if (ed.getId() == R.id.txtCode4)
                    {

                        txt3.requestFocus();

                    }
                }
            }
            return false;
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
        public void onTextChanged(final CharSequence s, int arg1, int arg2, int arg3)
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
                else if (s.length() > 1)
                {
                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable()
                    {
                        public void run()
                        {
                            String enter = new String(s.toString().substring(1));
                            ed.setText(enter);

                        }
                    });
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
                else if (s.length() > 1)
                {
                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable()
                    {
                        public void run()
                        {
                            String enter = new String(s.toString().substring(1));
                            ed.setText(enter);

                        }
                    });
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
