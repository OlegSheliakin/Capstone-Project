package home.oleg.placesnearme.presentation.feature.main.view;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.maps.SupportMapFragment;

import javax.inject.Inject;

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
    }

    void injectDependencies() {
        DaggerApplicationComponent.create().mainActivityComponentBuilder()
                .bind(this)
                .build()
                .inject(this);
    }

}
