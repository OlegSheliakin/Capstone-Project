package home.oleg.placesnearme.presentation.feature.main.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.maps.SupportMapFragment;

import javax.inject.Inject;

import home.oleg.placesnearme.R;
import home.oleg.placesnearme.di.components.DaggerApplicationComponent;
import home.oleg.placesnearme.presentation.feature.map.view.MapViewDelegate;

public final class MainActivity extends AppCompatActivity implements MainView {

    @Inject
    MapViewDelegate mapDelegate;

    @Inject
    SupportMapFragment mapFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        injectDependencies();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getLifecycle().addObserver(mapDelegate);
        mapFragment.getMapAsync(mapDelegate);
    }

    void injectDependencies() {
        DaggerApplicationComponent.create().mainActivityComponent().inject(this);
    }

}
