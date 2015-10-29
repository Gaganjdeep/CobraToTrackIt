package car.gagan.cobratotrackit.utills;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.RelativeLayout;


import car.gagan.cobratotrackit.Classes.Fragments.Engine_fragment;
import car.gagan.cobratotrackit.Classes.Fragments.Lights_fragment;
import car.gagan.cobratotrackit.Classes.Fragments.Lock_fragment;
import car.gagan.cobratotrackit.Classes.Fragments.Tracking_fragment;
import car.gagan.cobratotrackit.R;

/**
 * Created by gagandeep on 16/10/15.
 */
public class DialogReveal extends Dialog implements View.OnClickListener
{

    private CallBackDialogOption callBack;

    public DialogReveal(Context context, CallBackDialogOption callBack)
    {
        super(context);

        this.callBack = callBack;
    }

    FloatingActionButton btnLight, btnLock, btnAlarm, btnTracking;
    RelativeLayout layoutMain;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_options);
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        getWindow().setGravity(Gravity.BOTTOM);


        layoutMain = (RelativeLayout) findViewById(R.id.layoutDialog);
        layoutMain.setVisibility(View.INVISIBLE);
        layoutMain.post(new Runnable()
        {
            @Override
            public void run()
            {
                enterReveal(layoutMain);
            }
        });


        layoutMain.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent)
            {
                if (motionEvent.getY() > view.getMeasuredHeight() - 50 && motionEvent.getX() < 50)
                    DialogReveal.this.dismiss();

                return false;
            }
        });


        btnLight = (FloatingActionButton) findViewById(R.id.btnLights);
        btnLock = (FloatingActionButton) findViewById(R.id.btnLock);
        btnAlarm = (FloatingActionButton) findViewById(R.id.btnAlarm);
        btnTracking = (FloatingActionButton) findViewById(R.id.btntracking);

        btnTracking.setOnClickListener(this);
        btnLock.setOnClickListener(this);
        btnAlarm.setOnClickListener(this);
        btnLight.setOnClickListener(this);

    }


    public OnDismissListener dismissListener = new OnDismissListener()
    {
        @Override
        public void onDismiss(DialogInterface dialogInterface)
        {


        }
    };


    @Override
    public void dismiss()
    {


        exitReveal(layoutMain);


        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                DialogReveal.super.dismiss();
            }
        }, android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP ? 400 : 650);

    }

    void enterReveal(final RelativeLayout v)
    {


        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            // get the center for the clipping circle
            int cx = 0;
            int cy = v.getMeasuredHeight();

            // get the final radius for the clipping circle
            float finalRadius = (float) Math.hypot(v.getWidth(), v.getHeight());
//            float finalRadius =v.getWidth() / 2;

            // create the animator for this view (the start radius is zero)
            Animator anim = ViewAnimationUtils.createCircularReveal(v, cx, cy, 0, finalRadius);
            v.setVisibility(View.VISIBLE);
            anim.setDuration(550);
            anim.start();
            anim.addListener(new AnimatorListenerAdapter()
            {
                @Override
                public void onAnimationEnd(Animator animation)
                {

                    animateButtonsIn(v, 1);


                    super.onAnimationEnd(animation);
                }
            });
        }
        else
        {
            v.setVisibility(View.VISIBLE);
            animateButtonsIn(v, 1);
        }
    }


    private void animateButtonsIn(RelativeLayout layoutContainerAll, int scale)
    {
        for (int i = 0; i < layoutContainerAll.getChildCount(); i++)
        {

            View rowView = layoutContainerAll.getChildAt(i);

            rowView.animate().setStartDelay(i * 100)
                    .scaleX(scale).scaleY(scale);
        }
    }


    void exitReveal(final RelativeLayout v)
    {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            // get the center for the clipping circle
            int cx = 0;
            int cy = v.getMeasuredHeight();

            // get the initial radius for the clipping circle
//            int initialRadius = v.getWidth() / 2;
            float initialRadius = (float) Math.hypot(v.getWidth(), v.getHeight());

            // create the animation (the final radius is zero)
            Animator anim =
                    ViewAnimationUtils.createCircularReveal(v, cx, cy, initialRadius, 0);
            anim.setDuration(400);
            // make the view invisible when the animation is done
            anim.addListener(new AnimatorListenerAdapter()
            {
                @Override
                public void onAnimationEnd(Animator animation)
                {
                    super.onAnimationEnd(animation);
                    v.setVisibility(View.INVISIBLE);


                }
            });

            // start the animation
            anim.start();
        }
        else
        {
            animateButtonsIn(v, 0);
        }
    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.btnLights:

                this.dismiss();
                this.callBack.optionSelected(new Lights_fragment());

                break;


            case R.id.btnLock:


                this.dismiss();
                this.callBack.optionSelected(new Lock_fragment());

                break;


            case R.id.btnAlarm:

                this.dismiss();
                this.callBack.optionSelected(new Engine_fragment());

                break;


            case R.id.btntracking:

                this.dismiss();
                this.callBack.optionSelected(new Tracking_fragment());

                break;

        }
    }
}
