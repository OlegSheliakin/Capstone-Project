package home.oleg.placesnearme.di.components;

import dagger.Component;
import home.oleg.placesnearme.presentation.feature.main.di.MainActivityComponent;
import home.oleg.placesnearme.presentation.feature.main.view.MainActivity;
import home.oleg.placesnearme.presentation.feature.map.view.MapActivity;
import home.oleg.placesnearme.di.modules.NetworkModule;
import home.oleg.placesnearme.di.modules.RepositoryModule;

@Component(modules = {NetworkModule.class, RepositoryModule.class})
public interface ApplicationComponent {

    MainActivityComponent mainActivityComponent();

}
