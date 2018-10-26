package com.smedialink.feature_main.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.smedialink.feature_main.R;
import com.smedialink.feature_main.di.MainActivityComponent;
import com.smedialink.feature_venue_detail.venue.view.VenueFragment;

import javax.inject.Inject;

import home.oleg.placesnearme.core_presentation.ShowHideBottomBarListener;
import home.oleg.placesnearme.core_presentation.viewdata.VenueViewData;

public final class MainActivity extends AppCompatActivity implements ShowHideBottomBarListener {

    @Inject
    BottomBarDelegate bottomBarDelegate;

    private VenueFragment venueFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        injectDependencies();
        setContentView(R.layout.activity_main);

        venueFragment = (VenueFragment) getSupportFragmentManager().findFragmentById(R.id.venueFragment);
        initBottomBar(savedInstanceState);
    }

    @Override
    public void showBottomBar() {
        bottomBarDelegate.showBottomBar();
    }

    @Override
    public void hideBottomBar() {
        bottomBarDelegate.hideBottomBar();
    }

    @Override
    public void showVenueDetail(VenueViewData venueViewData) {
        venueFragment.showVenue(venueViewData.getId());
    }

    private void injectDependencies() {
        MainActivityComponent.Injector.inject(this);
    }

    private void initBottomBar(Bundle savedInstanceState) {
        AHBottomNavigation bottomNavigation = findViewById(R.id.bottomNavigationBar);
        View containerBottomBar = findViewById(R.id.containerBottomBar);
        bottomBarDelegate.attach(containerBottomBar, bottomNavigation, savedInstanceState);
    }

}
