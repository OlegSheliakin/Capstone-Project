package home.oleg.placesnearme.di.components;

import dagger.BindsInstance;
import dagger.Component;
import home.oleg.placesnearme.PlacesNearMeApp;
import home.oleg.placesnearme.di.modules.ResourceModule;
import home.oleg.placesnearme.di.modules.CoreModule;
import home.oleg.placesnearme.di.modules.InteractorModule;
import home.oleg.placesnearme.di.modules.NetworkModule;
import home.oleg.placesnearme.di.modules.RepositoryModule;
import home.oleg.placesnearme.di.modules.ViewModelModule;
import home.oleg.placesnearme.presentation.feature.main.di.MainActivityComponent;
import home.oleg.placesnearme.presentation.feature.map.di.PlacesMapFragmentComponent;
import home.oleg.placesnearme.presentation.feature.venue.di.VenueDetailsFragmentComponent;
import home.oleg.placesnearme.presentation.feature.venue.view.VenueDetailsFragment;

@Component(modules = {CoreModule.class,
        NetworkModule.class,
        RepositoryModule.class,
        InteractorModule.class,
        ViewModelModule.class,
        ResourceModule.class})
public interface ApplicationComponent {
    MainActivityComponent.Builder mainActivityComponentBuilder();
    PlacesMapFragmentComponent.Builder placeMapFragmentComponentBuilder();

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder bind(PlacesNearMeApp app);

        ApplicationComponent build();
    }

}
