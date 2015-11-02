package car.gagan.cobratotrackit.utills;


import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.OvershootInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import car.gagan.cobratotrackit.Classes.Fragments.Home;
import car.gagan.cobratotrackit.Classes.Splash_Cobra;
import car.gagan.cobratotrackit.R;

import com.google.android.gms.maps.model.LatLng;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


/**
 * Created by Gagan on 7/3/2015.
 */
public class Utills_G
{


    public static Toast toast;


    public static void showToast(String msg, Context context, boolean center)
    {
        if (toast != null)
        {
            toast.cancel();
        }
        toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
        if (center)
        {
            toast.setGravity(Gravity.CENTER, 0, 0);
        }

        toast.show();

    }

    //dialog onw button
    public static Dialog global_dialog;

    public static void show_dialog_msg(final Context con, String text, View.OnClickListener onClickListener)
    {
        global_dialog = new Dialog(con, R.style.Theme_Dialog);
        global_dialog.setContentView(R.layout.dialog_global);
        global_dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        TextView tex = (TextView) global_dialog.findViewById(R.id.text);
        Button ok = (Button) global_dialog.findViewById(R.id.ok);
        Button cancel = (Button) global_dialog.findViewById(R.id.cancel);


        tex.setText(text);


        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(global_dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

        global_dialog.show();
        global_dialog.getWindow().setAttributes(lp);


        if (onClickListener != null)
        {
            cancel.setVisibility(View.VISIBLE);
            // ok.setOnClickListener(oc);
            cancel.setOnClickListener(new View.OnClickListener()
            {

                @Override
                public void onClick(View v)
                {
                    global_dialog.dismiss();

                }
            });


            ok.setOnClickListener(onClickListener);

        }
        else
        {
            ok.setOnClickListener(new View.OnClickListener()
            {

                @Override
                public void onClick(View v)
                {
                    global_dialog.dismiss();

                }
            });
        }


    }

    static Locale myLocale;

    public static void change_language(String lang, Context con)
    {
        myLocale = new Locale(lang);
        Resources res = con.getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
    }


    public static void show_dialog_languageSelection(final Context con)
    {
        global_dialog = new Dialog(con, R.style.Theme_Dialog);
        global_dialog.setContentView(R.layout.dialog_global);
        global_dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        TextView tex = (TextView) global_dialog.findViewById(R.id.text);
        Button ok = (Button) global_dialog.findViewById(R.id.ok);
        Button cancel = (Button) global_dialog.findViewById(R.id.cancel);


        tex.setText("Please select an language..");


        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(global_dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

        global_dialog.show();
        global_dialog.getWindow().setAttributes(lp);


        cancel.setVisibility(View.VISIBLE);
        cancel.setText(R.string.hebrew);
        ok.setText(R.string.english);
        // ok.setOnClickListener(oc);
        cancel.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                global_dialog.dismiss();
                SharedPreferences preference = con.getSharedPreferences("Preference", Context.MODE_PRIVATE);

                if (!preference.getString("language", "").equals("he"))
                {

                    Utills_G.showToast(con.getString(R.string.translating_to) + " Hebrew", con, true);


                    preference.edit().putString("language", "he").apply();

                    change_language("he", con);


//                    Intent i = ((Activity) con).getBaseContext().getPackageManager()
//                            .getLaunchIntentForPackage(((Activity) con).getBaseContext().getPackageName());
//                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                    con.startActivity(i);
//                    ((Activity) con).finish();

                    ((Activity) con).recreate();
//                    Intent i1 = new Intent(con, Splash_Cobra.class);
//                    i1.setAction(Intent.ACTION_MAIN);
//                    i1.addCategory(Intent.CATEGORY_HOME);
//                    i1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                    i1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                    i1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                    con.startActivity(i1);
//                    ((Activity) con).finish();
                    Home.reStartFragments();

                }
            }
        });
        ok.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                global_dialog.dismiss();
                SharedPreferences preference = con.getSharedPreferences("Preference", Context.MODE_PRIVATE);

                if (!preference.getString("language", "").equals("en"))
                {
                    Utills_G.showToast(con.getString(R.string.translating_to) + " English", con, true);

                    preference.edit().putString("language", "en").apply();

                    change_language("en", con);

                    ((Activity) con).recreate();

                    Home.reStartFragments();

                }

            }
        });


    }


    public void PlayANim(View target)
    {

        AnimatorSet mAnimatorSet = new AnimatorSet();
        mAnimatorSet.playTogether(ObjectAnimator.ofFloat(target, "scaleX", 1, 0.8f, 0.8f, 0.9f, 0.9f, 1), ObjectAnimator.ofFloat(target, "scaleY", 1, 0.8f, 0.8f, 0.9f, 0.9f, 1), ObjectAnimator.ofFloat(target, "rotation", 0, -2, -2, 2, -2, 2, -2, 2, -2, 2, -2, 0));

        mAnimatorSet.setDuration(1000);
        mAnimatorSet.start();

    }


    public static String format_date(String date, String inputFormat, String outputFormat)
    {
        SimpleDateFormat sdfs = new SimpleDateFormat(inputFormat, Locale.US);
        Date date_ = null;
        try
        {
            date_ = sdfs.parse(date);

        }
        catch (Exception e)
        {
            date_ = null;
            e.printStackTrace();
        }
        return dateToString(date_, outputFormat);

    }


    public static String dateToString(Date date, String format)
    {

        try
        {
            String strDate = "";
            SimpleDateFormat simpledateformat = new SimpleDateFormat(format);
            strDate = simpledateformat.format(date);
            return strDate;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return "";
        }
    }


    public static void startGoogleMaps(Context con, LatLng ltlng)
    {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://maps.google.com/maps?daddr=" + ltlng.latitude + "," + ltlng.longitude));
        con.startActivity(intent);
    }


}