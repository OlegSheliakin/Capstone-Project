package home.oleg.placesnearme.di.components;

import android.arch.lifecycle.ViewModelProvider;

import dagger.BindsInstance;
import dagger.Component;
import home.oleg.placesnearme.PlacesNearMeApp;
import home.oleg.placesnearme.di.modules.CoreModule;
import home.oleg.placesnearme.di.modules.InteractorModule;
import home.oleg.placesnearme.di.modules.NetworkModule;
import home.oleg.placesnearme.di.modules.RepositoryModule;
import home.oleg.placesnearme.di.modules.ResourceModule;
import home.oleg.placesnearme.di.modules.ViewModelModule;
import home.oleg.placesnearme.network.config.NetworkConfig;

@Component(modules = {
        CoreModule.class,
        NetworkModule.class,
        RepositoryModule.class,
        InteractorModule.class,
        ViewModelModule.class,
        ResourceModule.class})
public interface ApplicationComponent {

    NetworkConfig getNetworkConfig();

    ViewModelProvider.Factory getFactory();

    @Component.Builder
    interface Builder {
        @BindsInstance
        ApplicationComponent.Builder bind(PlacesNearMeApp app);

        ApplicationComponent build();
    }

}
