package car.gagan.cobratotrackit.Classes;

import android.animation.Animator;
import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.transition.Transition;
import android.view.MenuItem;
import android.view.ViewAnimationUtils;
import android.widget.TextView;

import car.gagan.cobratotrackit.Classes.Fragments.TripReport;
import car.gagan.cobratotrackit.R;
import car.gagan.cobratotrackit.utills.Global_Constants;
import car.gagan.cobratotrackit.utills.Utills_G;

public class TripReportActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_report);

        String DateToShow = getIntent().getStringExtra("date");


        setupToolbar(DateToShow);

        setupWindowAnimations();

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.layoutTripReport, new TripReport(DateToShow)).commit();


    }

    private void setupWindowAnimations()
    {

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            getWindow().getEnterTransition().setDuration(600);
            getWindow().getEnterTransition().addListener(new Transition.TransitionListener()
            {
                @Override
                public void onTransitionStart(Transition transition)
                {
                    toolbar.setBackgroundColor(getResources().getColor(R.color.background));
                }

                @Override
                public void onTransitionCancel(Transition transition)
                {
                }

                @Override
                public void onTransitionPause(Transition transition)
                {
                }

                @Override
                public void onTransitionResume(Transition transition)
                {
                }

                @TargetApi(Build.VERSION_CODES.LOLLIPOP)
                @Override
                public void onTransitionEnd(Transition transition)
                {
                    getWindow().getEnterTransition().removeListener(this);

                    setBackButton();

                    // get the center for the clipping circle
                    int cx = toolbar.getMeasuredWidth() / 2;
                    int cy = toolbar.getMeasuredHeight() / 2;

                    // get the final radius for the clipping circle
                    float finalRadius = (float) Math.hypot(toolbar.getWidth(), toolbar.getHeight());
//                  float finalRadius =v.getWidth() / 2;

                    // create the animator for this view (the start radius is zero)
                    Animator anim = ViewAnimationUtils.createCircularReveal(toolbar, cx, cy, 2, finalRadius);
                    toolbar.setBackgroundColor(getResources().getColor(R.color.grey));
                    anim.setDuration(600);
                    anim.start();


                }
            });
        }
        else
        {
            setBackButton();
        }


    }


    Toolbar toolbar;

    private void setupToolbar(String date)
    {
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        ((TextView) toolbar.findViewById(R.id.txtvHeading)).setText(Utills_G.format_date(TripReportActivity.this, date, Global_Constants.SERVERTIME_FORMAT, Global_Constants.TIMEFORMAT_TRIP_DAILY));

        try
        {
            setSupportActionBar(toolbar);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private void setBackButton()
    {
        try
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {

        switch (item.getItemId())
        {

            case android.R.id.home:

                onBackPressed();

                return true;

        }

        return super.onOptionsItemSelected(item);
    }

}
