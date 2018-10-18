package home.oleg.placesnearme.app.di.components;

import javax.inject.Singleton;

import dagger.Component;
import home.oleg.placesnearme.api.AppApi;
import home.oleg.placesnearme.app.PlacesNearMeApp;
import home.oleg.placesnearme.app.di.modules.CoreModule;
import home.oleg.placesnearme.app.di.modules.DbModule;
import home.oleg.placesnearme.app.di.modules.InteractorModule;
import home.oleg.placesnearme.app.di.modules.NetworkModule;
import home.oleg.placesnearme.app.di.modules.RepositoryModule;
import home.oleg.placesnearme.app.di.modules.ViewModelModule;
import home.oleg.placesnearme.builder.ComponentBuilder;

@Component(modules = {
        CoreModule.class,
        DbModule.class,
        NetworkModule.class,
        RepositoryModule.class,
        InteractorModule.class,
        ViewModelModule.class})
@Singleton
public interface ApplicationComponent extends AppApi {

    @Component.Builder
    interface Builder extends ComponentBuilder<ApplicationComponent, PlacesNearMeApp> {

    }

}
