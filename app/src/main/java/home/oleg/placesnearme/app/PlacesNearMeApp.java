package home.oleg.placesnearme.app;

import android.app.Application;

import home.oleg.placesnearme.AppApiProvider;
import home.oleg.placesnearme.api.AppApi;
import home.oleg.placesnearme.app.di.components.ApplicationComponent;
import home.oleg.placesnearme.app.di.components.DaggerApplicationComponent;

public class PlacesNearMeApp extends Application implements AppApiProvider {
    private ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        applicationComponent = DaggerApplicationComponent.builder().bind(this).build();
    }

    @Override
    public AppApi getAppApi() {
        return applicationComponent;
    }
}
