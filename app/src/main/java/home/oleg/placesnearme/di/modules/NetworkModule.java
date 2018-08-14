package home.oleg.placesnearme.di.modules;

import dagger.Module;
import dagger.Provides;
import home.oleg.placesnearme.service.FourSquareAPIFactory;
import home.oleg.placesnearme.service.IFourSquareAPI;
import io.reactivex.annotations.NonNull;

@Module
public final class NetworkModule {

    @Provides
    @NonNull
    public IFourSquareAPI provide() {
        return FourSquareAPIFactory.create();
    }

}
