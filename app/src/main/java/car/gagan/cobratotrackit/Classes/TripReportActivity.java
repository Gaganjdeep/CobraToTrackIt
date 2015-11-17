package car.gagan.cobratotrackit.Classes;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import car.gagan.cobratotrackit.Classes.Fragments.TripReport;
import car.gagan.cobratotrackit.Classes.Fragments.VehicleInfofragment;
import car.gagan.cobratotrackit.R;

public class TripReportActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_report);


        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.layoutTripReport, new TripReport(getIntent().getStringExtra("date"))).commit();


    }


}
