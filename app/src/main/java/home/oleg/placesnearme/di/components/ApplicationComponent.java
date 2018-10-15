package home.oleg.placesnearme.di.components;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import home.oleg.placesnearme.PlacesNearMeApp;
import home.oleg.placesnearme.api.AppApi;
import home.oleg.placesnearme.builder.ComponentBuilder;
import home.oleg.placesnearme.di.modules.CoreModule;
import home.oleg.placesnearme.di.modules.DbModule;
import home.oleg.placesnearme.di.modules.InteractorModule;
import home.oleg.placesnearme.di.modules.NetworkModule;
import home.oleg.placesnearme.di.modules.RepositoryModule;
import home.oleg.placesnearme.di.modules.ViewModelModule;

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
