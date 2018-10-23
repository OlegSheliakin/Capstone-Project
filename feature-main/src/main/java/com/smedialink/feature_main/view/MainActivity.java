package com.smedialink.feature_main.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.smedialink.feature_main.R;
import com.smedialink.feature_main.di.DaggerMainActivityComponent;

import javax.inject.Inject;

import home.oleg.feature_favorite_venues.FavoritePlacesFragment;
import home.oleg.placesnearme.feature_map.view.VenuesMapFragment;
import home.oleg.placesnearme.feature_venues_history.VenuesHistoryFragment;

public final class MainActivity extends AppCompatActivity {

    @Inject
    BottomBarInitializer bottomBarInitializer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        injectDependencies();
        setContentView(R.layout.activity_main);
        initBottomBar();
    }

    private void injectDependencies() {
        DaggerMainActivityComponent.builder().build().inject(this);
    }

    private void initBottomBar() {
        AHBottomNavigation bottomNavigation = findViewById(R.id.bottomNavigationBar);
        bottomBarInitializer.initialize(bottomNavigation);
        bottomNavigation.setOnTabSelectedListener((position, wasSelected) -> {
            if (wasSelected) {
                return false;
            }

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

            String tag = String.valueOf(position);
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

            Fragment curFrag = getSupportFragmentManager().findFragmentById(R.id.container);
            if (curFrag != null) {
                if (curFrag instanceof VenuesMapFragment) {
                    fragmentTransaction.hide(curFrag);
                } else {
                    fragmentTransaction.detach(curFrag);
                }
            }

            Fragment curFragment = getSupportFragmentManager().findFragmentByTag(tag);
            if (curFragment == null) {
                fragmentTransaction.add(R.id.container, fragment, tag);
            } else if (curFragment instanceof VenuesMapFragment) {
                fragmentTransaction.show(curFragment);
            } else {
                fragmentTransaction.attach(curFragment);
            }

            fragmentTransaction.commit();
            return true;
        });

        bottomNavigation.setCurrentItem(1, true);
    }

}
