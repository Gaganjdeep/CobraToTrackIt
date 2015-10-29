package car.gagan.cobratotrackit.Classes.Fragments;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;
import android.transition.ChangeBounds;
import android.transition.Scene;
import android.transition.TransitionInflater;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;


import car.gagan.cobratotrackit.R;

import car.gagan.cobratotrackit.Classes.MainActivity;
import car.gagan.cobratotrackit.utills.CallBackDialogOption;
import car.gagan.cobratotrackit.utills.DialogReveal;

/**
 * A simple {@link Fragment} subclass.
 */
public class Home extends android.support.v4.app.Fragment
{


    public Home()
    {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {

        View v = inflater.inflate(R.layout.fragment_home, container, false);

//        settingToolBar(v);


        parentlayout = (FrameLayout) v.findViewById(R.id.parentlayout);


        displayView(new Lock_fragment());


        return v;

    }


    FrameLayout parentlayout;
    CallBackDialogOption callBack = new CallBackDialogOption()
    {
        @Override
        public void optionSelected(android.support.v4.app.Fragment fragmnt)
        {

            displayView(fragmnt);
        }
    };

//    private void settingToolBar(View v)
//    {
//
//        Toolbar toolbar = (Toolbar) v.findViewById(R.id.toolbar);
//        ImageButton btnOptions = (ImageButton) toolbar.findViewById(R.id.btnOptions);
//        btnOptions.setVisibility(View.VISIBLE);
//        btnOptions.setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View view)
//            {
//
//            }
//        });
//    }


    private void displayView(android.support.v4.app.Fragment fragment)
    {


        if (fragment != null)
        {
            FragmentManager fragmentManager = getChildFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.parentlayout, fragment).commit();


        }

    }


    @Override
    public void onResume()
    {
        super.onResume();

        if (!mIsReceiverRegistered)
        {
            if (mReceiver == null)
                mReceiver = new OpenDialogOptionBroadcastReceiverG();
            getActivity().registerReceiver(mReceiver, new IntentFilter(BROADCAST_OPENDIALOG));
            mIsReceiverRegistered = true;
        }


    }


    public static final String BROADCAST_OPENDIALOG = "gagan.open.dialog";


    @Override
    public void onPause()
    {
        super.onPause();
        if (mIsReceiverRegistered)
        {
            getActivity().unregisterReceiver(mReceiver);
            mReceiver = null;
            mIsReceiverRegistered = false;
        }
    }


    OpenDialogOptionBroadcastReceiverG mReceiver;
    private boolean mIsReceiverRegistered = false;


    private class OpenDialogOptionBroadcastReceiverG extends BroadcastReceiver
    {

        @Override
        public void onReceive(Context context, Intent intent)
        {
            DialogReveal dialogReveal = new DialogReveal(getActivity(), callBack);
            dialogReveal.show();
            dialogReveal.setOnDismissListener(dialogReveal.dismissListener);


        }
    }


}
