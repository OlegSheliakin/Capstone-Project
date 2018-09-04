package home.oleg.placesnearme.presentation.feature.main.di;

import dagger.BindsInstance;
import dagger.Subcomponent;
import home.oleg.placesnearme.presentation.feature.main.view.MainActivity;

@Subcomponent(modules = MainViewModule.class)
public interface MainActivityComponent {

    void inject(MainActivity target);

    @Subcomponent.Builder
    interface Builder {
        @BindsInstance
        MainActivityComponent.Builder bind(MainActivity mainActivity);
        MainActivityComponent build();
    }
}
