package home.oleg.placesnearme.presentation.feature.map.di;

import dagger.BindsInstance;
import dagger.Subcomponent;
import home.oleg.placesnearme.presentation.feature.main.view.MainActivity;
import home.oleg.placesnearme.presentation.feature.map.view.PlacesMapFragment;

@Subcomponent(modules = PlacesMapFragmentModule.class)
public interface PlacesMapFragmentComponent {

    void inject(PlacesMapFragment target);

    @Subcomponent.Builder
    interface Builder {
        @BindsInstance
        PlacesMapFragmentComponent.Builder bind(PlacesMapFragment mapFragment);
        PlacesMapFragmentComponent build();
    }
}
