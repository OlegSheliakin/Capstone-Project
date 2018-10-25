package com.smedialink.feature_main.view;

import android.graphics.Color;
import android.view.View;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.smedialink.feature_main.R;

import javax.inject.Inject;

import home.oleg.placesnearme.core_presentation.ShowHideBottomBarListener;
import home.oleg.placesnearme.core_presentation.viewdata.VenueViewData;

/**
 * Created by Oleg Sheliakin on 04.09.2018.
 * Contact me by email - olegsheliakin@gmail.com
 */
public class BottomBarDelegate implements ShowHideBottomBarListener {

    private View containerBottomBar;
    private BottomBarTabListener bottomBarTabListener;

    @Inject
    public BottomBarDelegate(BottomBarTabListener bottomBarTabListener) {
        this.bottomBarTabListener = bottomBarTabListener;
    }

    public void attach(View containerBottomBar, AHBottomNavigation bottomNavigation) {
        this.containerBottomBar = containerBottomBar;
        bottomNavigation.setAccentColor(Color.WHITE);
        bottomNavigation.setInactiveColor(Color.WHITE);
        bottomNavigation.setDefaultBackgroundColor(Color.TRANSPARENT);
        bottomNavigation.setTitleState(AHBottomNavigation.TitleState.ALWAYS_HIDE);

        AHBottomNavigationItem item1 = new AHBottomNavigationItem(null, R.drawable.fab_location_pin_state_drawable);
        AHBottomNavigationItem item2 = new AHBottomNavigationItem(null, R.drawable.button_map);
        AHBottomNavigationItem item3 = new AHBottomNavigationItem(null, R.drawable.fab_favorite_state_drawable);

        bottomNavigation.addItem(item1);
        bottomNavigation.addItem(item2);
        bottomNavigation.addItem(item3);

        bottomNavigation.setOnTabSelectedListener(bottomBarTabListener);
        bottomNavigation.setCurrentItem(1);
    }

    @Override
    public void showBottomBar() {
        containerBottomBar.animate().translationY(containerBottomBar.getHeight()).start();
    }

    @Override
    public void hideBottomBar() {
        containerBottomBar.animate().translationY(0).start();
    }

    @Override
    public void showVenueDetail(VenueViewData venueViewData) {

    }
}
