package home.oleg.placesnearme.di.components;

import dagger.Component;
import home.oleg.placesnearme.presentation.feature.map.view.MapActivity;
import home.oleg.placesnearme.di.modules.NetworkModule;
import home.oleg.placesnearme.di.modules.RepositoryModule;

@Component(modules = {NetworkModule.class, RepositoryModule.class})
public interface ApplicationComponent {

    void inject(MapActivity mapActivity);

}
