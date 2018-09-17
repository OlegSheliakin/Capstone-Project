package home.oleg.placesnearme.presentation.feature.main.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;

import javax.inject.Inject;

import home.oleg.placesnearme.PlacesNearMeApp;
import home.oleg.placesnearme.R;
import home.oleg.placesnearme.di.components.DaggerApplicationComponent;
import home.oleg.placesnearme.presentation.feature.favorite_places.FavoritePlacesFragment;
import home.oleg.placesnearme.presentation.feature.main.viewmodel.MainViewModel;
import home.oleg.placesnearme.presentation.feature.map.view.PlacesMapFragment;
import home.oleg.placesnearme.presentation.feature.places_history.PlacesHistoryFragment;

public final class MainActivity extends AppCompatActivity implements MainView {

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
        bottomNavigation.setOnTabSelectedListener((position, wasSelected) -> {
            if (wasSelected) {
                return false;
            }

            Fragment fragment = null;
            switch (position) {
                case 0:
                    fragment = new PlacesHistoryFragment();
                    break;
                case 1:
                    fragment = new PlacesMapFragment();
                    break;
                case 2:
                    fragment = new FavoritePlacesFragment();
                    break;
            }

            String tag = String.valueOf(position);
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

            Fragment curFrag = getSupportFragmentManager().findFragmentById(R.id.container);
            if (curFrag != null) {
                if(curFrag instanceof PlacesMapFragment) {
                    fragmentTransaction.hide(curFrag);
                } else {
                    fragmentTransaction.detach(curFrag);
                }
            }

            Fragment curFragment = getSupportFragmentManager().findFragmentByTag(tag);
            if (curFragment == null) {
                fragmentTransaction.add(R.id.container, fragment, tag);
            } else if (curFragment instanceof PlacesMapFragment){
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
