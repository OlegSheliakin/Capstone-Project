package home.oleg.placesnearme.presentation.feature.main.di;

import dagger.BindsInstance;
import dagger.Component;
import dagger.Subcomponent;
import home.oleg.placesnearme.di.components.ApplicationComponent;
import home.oleg.placesnearme.network.di.NetworkComponent;
import home.oleg.placesnearme.presentation.feature.main.view.MainActivity;
import home.oleg.placesnearme.presentation.feature.map.di.PlacesMapFragmentComponent;

@Component(dependencies = {ApplicationComponent.class, NetworkComponent.class},
        modules = MainViewModule.class)
public interface MainActivityComponent {

    PlacesMapFragmentComponent.Builder pBuilder();

    void inject(MainActivity target);

    @Component.Builder
    interface Builder {
        MainActivityComponent.Builder appComponent(ApplicationComponent applicationComponent);

        MainActivityComponent.Builder networkComponent(NetworkComponent networkComponent);

        @BindsInstance
        MainActivityComponent.Builder bind(MainActivity mainActivity);

        MainActivityComponent build();
    }
}
