package home.oleg.placesnearme.di.components;

import dagger.Component;
import home.oleg.placesnearme.di.modules.CoreModule;
import home.oleg.placesnearme.di.modules.NetworkModule;
import home.oleg.placesnearme.di.modules.RepositoryModule;
import home.oleg.placesnearme.presentation.feature.main.di.MainActivityComponent;

@Component(modules = {CoreModule.class, NetworkModule.class, RepositoryModule.class})
public interface ApplicationComponent {

    MainActivityComponent mainActivityComponent();

}
