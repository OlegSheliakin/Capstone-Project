package com.smedialink.feature_main.view;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.smedialink.feature_main.R;

import home.oleg.feature_favorite_venues.FavoritePlacesFragment;
import home.oleg.placesnearme.feature_map.view.VenuesMapFragment;
import home.oleg.placesnearme.feature_venues_history.VenuesHistoryFragment;

public class BottomBarTabListener implements AHBottomNavigation.OnTabSelectedListener {

    private final FragmentManager fragmentManager;

    public BottomBarTabListener(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }

    @Override
    public boolean onTabSelected(int position, boolean wasSelected) {
        if (wasSelected) {
            return false;
        }

        String tag = String.valueOf(position);
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        Fragment curFrag = fragmentManager.findFragmentById(R.id.container);
        if (curFrag != null) {
            if (curFrag instanceof VenuesMapFragment) {
                fragmentTransaction.hide(curFrag);
            } else {
                fragmentTransaction.detach(curFrag);
            }
        }

        Fragment curFragment = fragmentManager.findFragmentByTag(tag);
        if (curFragment == null) {
            Fragment fragment;
            switch (position) {
                case 0:
                    fragment = new VenuesHistoryFragment();
                    break;
                case 1:
                    fragment = new VenuesMapFragment();
                    break;
                case 2:
                    fragment = new FavoritePlacesFragment();
                    break;
                default:
                    fragment = new VenuesMapFragment();
                    break;
            }
            fragmentTransaction.add(R.id.container, fragment, tag);
        } else if (curFragment instanceof VenuesMapFragment) {
            fragmentTransaction.show(curFragment);
        } else {
            fragmentTransaction.attach(curFragment);
        }

        fragmentTransaction.commit();
        return true;
    }
}
