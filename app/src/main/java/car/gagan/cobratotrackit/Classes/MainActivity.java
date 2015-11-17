package car.gagan.cobratotrackit.Classes;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import car.gagan.cobratotrackit.Classes.Fragments.Home;
import car.gagan.cobratotrackit.Classes.Fragments.LocationFragment;
import car.gagan.cobratotrackit.Classes.Fragments.Notification;
import car.gagan.cobratotrackit.Classes.Fragments.Settings;
import car.gagan.cobratotrackit.Classes.Fragments.TripReport;
import car.gagan.cobratotrackit.Classes.Fragments.TripReportDaily;
import car.gagan.cobratotrackit.R;

public class MainActivity extends AppCompatActivity
{


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPagerG = (ViewPager) findViewById(R.id.viewpager);
        if (viewPagerG != null)
        {
            setupViewPager(viewPagerG);
        }


        tabLayoutG = (TabLayout) findViewById(R.id.tabs);
        tabLayoutG.setSelectedTabIndicatorColor(getResources().getColor(R.color.red));
        tabLayoutG.setupWithViewPager(viewPagerG);


        setupTabLayout(tabLayoutG, viewPagerG);


//        tabLayoutG.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener()
//        {
//            @Override
//            public void onTabSelected(TabLayout.Tab tab)
//            {
//                viewPagerG.setCurrentItem(tab.getPosition());
//
//            }
//
//            @Override
//            public void onTabUnselected(TabLayout.Tab tab)
//            {
//
//            }
//
//            @Override
//            public void onTabReselected(TabLayout.Tab tab)
//            {
//                if (tab.getPosition() == 0 && lastTab == 0)
//                {
//                    Intent intent = new Intent(Home.BROADCAST_OPENDIALOG);
//                    sendBroadcast(intent);
//                }
//            }
//        });


    }


    TabLayout tabLayoutG;
    ViewPager viewPagerG;

    private void setupViewPager(ViewPager viewPager)
    {
        Adapter adapter = new Adapter(getSupportFragmentManager());
        adapter.addFragment(new Home(), "Home");
        adapter.addFragment(new TripReportDaily(), "TripReport");
        adapter.addFragment(new Notification(), "notifications");
        adapter.addFragment(new LocationFragment(), "location");
        adapter.addFragment(new Settings(), "Settings");
        viewPager.setAdapter(adapter);


    }

    public void setupTabLayout(TabLayout tabLayout, final ViewPager mViewpager)
    {
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
//        tabLayout.setTabGravity(TabLayout.GRAVITY_CENTER);
        tabLayout.setupWithViewPager(mViewpager);


        for (int i = 0; i < tabLayout.getTabCount(); i++)
        {
            ImageView tab = (ImageView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);

            tab.setBackgroundResource(selectedIcons[i]);

            tabLayout.getTabAt(i).setCustomView(tab);


            tab.setSelected(i == 0);


            if (i == 0)
            {
                tab.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View view)
                    {
                        if (mViewpager.getCurrentItem() == 0)
                        {
                            Intent intent = new Intent(Home.BROADCAST_OPENDIALOG);
                            sendBroadcast(intent);
                        }
                    }
                });
            }


        }


        //..
    }


    final private int selectedIcons[] = {R.drawable.tab_car_selector, R.drawable.tab_trip_report_selector, R.drawable.tab_notification_selector, R.drawable.tab_getlocation_selector, R.drawable.tab_settings_selector};


    static class Adapter extends FragmentPagerAdapter
    {
        private final List<Fragment> mFragments = new ArrayList<>();
        private final List<String> mFragmentTitles = new ArrayList<>();

        public Adapter(FragmentManager fm)
        {
            super(fm);
        }

        public void addFragment(Fragment fragment, String title)
        {
            mFragments.add(fragment);
            mFragmentTitles.add(title);
        }

        @Override
        public Fragment getItem(int position)
        {
            return mFragments.get(position);
        }

        @Override
        public int getCount()
        {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position)
        {
            return mFragmentTitles.get(position);
        }
    }

}
