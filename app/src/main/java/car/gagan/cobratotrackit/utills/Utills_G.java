package car.gagan.cobratotrackit.utills;


import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.OvershootInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


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