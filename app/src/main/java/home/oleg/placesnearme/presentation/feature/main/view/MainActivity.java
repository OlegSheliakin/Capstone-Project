package home.oleg.placesnearme.presentation.feature.main.view;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.google.android.gms.maps.SupportMapFragment;

import javax.inject.Inject;

import home.oleg.placesnearme.PlacesNearMeApp;
import home.oleg.placesnearme.R;
import home.oleg.placesnearme.di.components.DaggerApplicationComponent;
import home.oleg.placesnearme.presentation.feature.map.view.MapViewDelegate;
import home.oleg.placesnearme.presentation.feature.map.viewmodel.MapViewModel;

public final class MainActivity extends AppCompatActivity implements MainView {

    @Inject
    MapViewDelegate mapDelegate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        injectDependencies();
        setContentView(R.layout.activity_main);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(mapDelegate);

        mapDelegate.attachLifecycleOwner(this);


        AHBottomNavigation bottomNavigation = findViewById(R.id.navigation);
        bottomNavigation.setDefaultBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary));
        bottomNavigation.setAccentColor(Color.WHITE);
        bottomNavigation.setTitleState(AHBottomNavigation.TitleState.ALWAYS_HIDE);

        AHBottomNavigationItem item1 = new AHBottomNavigationItem(null, R.drawable.ic_map_view);
        AHBottomNavigationItem item2 = new AHBottomNavigationItem(null, R.drawable.ic_place);
        AHBottomNavigationItem item3 = new AHBottomNavigationItem(null, R.drawable.ic_add_favorite);
        bottomNavigation.addItem(item1);
        bottomNavigation.addItem(item2);
        bottomNavigation.addItem(item3);
    }

    void injectDependencies() {
        DaggerApplicationComponent.builder()
                .bind((PlacesNearMeApp) getApplication())
                .build()
                .mainActivityComponentBuilder()
                .bind(this)
                .build()
                .inject(this);
    }

}
