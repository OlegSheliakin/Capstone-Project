package com.smedialink.feature_main.view;

import android.graphics.Color;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.smedialink.feature_main.R;

import javax.inject.Inject;

/**
 * Created by Oleg Sheliakin on 04.09.2018.
 * Contact me by email - olegsheliakin@gmail.com
 */
public class BottomBarInitializer {

    @Inject
    public BottomBarInitializer() {

    }

    public void initialize(AHBottomNavigation bottomNavigation) {
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
    }

}
