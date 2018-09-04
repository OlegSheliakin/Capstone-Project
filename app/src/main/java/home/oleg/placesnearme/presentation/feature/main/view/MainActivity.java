package home.oleg.placesnearme.presentation.feature.main.view;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.google.android.gms.maps.SupportMapFragment;

import javax.inject.Inject;

import home.oleg.placesnearme.PlacesNearMeApp;
import home.oleg.placesnearme.R;
import home.oleg.placesnearme.di.components.DaggerApplicationComponent;
import home.oleg.placesnearme.presentation.feature.main.viewmodel.MainViewModel;
import home.oleg.placesnearme.presentation.feature.map.view.MapViewDelegate;

public final class MainActivity extends AppCompatActivity implements MainView {

    @Inject
    MapViewDelegate mapDelegate;

    @Inject
    BottomBarInitializer bottomBarInitializer;

    @Inject
    MainViewModel mainViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        injectDependencies();
        setContentView(R.layout.activity_main);
        initBottomBar();
        initMapDelegate();
    }

    private void injectDependencies() {
        DaggerApplicationComponent.builder()
                .bind((PlacesNearMeApp) getApplication())
                .build()
                .mainActivityComponentBuilder()
                .bind(this)
                .build()
                .inject(this);
    }

    private void initBottomBar() {
        AHBottomNavigation bottomNavigation = findViewById(R.id.bottomNavigationBar);
        bottomBarInitializer.initialize(bottomNavigation);
    }

    private void initMapDelegate() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(mapDelegate);
        mapDelegate.attachLifecycleOwner(this);
    }
}
